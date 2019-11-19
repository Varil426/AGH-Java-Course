import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public CSVReader(String filename, String delimiter) throws FileNotFoundException {
        this(filename, delimiter, false);
    }

    public CSVReader(String filename) throws FileNotFoundException {
        this(filename, ",", false);
    }

    public CSVReader(Reader reader, String delimiter, boolean hasHeader) {
        this.reader = new BufferedReader(reader);
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
            String[] header = line.split(delimiter + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
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
        current = tmp.split(delimiter + "(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        return true;
    }
    List<String> getColumnLabels() {
        return columnLabels;
    }
    int getRecordLength() {
        return current.length;
    }
    boolean isMissing(int columnIndex) {
        if(this.getRecordLength()<=columnIndex)return true;
        if(current[columnIndex]!=null && current[columnIndex]!="")return false;
        return true;
    }
    boolean isMissing(String columnLabel) {
        if(columnLabelsToInt.size()==0 || columnLabelsToInt.get(columnLabel)==null) {
            return true;
        }
        return isMissing(columnLabelsToInt.get(columnLabel));
    }
    String get(int columnIndex) {
        if(current.length<=columnIndex ||current[columnIndex]==null)return "";
        return current[columnIndex];
    }
    String get(String columnLabel) {
        if(columnLabelsToInt.size()==0 || columnLabelsToInt.get(columnLabel)==null) {
            return "";
        }
        return get(columnLabelsToInt.get(columnLabel));
    }
    int getInt(int columnIndex) {
        if(columnIndex>=current.length)throw new RuntimeException("Out of bounds");
        return Integer.parseInt(current[columnIndex]);
    }
    int getInt(String columnLabel) {
        if(columnLabelsToInt.get(columnLabel)==null)throw new RuntimeException("Unknown Label");
        return getInt(columnLabelsToInt.get(columnLabel));
    }
    long getLong(int columnIndex) {
        if(columnIndex>=current.length)throw new RuntimeException("Out of bounds");
        return Long.parseLong(current[columnIndex]);
    }
    long getLong(String columnLabel) {
        if(columnLabelsToInt.get(columnLabel)==null)throw new RuntimeException("Unknown Label");
        return getLong(columnLabelsToInt.get(columnLabel));
    }
    double getDouble(int columnIndex) {
        if(columnIndex>=current.length)throw new RuntimeException("Out of bounds");
        return Double.parseDouble(current[columnIndex]);
    }
    double getDouble(String columnLabel) {
        if(columnLabelsToInt.get(columnLabel)==null)throw new RuntimeException("Unknown Label");
        return getDouble(columnLabelsToInt.get(columnLabel));
    }
    LocalTime getTime(int columnIndex) throws RuntimeException {
        return getTime(columnIndex, "HH:mm:ss");
    }
    LocalTime getTime(int columnIndex, String format) {
        if(get(columnIndex)=="")throw new RuntimeException("Out of bounds");
        return LocalTime.parse(get(columnIndex), DateTimeFormatter.ofPattern(format));
    }
    LocalTime getTime(String columnLabel) throws RuntimeException {
        return getTime(columnLabel, "HH:mm:ss");
    }
    LocalTime getTime(String columnLabel, String format) {
        if(columnLabelsToInt.get(columnLabel)==null)throw new RuntimeException("Unknown Label");
        return getTime(columnLabelsToInt.get(columnLabel), format);
    }
    LocalDate getDate(int columnIndex) throws RuntimeException {
        return getDate(columnIndex, "RR-mm-dd");
    }
    LocalDate getDate(int columnIndex, String format) {
        if(get(columnIndex)=="")throw new RuntimeException("Out of bounds");
        return LocalDate.parse(get(columnIndex), DateTimeFormatter.ofPattern(format));
    }
    LocalDate getDate(String columnLabel) throws RuntimeException {
        return getDate(columnLabel, "RR-mm-dd");
    }
    LocalDate getDate(String columnLabel, String format) {
        if(columnLabelsToInt.get(columnLabel)==null)throw new RuntimeException("Unknown Label");
        return getDate(columnLabelsToInt.get(columnLabel), format);
    }
}