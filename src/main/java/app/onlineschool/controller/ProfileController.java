package app.onlineschool.controller;

import app.onlineschool.dto.ProfilePage;
import app.onlineschool.model.User;
import app.onlineschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    String index(Model model, Principal principal,@RequestParam(value = "success", required = false) String success) {
        ProfilePage pp = new ProfilePage();
        pp.setUser(userRepository.findByUsername(principal.getName()).get());
        if (userRepository.findByUsername(principal.getName()).get().getRole() == 1) {
            pp.setAdmin(true);
        }
        pp.setAdminMessage(success == null ? "" : success.replace("+", " "));
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

    @PostMapping("add_admin")
    String addAdmin(@RequestParam String username, Principal principal) {
        if (userRepository.findByUsername(principal.getName()).get().getRole() != 1) {
            return "redirect:/profile";
        }
        User user = userRepository.findByUsername(username).get();
        user.setRole(1);
        userRepository.save(user);
        return "redirect:/profile?success=Admin+added";
    }
}
