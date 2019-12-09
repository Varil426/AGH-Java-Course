import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();
    Map<Long, AdminUnit> longToAdminUnit = new HashMap<>();
    Map<AdminUnit, Long> adminUnitToParentId = new HashMap<>();

    public void read(String filename) throws FileNotFoundException {
        CSVReader reader = new CSVReader(filename, ",", true);
        while (reader.next()) {
            AdminUnit tmp = new AdminUnit();
            if(!reader.isMissing("id"))longToAdminUnit.put(reader.getLong("id"), tmp);
            if(!reader.isMissing("parent"))adminUnitToParentId.put(tmp, reader.getLong("parent"));
            if(!reader.isMissing("name"))tmp.name = reader.get("name");
            if(!reader.isMissing("admin_level"))tmp.adminLevel = reader.getInt("admin_level");
            if(!reader.isMissing("population"))tmp.population = reader.getDouble("population");
            if(!reader.isMissing("area"))tmp.area = reader.getDouble("area");
            if(!reader.isMissing("density"))tmp.density = reader.getDouble("density");
            //Wczytywanie punktów bounding box
            for (int i = 7; i < 13; i+=2) {
                if(!reader.isMissing(i) && !reader.isMissing(i+1)) {
                    tmp.bbox.addPoint(reader.getDouble(i), reader.getDouble(i+1));
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
                if(units.get(i).name.matches(pattern)) {
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

    void fixMissingValues() {
        for (int i = 0; i < units.size(); i++) {
            if(units.get(i).density == 0 || units.get(i).population == 0) {
                fixMissingValues(units.get(i));
            }
        }
    }
    void fixMissingValues(AdminUnit au) {
        AdminUnit parent = longToAdminUnit.get(adminUnitToParentId.get(au));
        if(au.density == 0 && parent != null) {
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
    AdminUnitList getNeighbours(AdminUnit unit, double maxdistance) {
        AdminUnitList result = new AdminUnitList();
        for (int i = 0; i < units.size(); i++) {
            AdminUnit current = units.get(i);
            if(current == unit)continue;
            if(unit.adminLevel <= 8) {
                if(current.adminLevel == unit.adminLevel && (current.bbox.intersects(unit.bbox) || current.bbox.distanceTo(unit.bbox) < maxdistance)) {
                    result.units.add(current);
                }
            } else {
                if (current.adminLevel == unit.adminLevel && current.bbox.intersects(unit.bbox)) {
                    result.units.add(current);
                }
            }
        }
        return result;
    }
    AdminUnitList sortInplaceByName(){
        class SortByName implements Comparator<AdminUnit> {
            @Override
            public int compare(AdminUnit u1, AdminUnit u2) {
                return u1.name.compareTo(u2.name);
            }
        }
        units.sort(new SortByName());
        return this;
    }
    AdminUnitList sortInplaceByArea(){
        this.units.sort(new Comparator<AdminUnit>() {
            @Override
            public int compare(AdminUnit u1, AdminUnit u2) {
                return Double.compare(u1.area, u2.area);
            }
        });
        return this;
    }
    AdminUnitList sortInplaceByPopulation(){
        this.units.sort((x1,x2)->Double.compare(x1.population,x2.population));
        return this;
    }
    AdminUnitList sortInplace(Comparator<AdminUnit> cmp){
        this.units.sort(cmp);
        return this;
    }
    AdminUnitList sort(Comparator<AdminUnit> cmp){
        AdminUnitList result = new AdminUnitList();
        result.units = new ArrayList<AdminUnit>();
        for (int i = 0; i < this.units.size(); i++) {
            result.units.add(this.units.get(i));
        }
        return result.sortInplace(cmp);
    }
    AdminUnitList filter(Predicate<AdminUnit> pred, int limit){
        AdminUnitList result = new AdminUnitList();
        for (int i = 0; i < this.units.size() && result.units.size()<=limit; i++) {
            if(pred.test(this.units.get(i)))result.units.add(this.units.get(i));
        }
        return result;
    }
    AdminUnitList filter(Predicate<AdminUnit> pred) {
        return filter(pred, this.units.size());
    }
    AdminUnitList filter(Predicate<AdminUnit> pred, int offset, int limit){
        AdminUnitList filter = this.filter(pred, limit);
        AdminUnitList result = new AdminUnitList();
        if(offset<0 || offset>filter.units.size())throw new RuntimeException("Offset beyond limits");
        for (int i = offset; i < filter.units.size(); i++) {
            result.units.add(filter.units.get(i));
        }
        return result;
    }
}

