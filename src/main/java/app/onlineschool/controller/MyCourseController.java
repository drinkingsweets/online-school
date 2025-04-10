package app.onlineschool.controller;

import app.onlineschool.dto.CurrentLessonPage;
import app.onlineschool.model.Lesson;
import app.onlineschool.model.Test;
import app.onlineschool.model.User;
import app.onlineschool.repository.CourseRepository;
import app.onlineschool.repository.LessonRepository;
import app.onlineschool.repository.TestRepository;
import app.onlineschool.repository.UserRepository;
import app.onlineschool.service.MarkdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/mycourses")
public class MyCourseController {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    MarkdownService markdownService;
    @Autowired
    private TestRepository testRepository;

    @GetMapping
    String index(Model model, Principal principal) {
        // showing all courses
        User user = userRepository.findByUsername(principal.getName()).get();
        model.addAttribute("courses", user.getCourses());
        model.addAttribute("courseProgress", user.getCourseProgress());
        return "contents/mycourses-buttons";
    }

    @GetMapping("/{id}")
    String show(Model model, @PathVariable Long id, Principal principal,
                @RequestParam(name = "lessonNum", required = false) String redirectToLesson) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if (user.getCourses().contains(courseRepository.findById(id).get())) {

            CurrentLessonPage clp = new CurrentLessonPage();
            int lessonNumberCurrent = user.getCourseProgress().get(id).getCompletedLessons();
            Lesson lesson;

            if (redirectToLesson != null) {
                // Если запрошен конкретный урок по параметру
                long requestedLessonNum = Long.parseLong(redirectToLesson);
                lesson = lessonRepository.findByCourseIdAndLessonNumber(id, requestedLessonNum).get();
                clp.setLessonNum(redirectToLesson);

                // Проверяем, является ли запрошенный урок уже пройденным
                boolean isLessonCompleted = requestedLessonNum < lessonNumberCurrent;
                Optional<Test> testForLesson = testRepository.findByLessonId(lesson.getId());
                if (testForLesson.isPresent() && !isLessonCompleted) {
                    // Показываем тест только если урок текущий (не пройденный)
                    clp.setHasTest(true);
                    clp.setTest(testForLesson.get());
                }
            } else {
                // Показываем текущий урок (последний непройденный)
                lesson = lessonRepository.findByCourseIdAndLessonNumber(id, lessonNumberCurrent).get();

                Optional<Test> testForLesson = testRepository.findByLessonId(lesson.getId());
                if (testForLesson.isPresent()) {
                    clp.setHasTest(true);
                    clp.setTest(testForLesson.get());
                }
            }

            clp.setLesson(lesson);
            model.addAttribute("page", clp);
            model.addAttribute("markdownService", markdownService);
            return "contents/mycourses-lesson";
        }
        return "redirect:/courses" + id;
    }

    @PostMapping("/{id}/next")
    String nextLesson(@PathVariable long id,
                      @RequestParam(name = "lessonNum", required = false) String redirectToLesson,
                      Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if (user.getCourses().contains(courseRepository.findById(id).get())) { // if user has this course
            int lessonCurrent = user.getCourseProgress().get(id).getCompletedLessons();

            if (redirectToLesson != null && Integer.parseInt(redirectToLesson) < lessonCurrent)
                return "redirect:/mycourses/" + id + "?lessonNum=" + (Long.parseLong(redirectToLesson) + 1);

            if (lessonCurrent == Collections.max(courseRepository.findById(id).get().getLessons()
                    .stream()
                    .map(Lesson::getLessonNumber)
                    .toList())) { // if last lesson
                user.getCourseProgress().get(id).setFinished(true); // set course as finished
                userRepository.save(user);
                return "redirect:/mycourses/" + id + "/finished";
            } else {
                user.getCourseProgress().get(id).setCompletedLessons(lessonCurrent + 1); // setting next lesson
                userRepository.save(user);
                return "redirect:/mycourses/" + id;
            }
        }
        return "redirect:/courses/" + id;
    }

    @GetMapping("/{id}/finished")
    String finishPage(@PathVariable Long id, Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if (user.getCourses().contains(courseRepository.findById(id).get())) { // showing finish page
            model.addAttribute("course", courseRepository.findById(id).get());
            return "contents/mycourses-finished";
        }
        return "redirect:/courses/" + id;
    }

    @PostMapping("/{id}/{lessonNum}")
    String gotoLesson(@PathVariable long id,
                      @PathVariable long lessonNum,
                      Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if (user.getCourses().contains(courseRepository.findById(id).get()) && user.getCourseProgress().get(id).getCompletedLessons() >= lessonNum) {
            // Просто перенаправляем на указанный урок без изменения completedLessons
            return "redirect:/mycourses/" + id + "?lessonNum=" + lessonNum;
        }
        return "redirect:/courses/" + id;
    }


}
