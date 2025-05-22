package app.onlineschool.controller;

import app.onlineschool.dto.CourseEditPage;
import app.onlineschool.dto.PreviewPage;
import app.onlineschool.exception.ResourceNotFoundException;
import app.onlineschool.model.Course;
import app.onlineschool.model.Lesson;
import app.onlineschool.model.User;
import app.onlineschool.repository.CourseRepository;
import app.onlineschool.repository.LessonRepository;
import app.onlineschool.repository.TestRepository;
import app.onlineschool.repository.UserRepository;
import app.onlineschool.service.CustomUserDetailsService;
import app.onlineschool.service.MarkdownService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

/**
 * Handles all course CRUD + adding to mycourse
 */
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

    @Autowired
    private TestRepository testRepository;

    /**
     * Shows all courses
     * @param model
     * @return courses jte page
     */
    @GetMapping
    public String index(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "page/courses";
    }

    /**
     * Handles courses search
     * @param query String query to search
     * @param startDate start date for interval when course is created
     * @param endDate end date for interval when course is created
     * @param model
     * @return courses jte page
     */
    @PostMapping
    public String search(@RequestParam(required = false) String query,
                  @RequestParam(required = false) LocalDate startDate,
                  @RequestParam(required = false) LocalDate endDate,
                  Model model) {
        if (query != null && startDate == null && endDate == null) {
            model.addAttribute("courses", courseRepository.findByTitleContainingIgnoreCase(query));
        } else if (query != null && startDate != null && endDate != null) {
            model.addAttribute("courses", courseRepository.findByTitleContainingIgnoreCaseAndCreatedAtBetween(query, startDate, endDate));
        } else if (query == null && startDate != null && endDate != null) {
            model.addAttribute("courses", courseRepository.findByCreatedAtBetween(startDate, endDate));
        } else if (query != null && startDate != null && endDate == null) {
            model.addAttribute("courses", courseRepository.findByTitleContainingIgnoreCaseAndCreatedAtAfter(query, startDate));
        } else if (query != null && startDate == null && endDate != null) {
            model.addAttribute("courses", courseRepository.findByTitleContainingIgnoreCaseAndCreatedAtBefore(query, endDate));
        } else if (query == null && startDate != null && endDate == null) {
            model.addAttribute("courses", courseRepository.findByCreatedAtAfter(startDate));
        } else if (query == null && startDate == null && endDate != null) {
            model.addAttribute("courses", courseRepository.findByCreatedAtBefore(endDate));
        } else {
            model.addAttribute("courses", courseRepository.findAll());
        }
        return "page/courses";
    }

    /**
     * Shows current lesson of course
     * @param model
     * @param id course id to show
     * @param principal
     * @return course jte page
     */
    @GetMapping("/{id}")
    public String show(Model model, @PathVariable Long id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        model.addAttribute("isAdmin", user.isAdmin());

        if (user.getCourses().contains(courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found")))) {
            model.addAttribute("isAdded", true);
        } else {
            model.addAttribute("isAdded", false);
        }
        model.addAttribute("course", courseRepository.findById(id).get());
        return "page/course";
    }

    /**
     * Handles user adding course
     * @param id course id to add
     * @param principal
     * @return redirect to /courses/{id}
     */
    @PostMapping("/{id}/add")
    public String addCourse(@PathVariable long id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getCourses().contains(courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found")))) {
            user.getCourses().add(courseRepository.findById(id).get());
            user.getCourseProgress().putIfAbsent(id, new User.CourseProgress());
            user.getCourseProgress().get(id).setFinished(false);
            user.getCourseProgress().get(id).setCompletedLessons(1);
        }

        userRepository.save(user);
        return "redirect:/courses/" + id;
    }

    /**
     * Creates a course
     * @param principal
     * @return redirects to /courses if not admin, else shows courses-create jte page
     */
    @GetMapping("/create")
    public String createCourse(Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user.checkIfAdminAndRedirectTo("contents/courses-create", "redirect:/courses");
    }

    /**
     * Handles course creation
     * @param title
     * @param description
     * @param principal
     * @return redirect to /courses
     */
    @PostMapping("/create")
    public String createCoursePost(@RequestParam String title,
                            @RequestParam String description,
                            Principal principal) {
        if (userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"))
                .isAdmin()) {
            Course course = new Course();
            course.setTitle(title);
            course.setShortDescription(description);
            courseRepository.save(course);
        }
        return "redirect:/courses";
    }

    /**
     * Shows user interface for updating lesson
     * @param id course id
     * @param lessonNum
     * @param model
     * @param principal
     * @return courses edit page if admin, else redirect to /courses
     */
    @GetMapping("/{id}/{lessonNum}/edit")
    public String editCourse(@PathVariable long id,
                      @PathVariable int lessonNum, Model model, Principal principal) {
        Lesson lesson;

        if (userRepository.findByUsername(principal.getName()).get().isAdmin()) {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            CourseEditPage cep = new CourseEditPage();

            if (course.getLessons().isEmpty()) {
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

    /**
     * Handles updating lesson
     * @param id course id
     * @param lessonNum
     * @param title
     * @param content
     * @param principal
     * @return redirect to preview page if admin, else to /courses
     */
    @PostMapping("/{id}/{lessonNum}/edit")
    public String saveLesson(@PathVariable long id,
                      @PathVariable int lessonNum,
                      @RequestParam String title,
                      @RequestParam String content, Principal principal) {
        if (userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"))
                .isAdmin()){
            Lesson lesson = lessonRepository.findByCourseIdAndLessonNumber(id, lessonNum)
                    .orElseThrow(() -> new ResourceNotFoundException("Lesson not found"));
            lesson.setTitle(title);
            lesson.setContent(content);
            lessonRepository.save(lesson);
            customUserDetailsService.resetCourseProgressForUsers(id);

            return "redirect:/courses/" + id + "/" + lessonNum + "/preview";
        }
        return "redirect:/courses";
    }

    /**
     * Shows lesson preview
     * @param id
     * @param lessonNum
     * @param principal
     * @param model
     * @return lesson preview page if admin, else redirect to /courses
     */
    @GetMapping("/{id}/{lessonNum}/preview")
    public String previewLesson(@PathVariable long id,
                         @PathVariable int lessonNum,
                         Principal principal,
                         Model model) {
        if (userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"))
                .isAdmin()) {
            PreviewPage pp = new PreviewPage();
            pp.setLesson(lessonRepository.findByCourseIdAndLessonNumber(id, lessonNum)
                    .orElseThrow(() -> new ResourceNotFoundException("Lesson not found")));
            pp.setMarkdownService(markdownService);
            model.addAttribute("page", pp);
            return "contents/courses-preview";
        }
        return "redirect:/courses/" + id + "/" + lessonNum;
    }


    @PostMapping("/{courseId}/{lessonNum}/delete")
    @Transactional
    public String deleteLesson(@PathVariable long courseId,
                        @PathVariable int lessonNum,
                        Principal principal) {
        if (userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"))
                .isAdmin() &&
                lessonNum > 1) {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

            Lesson lesson = lessonRepository.findByCourseIdAndLessonNumber(courseId, lessonNum)
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));
            testRepository.deleteByLesson(lesson);
            course.removeLesson(lesson);


            int maxLessonNumber = course.getLessons().stream()
                    .mapToInt(Lesson::getLessonNumber)
                    .max()
                    .orElse(1);

            List<User> users = userRepository.findByCoursesContaining(course);
            for (User user : users) {
                User.CourseProgress progress = user.getCourseProgress().get(courseId);
                if (progress != null && progress.getCompletedLessons() > maxLessonNumber) {
                    progress.setCompletedLessons(maxLessonNumber);
                }
            }
            userRepository.saveAll(users);
        }
        return "redirect:/courses/" + courseId + "/" + (lessonNum - 1) + "/edit";
    }


    @PostMapping("/{id}/delete")
    @Transactional
    public String deleteCourse(@PathVariable long id, Principal principal) {
        if (userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"))
                .isAdmin()) {
            return "redirect:/courses/" + id;
        }

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        List<User> usersWithCourse = userRepository.findByCoursesContaining(course);
        for (User user : usersWithCourse) {
            user.getCourses().remove(course);
            user.getCourseProgress().remove(id); // Also remove progress tracking
            userRepository.save(user);
        }

        courseRepository.delete(course);

        return "redirect:/courses";
    }
}
