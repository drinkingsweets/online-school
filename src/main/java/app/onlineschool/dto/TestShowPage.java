package app.onlineschool.dto;

import app.onlineschool.model.Test;
import lombok.Getter;
import lombok.Setter;

/**
 * Transfers params to test show page
 */
@Getter
@Setter
public class TestShowPage {
    private Test test;
    private int questionNumber;
    private long courseId;
}
