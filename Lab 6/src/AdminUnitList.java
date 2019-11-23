import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();
    Map<Long, AdminUnit> longToAdminUnit = new HashMap<>();
    Map<AdminUnit, Long> adminUnitToParentId = new HashMap<>();

    public void read(String filename) throws FileNotFoundException {
        CSVReader reader = new CSVReader(filename, ",", true);
        while (reader.next()) {
            AdminUnit tmp = new AdminUnit();
            for (int i = 0; i < reader.getRecordLength(); i++) {
                if(!reader.isMissing(i)) {
                    if(i==0) {
                        longToAdminUnit.put(reader.getLong(i), tmp);
                    }
                    if(i==1) {
                        adminUnitToParentId.put(tmp, reader.getLong(i));
                    }
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
            units.add(tmp);
        }
        for (int i = 0; i < units.size(); i++) {
            units.get(i).parent = longToAdminUnit.get(adminUnitToParentId.get(units.get(i)));
            //Odczyt z pliku v.3
            if(units.get(i).parent != null)units.get(i).parent.children.add(units.get(i));
        }
    }

    void list(PrintStream out){
        units.forEach((e) -> out.printf("%s\n", e.toString()));
    }

    void list(PrintStream out,int offset, int limit ){
        if(offset >= units.size() || offset < 0) throw new RuntimeException("Out Of Bounds");
        for (int i = offset; i < offset+limit && i<units.size(); i++) {
            out.printf("%s\n", units.get(i).toString());
        }
    }

    AdminUnitList selectByName(String pattern, boolean regex){
        AdminUnitList ret = new AdminUnitList();
        for (int i = 0; i < units.size(); i++) {
            if(regex) {
                if(units.get(i).toString().matches(pattern)) {
                    ret.units.add(units.get(i));
                }
            } else {
                if(units.get(i).toString().contains(pattern)) {
                    ret.units.add(units.get(i));
                }
            }
        }
        return ret;
    }


    //Dużo wartości jest wciąż zerowych, po prześledzeniu na debuggerze kod wydaje działać się dobrze, po prostu dociera do obiektu, który nie ma już rodzica a wciąż parent.density == 0
    void fixMissingValues() {
        for (int i = 0; i < units.size(); i++) {
            if(units.get(i).density == 0 || units.get(i).population == 0) {
                fixMissingValues(units.get(i));
            }
        }
    }
    void fixMissingValues(AdminUnit au) {
        AdminUnit parent = longToAdminUnit.get(adminUnitToParentId.get(au));
        if(au.density == 0 &&  parent != null) {
            if(parent.density == 0) {
                fixMissingValues(parent);
            }
            au.density = parent.density;
        }
        if(au.population == 0 && parent != null) {
            if(parent.population == 0) {
                fixMissingValues(parent);
            }
            au.population = au.area * au.density;
        }
    }
}

