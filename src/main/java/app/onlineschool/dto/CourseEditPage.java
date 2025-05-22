package app.onlineschool.dto;

import app.onlineschool.model.Lesson;
import lombok.Getter;
import lombok.Setter;

/**
 * Transfers params to lesson edit page(admin)
 */
@Getter
@Setter
public class CourseEditPage {
    private Lesson lesson;
    private int previousLesson = 0;
    private int nextLesson = 2;
}
