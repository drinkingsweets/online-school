package app.onlineschool.controller;

import app.onlineschool.dto.ProfilePage;
import app.onlineschool.exception.ResourceNotFoundException;
import app.onlineschool.model.User;
import app.onlineschool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * RUD of profile
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Shows profile page
     * @param model
     * @param principal
     * @param success success message for different operations
     * @return profile page
     */
    @GetMapping
    public String index(Model model, Principal principal,@RequestParam(value = "success", required = false) String success) {
        ProfilePage pp = new ProfilePage();
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        pp.setUser(user);

        if (user.isAdmin()) {
            pp.setAdmin(true);
        }
        pp.setAdminMessage(success == null ? "" : success.replace("+", " "));
        model.addAttribute("page", pp);
        return "contents/profile-page";
    }

    /**
     * Handles changing pfp
     * @param principal
     * @param link
     * @return redirect to /profile
     */
    @PostMapping("/picture_change")
    public String pictureChange(Principal principal, @RequestParam String link) {
        User user = userRepository.findByUsername(principal.getName())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setPfpLink(link);
        userRepository.save(user);
        return "redirect:/profile";
    }

    @PostMapping()
    public String passwordChange(Principal principal,
                          @RequestParam String oldPassword,
                          @RequestParam String newPassword,
                          @RequestParam String newPasswordRepeat,
                          Model model) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
    public String delete(Principal principal, @RequestParam String password) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (passwordEncoder.matches(password, user.getPasswordDigest())) {
            userRepository.delete(user);
            return "redirect:/logout";
        }
        return "redirect:/profile";
    }

    @PostMapping("add_admin")
    public String addAdmin(@RequestParam String username, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isAdmin()) {
            return "redirect:/profile";
        }
        User createAdmin = userRepository.findByUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        createAdmin.setRole(1);
        userRepository.save(createAdmin);
        return "redirect:/profile?success=Admin+added";
    }
}
