package app.onlineschool.dto;

import app.onlineschool.model.Lesson;
import app.onlineschool.model.Test;
import lombok.Getter;
import lombok.Setter;

/**
 * Transfers params to user view lesson page
 */
@Getter
@Setter
public class CurrentLessonPage {
    private Lesson lesson;
    private Test test;
    private boolean hasTest = false;
    private String lessonNum = "";
}
