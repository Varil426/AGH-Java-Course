import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;
    String[] current;
    /**
     *
     * @param filename - nazwa pliku
     * @param delimiter - separator pól
     * @param hasHeader - czy plik ma wiersz nagłówkowy
     */

    // nazwy kolumn w takiej kolejności, jak w pliku
    List<String> columnLabels = new ArrayList<>();
    // odwzorowanie: nazwa kolumny -> numer kolumny
    Map<String,Integer> columnLabelsToInt = new HashMap<>();

    public CSVReader(String filename,String delimiter,boolean hasHeader) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(filename));
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if(hasHeader)parseHeader();
    }

    void parseHeader() {
        // wczytaj wiersz
        try {
            String line = reader.readLine();
            if (line == null) {
                return;
            }
            // podziel na pola
            String[] header = line.split(delimiter);
            // przetwarzaj dane w wierszu
            for (int i = 0; i < header.length; i++) {
                columnLabels.add(header[i]);
                columnLabelsToInt.put(header[i], i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    boolean next(){
        String tmp = null;
        try {
            tmp = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if(tmp==null)return false;
        current = tmp.split(delimiter);
        return true;
    }
}