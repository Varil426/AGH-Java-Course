import java.io.PrintStream;

public class Photo {
    String url;
    Photo(){};
    Photo(String url) {
        this.url = url;
    }

    void writeHTML(PrintStream out) {
        out.printf("<img src=\"%s\" alt=\"CV Picture\" height=\"42\" width=\"42\"/>\n",this.url);
    }
}
