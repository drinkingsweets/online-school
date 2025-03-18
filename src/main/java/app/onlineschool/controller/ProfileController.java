package app.onlineschool.controller;

import app.onlineschool.dto.ProfilePage;
import app.onlineschool.model.User;
import app.onlineschool.repositoty.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    String index(Model model, Principal principal) {
        ProfilePage pp = new ProfilePage();
        pp.setUser(userRepository.findByUsername(principal.getName()).get());
        model.addAttribute("page", pp);
        return "contents/profile-page";
    }

    @PostMapping("/picture_change")
    String pictureChange(Principal principal, @RequestParam String link) {
        User user = userRepository.findByUsername(principal.getName()).get();
        user.setPfpLink(link);
        userRepository.save(user);
        return "redirect:/profile";
    }

    @PostMapping()
    String passwordChange(Principal principal,
                          @RequestParam String oldPassword,
                          @RequestParam String newPassword,
                          @RequestParam String newPasswordRepeat,
                          Model model) {
        User user = userRepository.findByUsername(principal.getName()).get();
        ProfilePage pp = new ProfilePage();
        pp.setUser(user);

        if(passwordEncoder.matches(oldPassword, user.getPasswordDigest())) {
            if(newPassword.equals(newPasswordRepeat)) {
                user.setPasswordDigest(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                pp.setSuccess("Password changed successfully");
            }
            else {
               pp.setError("New passwords do not match");
            }
        }
        else {
            pp.setError("Old password is incorrect");
        }
        model.addAttribute("page", pp);
        return "contents/profile-page";
    }

    @PostMapping("/delete")
    String delete(Principal principal, @RequestParam String password) {
        if(passwordEncoder.matches(password, userRepository.findByUsername(principal.getName()).get().getPasswordDigest())) {
            userRepository.delete(userRepository.findByUsername(principal.getName()).get());
            return "redirect:/logout";
        }
        return "redirect:/profile";
    }
}
