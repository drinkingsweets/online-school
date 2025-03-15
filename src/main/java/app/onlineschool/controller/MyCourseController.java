package app.onlineschool.controller;

import app.onlineschool.model.Lesson;
import app.onlineschool.model.User;
import app.onlineschool.repositoty.CourseRepository;
import app.onlineschool.repositoty.LessonRepository;
import app.onlineschool.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collections;

@Controller
@RequestMapping("/mycourses")
public class MyCourseController {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LessonRepository lessonRepository;

    @GetMapping
    String index(Model model, Principal principal) {
        // showing all courses
        User user = userRepository.findByUsername(principal.getName()).get();
        model.addAttribute("courses", user.getCourses());
        model.addAttribute("courseProgress", user.getCourseProgress());
        return "contents/mycourses-buttons";
    }

    @GetMapping("/{id}")
    String show(Model model, @PathVariable Long id, Principal principal) {
        // showing lesson when clicked on course
        User user = userRepository.findByUsername(principal.getName()).get();
        if (user.getCourses().contains(courseRepository.findById(id).get())) {
            int lessonCurrent = user.getCourseProgress().get(id).getCompletedLessons();
            model.addAttribute("currentLesson", lessonRepository.findByCourseIdAndLessonNumber(id, lessonCurrent).get());
            return "contents/mycourses-lesson";
        }
        return "redirect:/courses" + id;
    }

    @PostMapping("/{id}/next")
    String nextLesson(@PathVariable long id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if (user.getCourses().contains(courseRepository.findById(id).get())) { // if user has this course
            int lessonCurrent = user.getCourseProgress().get(id).getCompletedLessons();
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

    @PostMapping("/{id}/previous")
    String previousLesson(@PathVariable long id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if (user.getCourses().contains(courseRepository.findById(id).get())) {
            int lessonCurrent = user.getCourseProgress().get(id).getCompletedLessons();
            if (lessonCurrent > 1) {
                user.getCourseProgress().get(id).setCompletedLessons(lessonCurrent - 1);
                userRepository.save(user);
            }
            return "redirect:/mycourses/" + id;
        }
        return "redirect:/courses/" + id;
    }
}
