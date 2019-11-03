import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ListItemTest {

    @Test
    void writeHTML() {
        String content = "Sentence to check! < > { } [ ] Ą ć";
        ListItem listItem = new ListItem(content);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(os);
        listItem.writeHTML(out);
        String result = null;
        try {
            result = os.toString("UTF-8");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertTrue(result.contains("Ą"));
        assertTrue(result.contains(content));
        assertTrue(result.contains("<li>"));
        assertTrue(result.contains("</li>"));
    }
}