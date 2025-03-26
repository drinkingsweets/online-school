package app.onlineschool.dto;

import app.onlineschool.model.Course;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WelcomePage {
    private List<Course> courses3;
}
