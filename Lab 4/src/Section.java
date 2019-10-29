import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Section {
    String title;
    List<Paragraph> paragraphList = new ArrayList<>();

    Section() {};
    Section(String title) {
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    void addParagraph(Paragraph p) {
        this.paragraphList.add(p);
    }
    void writeHTML(PrintStream out) {
        out.printf("<div><h2>%s</h2>", this.title);
        for (int i = 0; i < this.paragraphList.size(); i++) {
            this.paragraphList.get(i).writeHTML(out);
        }
        out.printf("</div>");
    }
}
