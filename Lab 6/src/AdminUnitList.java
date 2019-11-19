import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();

    /**
     * Czyta rekordy pliku i dodaje do listy
     * @param filename nazwa pliku
     */

    public void read(String filename) throws FileNotFoundException {
        CSVReader reader = new CSVReader(filename, ",", true);
        while (reader.next()) {
            AdminUnit tmp = new AdminUnit();
            for (int i = 0; i < reader.getRecordLength(); i++) {
                if(!reader.isMissing(i)) {
                    if(i==2) {
                        tmp.name = reader.get(i);
                    }
                    if(i==3) {
                        tmp.adminLevel = reader.getInt(i);
                    }
                    if(i==4) {
                        tmp.population = reader.getDouble(i);
                    }
                    if(i==5) {
                        tmp.area = reader.getDouble(i);
                    }
                }
            }
        }
    }
}

