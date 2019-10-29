import java.io.PrintStream;

public class ParagraphWithList extends Paragraph {
    UnorderedList list = new UnorderedList();

    ParagraphWithList(String content) {
        super(content);
    }
    void setContent(String content) {
        this.content = content;
    }
    void addItemToList(ListItem item) {
        this.list.addItem(item);
    }
    void writeHTML(PrintStream out) {
        super.writeHTML(out);
        this.list.writeHTML(out);
    }
}
