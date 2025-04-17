package app.onlineschool.controller;

import app.onlineschool.dto.CurrentLessonPage;
import app.onlineschool.exception.ResourceNotFoundException;
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
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        model.addAttribute("courses", user.getCourses());
        model.addAttribute("courseProgress", user.getCourseProgress());
        return "contents/mycourses-buttons";
    }

    @GetMapping("/{id}")
    String show(Model model, @PathVariable Long id, Principal principal,
                @RequestParam(name = "lessonNum", required = false) String redirectToLesson) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getCourses().contains(courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found")))) {

            CurrentLessonPage clp = new CurrentLessonPage();
            int lessonNumberCurrent = user.getCourseProgress().get(id).getCompletedLessons();
            Lesson lesson;

            if (redirectToLesson != null) {
                long requestedLessonNum = Long.parseLong(redirectToLesson);
                lesson = lessonRepository.findByCourseIdAndLessonNumber(id, requestedLessonNum).get();
                clp.setLessonNum(redirectToLesson);

                boolean isLessonCompleted = requestedLessonNum < lessonNumberCurrent;
                Optional<Test> testForLesson = testRepository.findByLessonId(lesson.getId());
                if (testForLesson.isPresent() && !isLessonCompleted) {
                    clp.setHasTest(true);
                    clp.setTest(testForLesson.get());
                }
            } else {
                lesson = lessonRepository.findByCourseIdAndLessonNumber(id, lessonNumberCurrent)
                        .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));

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
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getCourses().contains(courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found")))) {

            int lessonCurrent = user.getCourseProgress().get(id).getCompletedLessons();

            if (redirectToLesson != null && Integer.parseInt(redirectToLesson) < lessonCurrent)
                return "redirect:/mycourses/" + id + "?lessonNum=" + (Long.parseLong(redirectToLesson) + 1);

            if (lessonCurrent == Collections.max(courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"))
                    .getLessons()
                    .stream()
                    .map(Lesson::getLessonNumber)
                    .toList())) {
                user.getCourseProgress().get(id).setFinished(true);
                userRepository.save(user);
                return "redirect:/mycourses/" + id + "/finished";
            } else {
                user.getCourseProgress().get(id).setCompletedLessons(lessonCurrent + 1);
                userRepository.save(user);
                return "redirect:/mycourses/" + id;
            }
        }
        return "redirect:/courses/" + id;
    }

    @GetMapping("/{id}/finished")
    String finishPage(@PathVariable Long id, Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getCourses().contains(courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found")))) {
            model.addAttribute("course", courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found")));
            return "contents/mycourses-finished";
        }
        return "redirect:/courses/" + id;
    }

    @PostMapping("/{id}/{lessonNum}")
    String gotoLesson(@PathVariable long id,
                      @PathVariable long lessonNum,
                      Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getCourses().contains(courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"))) &&
                user.getCourseProgress().get(id).getCompletedLessons() >= lessonNum) {
            return "redirect:/mycourses/" + id + "?lessonNum=" + lessonNum;
        }

        return "redirect:/courses/" + id;
    }


}
