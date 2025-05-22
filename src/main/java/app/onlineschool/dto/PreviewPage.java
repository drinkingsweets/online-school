package app.onlineschool.dto;

import app.onlineschool.model.Lesson;
import app.onlineschool.service.MarkdownService;
import lombok.Getter;
import lombok.Setter;

/**
 * Transfers params to preview lesson page.
 * Includes markdownService for parsing Markdown to HTML
 */
@Getter
@Setter
public class PreviewPage {
    private Lesson lesson;
    private MarkdownService markdownService;
}
