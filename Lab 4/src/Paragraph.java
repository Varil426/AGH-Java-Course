import java.io.PrintStream;

public class Paragraph {
    String content;

    Paragraph() {

    }

    Paragraph(String content) {
        this.content = content;
    }

    void setContent(String content) {
        this.content = content;
    }
    void writeHTML(PrintStream out){
        out.printf("<p>%s</p>",this.content);
    }
}
