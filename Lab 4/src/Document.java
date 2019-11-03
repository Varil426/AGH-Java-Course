import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Document {
    String title;
    Photo photo = new Photo();
    List<Section> sections = new ArrayList<Section>();

    Document(){};

    Document(String title) {
        this.title = title;
    }

    void setTitle(String title){
        this.title = title;
    }
    void setPhoto(String photoUrl){
        this.photo.url = photoUrl;
    }
    void addSection(Section s) {
        this.sections.add(s);
    }
    void writeHTML(PrintStream out) {
        out.printf("<h1>%s</h1>", this.title);
        this.photo.writeHTML(out);
        for (int i = 0; i < this.sections.size(); i++) {
            this.sections.get(i).writeHTML(out);
        }
    }
}
