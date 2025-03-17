package app.onlineschool.service;


import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.stereotype.Service;

@Service
public class MarkdownService {

    static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.BLANK_LINES_IN_AST, true)  // Сохранять пустые строки в AST
            .set(Parser.CODE_SOFT_LINE_BREAKS, true)
            .set(Parser.FENCED_CODE_BLOCK_PARSER, true)
            .set(Parser.LISTS_LOOSE_WHEN_CONTAINS_BLANK_LINE, true)
            .set(Parser.BLOCK_QUOTE_EXTEND_TO_BLANK_LINE, true)
            .set(HtmlRenderer.SOFT_BREAK, "<br />\n");

    private final Parser parser = Parser.builder(OPTIONS).build();
    private final HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS).build();

    public String convertMarkdownToHtml(String markdown) {
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }
}
