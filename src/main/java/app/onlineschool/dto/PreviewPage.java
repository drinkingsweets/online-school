package app.onlineschool.dto;

import app.onlineschool.model.Lesson;
import app.onlineschool.service.MarkdownService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PreviewPage {
    private Lesson lesson;
    private MarkdownService markdownService;
}
