package app.onlineschool.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RegisterLoginPage {
    private String username = "";
    private String fullName = "";
    private List<String> errors = new ArrayList<>();

    public void addError(String error) {
        errors.add(error);
    }
}
