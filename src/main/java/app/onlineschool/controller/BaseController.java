package app.onlineschool.controller;

import app.onlineschool.dto.RegisterLoginPage;
import app.onlineschool.dto.WelcomePage;
import app.onlineschool.model.Course;
import app.onlineschool.model.User;
import app.onlineschool.repository.CourseRepository;
import app.onlineschool.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles base GET &amp; POST requests
 */
@Controller
public class BaseController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Shows "/" welcome page
     * @param model
     * @return welcome jte page
     */
    @GetMapping
    public String index(Model model) {
        List<Object[]> topCourses = userRepository.countUsersByCourseId();
        List<Course> courses3 = new ArrayList<>();

        for (int i = 0; i < Math.min(3, topCourses.size()); i++) {
            Object[] result = topCourses.get(i);
            Long courseId = (Long) result[0];
            Course course = courseRepository.findById(courseId).orElse(null);
            if (course != null) {
                courses3.add(course);
            }
        }

        WelcomePage wp = new WelcomePage();
        wp.setCourses3(courses3);
        model.addAttribute("page", wp);
        return "page/welcome";
    }

    /**
     * Shows "/login" page
     * @param error shows login error for user
     * @param username shows username if not first login attempt
     * @param model
     * @return login jte page
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                 @RequestParam(value = "username", required = false) String username, Model model) {
        RegisterLoginPage page = new RegisterLoginPage();
        if (error != null) {
            page.setUsername(username);
            page.addError("Invalid username or password");
        }
        model.addAttribute("page", page);
        return "page/login";
    }

    /**
     * Shows "/register" page
     * @param model
     * @return register jte page
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("page", new RegisterLoginPage());
        return "page/register";
    }

    /**
     * Creates new user
     * @param fullName
     * @param username
     * @param password
     * @param passwordConfirmation
     * @param model
     * @return redirect to login if success, else register page with error
     */
    @PostMapping("/register")
    public String registerPost(@RequestParam String fullName,
                               @RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String passwordConfirmation,
                               Model model) {
        RegisterLoginPage rlp = new RegisterLoginPage();
        rlp.setFullName(fullName);
        rlp.setUsername(username);

        boolean hasErrors = false;

        if (!userRepository.findByUsername(username).isEmpty()) {
            rlp.addError("This username is taken");
            hasErrors = true;
        }

        if (username == null || username.trim().isEmpty()) {
            rlp.addError("Username cannot be empty");
            hasErrors = true;
        } else if (username.length() < 3 || username.length() > 50) {
            rlp.addError("Username must be between 3 and 50 characters");
            hasErrors = true;
        } else if (!username.matches("^[a-zA-Z0-9._-]+$")) {
            rlp.addError("Username can only contain letters, numbers, dots, underscores, or hyphens");
            hasErrors = true;
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            rlp.addError("Full name cannot be empty");
            hasErrors = true;
        } else if (fullName.length() < 2 || fullName.length() > 100) {
            rlp.addError("Full name must be between 2 and 100 characters");
            hasErrors = true;
        } else if (!fullName.matches("^[a-zA-Z\\s-]+$")) {
            rlp.addError("Full name can only contain letters, spaces, or hyphens");
            hasErrors = true;
        }

        if (password == null || password.length() < 8) {
            rlp.addError("Password must be at least 8 characters");
            hasErrors = true;
        }

        if (!password.equals(passwordConfirmation)) {
            rlp.addError("Passwords don't match");
            hasErrors = true;
        }

        if (hasErrors) {
            model.addAttribute("page", rlp);
            return "page/register";
        }

        User user = new User();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setPasswordDigest(passwordEncoder.encode(password));
        user.setRole(0);
        user.setPfpLink("https://i.pinimg.com/736x/8b/db/8e/8bdb8e8a536946dbe616ee509b7fb435.jpg");
        userRepository.save(user);
        return "redirect:/login";
    }

    /**
     * Shows "/home" page after success login
     * @param model
     * @param principal
     * @return home jte page
     */
    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        model.addAttribute("isAdmin",
                userRepository.findByUsername(principal.getName()).get().isAdmin());
        return "contents/buttons-welcome";
    }

    /**
     * Shows "/about" page with author info
     * @param model
     * @return about jte page
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("author", userRepository.findByUsername("admin").get());
        return "contents/about";
    }
}
