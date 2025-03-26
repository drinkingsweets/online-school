package app.onlineschool.controller;

import app.onlineschool.dto.RegisterLoginPage;
import app.onlineschool.dto.WelcomePage;
import app.onlineschool.model.Course;
import app.onlineschool.model.User;
import app.onlineschool.repositoty.CourseRepository;
import app.onlineschool.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.desktop.ScreenSleepEvent;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BaseController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    String index(Model model) {
        List<Object[]> topCourses = userRepository.countUsersByCourseId();
        List<Course> courses3 = new ArrayList<>();

        for(Object[] object: topCourses) {
            System.out.println(object[0] + " " + object[1]);
        }
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

    @GetMapping("/login")
    String login(@RequestParam(value = "error", required = false) String error,
                 @RequestParam(value = "username", required = false) String username, Model model) {
        RegisterLoginPage page = new RegisterLoginPage();
        if (error != null) {
            page.setUsername(username);
            page.addError("Invalid username or password");
        }
        model.addAttribute("page", page);
        return "page/login";
    }


    @GetMapping("/register")
    String register(Model model) {
        model.addAttribute("page", new RegisterLoginPage());
        return "page/register";
    }

    @PostMapping("/register")
    String registerPost(@RequestParam String fullName,
                        @RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String passwordConfirmation, Model model) {

        boolean isUnique = userRepository.findByUsername(username).isEmpty();
        if (password.equals(passwordConfirmation) && isUnique) { // checks if passwords match and username is unique
            User user = new User();
            user.setFullName(fullName);
            user.setUsername(username);
            user.setPasswordDigest(passwordEncoder.encode(password));
            user.setRole(0); // sets the role to user
            user.setPfpLink("https://i.pinimg.com/736x/8b/db/8e/8bdb8e8a536946dbe616ee509b7fb435.jpg");
            userRepository.save(user);
            return "redirect:/login";
        } else {
            RegisterLoginPage rlp = new RegisterLoginPage();
            if (!isUnique) rlp.addError("This username is taken");
            if (!password.equals(passwordConfirmation)) rlp.addError("Passwords don't match"); // showing errors
            rlp.setFullName(fullName);

            model.addAttribute("page", rlp);
            return "page/register";
        }
    }

    @GetMapping("/home")
    String home(Model model, Principal principal) {
        model.addAttribute("isAdmin",
                userRepository.findByUsername(principal.getName()).get().getRole() == 1);
        return "contents/buttons-welcome";
    }
}
