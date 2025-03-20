package app.onlineschool.controller;

import app.onlineschool.dto.CourseEditPage;
import app.onlineschool.dto.PreviewPage;
import app.onlineschool.model.Course;
import app.onlineschool.model.Lesson;
import app.onlineschool.model.User;
import app.onlineschool.repositoty.CourseRepository;
import app.onlineschool.repositoty.LessonRepository;
import app.onlineschool.repositoty.UserRepository;
import app.onlineschool.service.CustomUserDetailsService;
import app.onlineschool.service.MarkdownService;
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
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private MarkdownService markdownService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping
    String index(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "page/courses";
    }

    @GetMapping("/{id}")
    String show(Model model, @PathVariable Long id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).get();
        if (user.getRole() == 1) {
            model.addAttribute("isAdmin", true);
        }
        else {
            model.addAttribute("isAdmin", false);
        }
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

    @GetMapping("/{id}/{lessonNum}/edit")
    String editCourse(@PathVariable long id,
                      @PathVariable int lessonNum,  Model model, Principal principal) {
        Lesson lesson;

        if (userRepository.findByUsername(principal.getName()).get().getRole() == 1) {
            Course course = courseRepository.findById(id).get();
            CourseEditPage cep = new CourseEditPage();
            if (course.getLessons().isEmpty()) { // if empty, creating new empty lesson
                lesson = new Lesson();

                lesson.setTitle("Untitled");
                lesson.setContent("");
                lesson.setLessonNumber(1);
                lesson.setCourse(course);
                lessonRepository.save(lesson);
                cep.setLesson(lesson);

                model.addAttribute("page", cep);
                return "contents/courses-edit";

            } else if (lessonNum > course.getLessons()
                             .stream()
                             .mapToInt(Lesson::getLessonNumber)
                             .max()
                             .getAsInt()) {

                lesson = new Lesson();
                lesson.setTitle("Untitled");
                lesson.setContent("");
                lesson.setLessonNumber(lessonNum);
                lesson.setCourse(course);
                lessonRepository.save(lesson);

            } else {
                lesson = lessonRepository.findByCourseIdAndLessonNumber(id, lessonNum).get();
            }

            cep.setLesson(lesson);
            cep.setNextLesson(lessonNum + 1);
            cep.setPreviousLesson(lessonNum - 1);

            model.addAttribute("page", cep);
            return "contents/courses-edit";
        }
        return "redirect:/courses";
    }

    @PostMapping("/{id}/{lessonNum}/edit")
    String saveLesson(@PathVariable long id,
                      @PathVariable int lessonNum,
                      @RequestParam String title,
                      @RequestParam String content, Principal principal) {
        if (userRepository.findByUsername(principal.getName()).get().getRole() == 1) {
            Lesson lesson = lessonRepository.findByCourseIdAndLessonNumber(id, lessonNum).get();
            lesson.setTitle(title);
            lesson.setContent(content);
            lessonRepository.save(lesson);
            customUserDetailsService.resetCourseProgressForUsers(id);

            return "redirect:/courses/" + id + "/" + lessonNum + "/preview";
        }
        return "redirect:/courses";
    }

    @GetMapping("/{id}/{lessonNum}/preview")
    String previewLesson(@PathVariable long id,
                         @PathVariable int lessonNum,
                         Principal principal,
                         Model model) {
        if (userRepository.findByUsername(principal.getName()).get().getRole() == 1) {
            PreviewPage pp = new PreviewPage();
            pp.setLesson(lessonRepository.findByCourseIdAndLessonNumber(id, lessonNum).get());
            pp.setMarkdownService(markdownService);
            model.addAttribute("page", pp);
            return "contents/courses-preview";
        }
        return "redirect:/courses/" + id + "/" + lessonNum;
    }
}
