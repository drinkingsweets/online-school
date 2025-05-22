package app.onlineschool.dto;

import app.onlineschool.model.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Transfers params to user profile page
 */
@Getter
@Setter
public class ProfilePage {
    private User user;
    private String success = "";
    private String error = "";
    private boolean isAdmin = false;
    private String adminMessage = "";
}
