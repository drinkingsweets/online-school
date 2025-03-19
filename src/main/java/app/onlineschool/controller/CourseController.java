package app.onlineschool.controller;

import app.onlineschool.model.Course;
import app.onlineschool.model.User;
import app.onlineschool.repositoty.CourseRepository;
import app.onlineschool.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    String index(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "page/courses";
    }

    @GetMapping("/{id}")
    String show(Model model, @PathVariable Long id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if (user.getCourses().contains(courseRepository.findById(id).get())) { // checking if course is added
            model.addAttribute("isAdded", true);
        }
        else {
            model.addAttribute("isAdded", false);
        }
        model.addAttribute("course", courseRepository.findById(id).get());
        return "page/course";
    }

    @PostMapping("/{id}/add")
    String addCourse(@PathVariable long id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if (!user.getCourses().contains(courseRepository.findById(id).get())) { // adding if course is not added
            user.getCourses().add(courseRepository.findById(id).get());
            user.getCourseProgress().putIfAbsent(id, new User.CourseProgress()); // creating course progress
            user.getCourseProgress().get(id).setFinished(false); // setting course as not finished
            user.getCourseProgress().get(id).setCompletedLessons(1); // setting first lesson as progress
        }

        userRepository.save(user);
        return "redirect:/courses/" + id;
    }

    //TODO make a search bar for courses

    @GetMapping("/create")
    String createCourse(Principal principal) {
        if (userRepository.findByUsername(principal.getName()).get().getRole() == 1) {
            return "contents/courses-create";
        }
        return "redirect:/courses";
    }

    @PostMapping("/create")
    String createCoursePost(@RequestParam String title,
                            @RequestParam String description,
                            Principal principal) {
        if (userRepository.findByUsername(principal.getName()).get().getRole() == 1) {
            Course course = new Course();
            course.setTitle(title);
            course.setShortDescription(description);
            courseRepository.save(course); // TODO maybe show blank page to write lessons?
        }
        return "redirect:/courses";
    }

}
