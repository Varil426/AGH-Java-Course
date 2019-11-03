import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PhotoTest {

    @Test
    void writeHTML() {
        String url = "/exampleFolder/example.png";
        Photo photo = new Photo(url);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(os);
        photo.writeHTML(out);
        String result = null;
        try {
            result = os.toString("ISO-8859-2");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertTrue(result.contains("/>"));
        assertTrue(result.contains(url));
        assertTrue(result.contains("height=\"42\" width=\"42\""));
    }
}