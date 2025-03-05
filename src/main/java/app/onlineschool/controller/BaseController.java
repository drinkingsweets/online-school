package app.onlineschool.controller;

import app.onlineschool.dto.RegisterLoginPage;
import app.onlineschool.model.User;
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

import java.util.Optional;

@Controller
public class BaseController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    String index() {
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
        if (password.equals(passwordConfirmation) && isUnique) {
            User user = new User();
            user.setFullName(fullName);
            user.setUsername(username);
            user.setPasswordDigest(passwordEncoder.encode(password));
            user.setRole(0);
            userRepository.save(user);
            return "redirect:/login";
        } else {
            RegisterLoginPage rlp = new RegisterLoginPage();
            if (!isUnique) rlp.addError("This username is taken");
            if (!password.equals(passwordConfirmation)) rlp.addError("Passwords don't match");
            rlp.setFullName(fullName);

            model.addAttribute("page", rlp);
            return "page/register";
        }
    }

//    @PostMapping("/login")
//    String performLogin(@RequestParam String username,
//                        @RequestParam String password, Model model) {
//        Optional<User> user = userRepository.findByUsername(username);
//        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPasswordDigest())) {
//            return "redirect:/courses";
//        }
//
//        RegisterLoginPage rlp = new RegisterLoginPage();
//
//        if (user.isEmpty()) {
//            rlp.addError("User doesn't exist");
//        } else if (!passwordEncoder.matches(password, user.get().getPasswordDigest())) {
//            rlp.addError("Invalid password");
//        }
//        rlp.setUsername(username);
//        model.addAttribute("page", rlp);
//        return "page/login";
//    }
}
