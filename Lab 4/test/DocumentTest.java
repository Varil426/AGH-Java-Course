import org.junit.jupiter.api.Test;

import javax.print.Doc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    @Test
    void writeHTML() {
        Document testDoc = new Document("Title");
        testDoc.setPhoto("example/photo.png");
        testDoc.addSection(new Section("Section 0"));
        testDoc.addSection(new Section("Section 1"));
        testDoc.sections.get(0).addParagraph(new Paragraph("Paragraph within Section 0 Content"));
        testDoc.sections.get(0).addParagraph(new ParagraphWithList("Paragraph With List within Section 0 Content"));
        ((ParagraphWithList) testDoc.sections.get(0).paragraphList.get(1)).addItemToList(new ListItem("Item 1"));
        ((ParagraphWithList) testDoc.sections.get(0).paragraphList.get(1)).addItemToList(new ListItem("Item 2"));
        testDoc.sections.get(1).addParagraph(new Paragraph("Paragraph within Section 1 Content"));

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(os);
        testDoc.writeHTML(out);

        String result = null;
        try {
            result = os.toString("UTF-8");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertTrue(result.contains("Paragraph With List within Section 0 Content"));
        assertTrue(result.contains("Item 2"));
        assertTrue(result.contains("<li>"));
        assertTrue(result.contains("</ul>"));
        assertTrue(result.contains("<div>"));
        assertTrue(result.contains("</div>"));
        System.out.println(result);
    }
}