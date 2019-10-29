import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class UnorderedList {
    List<ListItem> listItems = new ArrayList<ListItem>();

    UnorderedList() {};
    void addItem(ListItem item) {
        this.listItems.add(item);
    }

    void writeHTML(PrintStream out) {
        out.printf("<ul>");
        for (int i = 0; i < listItems.size(); i++) {
            listItems.get(i).writeHTML(out);
        }
        out.printf("</ul>");
    }
}
