import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class main {
    public static void main(String[] args) {
        try {
            CSVReader reader = new CSVReader("wplatomaty-euronet.csv", ";", true);
            System.out.println(reader.getColumnLabels().toString());
            List<ATM> units = new ArrayList<>();
            while (reader.next()) {
                String id = reader.get("ID");
                String name = reader.get("Name");
                String city = reader.get("City");
                String address = reader.get("Address");
                String postCode = reader.get("Post code");
                String district = reader.get("District");
                LocalDate liveDate = reader.getDate("Live Date","d.M.y");
                String accessHours = reader.get("Client access hours");
                units.add(new ATM(id, name, city, address, postCode, district, liveDate, accessHours));
            }
            //Podpunkt 1
            for (int i = 0; i < units.size(); i++) {
                ATM current = units.get(i);
                if(current.district.equals("Małopolskie") && current.accessHours.equals("00:00 - 23:59")) System.out.println(current.toString());
            }
            //Podpunkt 2
            for (int i = 0; i < units.size(); i++) {
                ATM current = units.get(i);
                LocalDate year = LocalDate.of(2017,01,01);
                int result = year.compareTo(current.liveDate);
                if(!current.name.contains("Bank") && result>0) {
                    System.out.println(current.toString());
                }
            }
            //Podpunkt 3
            Set<String> hash_Set = new HashSet<String>();
            for (int i = 0; i < units.size(); i++) {
                ATM current = units.get(i);
                if (current.name.contains("Bank")) {
                    hash_Set.add(current.name);
                }
            }
            System.out.println(Arrays.toString(hash_Set.toArray()));
            //TESTY
            /*for (int i = 0; i < units.size(); i++) {
                ATM current = units.get(i);
                System.out.println(current.liveDate);
            }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
