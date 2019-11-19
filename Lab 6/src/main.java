import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class main {
    public static void main(String[] args) {
        //TEST 1
        try {
            //With header
            System.out.println("With header\n");
            CSVReader reader = new CSVReader("csv" + File.separator + "with-header.csv",";",true);
            System.out.println(reader.getColumnLabels().toString());
            while(reader.next()){
                String id = reader.get("id");
                String name = reader.get("imi�");
                String lastname = reader.get("nazwisko");
                String ulica = reader.get("ulica");
                String nrdomu = reader.get("nrdomu");
                String nrmieszkania = reader.get("nrmieszkania");

                System.out.printf(Locale.US,"%s %s %s %s %s %s\n",id, name,lastname, ulica, nrdomu, nrmieszkania);
            }
            //Without header
            System.out.println("\nNo header\n");
            reader = new CSVReader("csv" + File.separator + "no-header.csv",";");
            while(reader.next()){
                String id = reader.get(0);
                String name = reader.get(1);
                String lastname = reader.get(2);
                String ulica = reader.get(3);
                String nrdomu = reader.get(4);
                String nrmieszkania = reader.get(5);

                System.out.printf(Locale.US,"%s %s %s %s %s %s\n",id, name,lastname, ulica, nrdomu, nrmieszkania);
            }
            //TEST 2
            reader = new CSVReader("csv" + File.separator + "accelerator.csv",";",true);
            System.out.println("\nTEST 2\n");
            while(reader.next()){
                String value1 = reader.get(0);
                int value2 = reader.getInt("label");
                double value3 = reader.getDouble(2);
                long value4 = reader.getLong("time");
                System.out.printf(Locale.US,"%s %d %f %d\n",value1,value2,value3,value4);
            }
            //TEST 3
            reader = new CSVReader("csv" + File.separator + "missing-values.csv",";", true);
            while (reader.next()) {
                for (String columnLabel : reader.getColumnLabels()) {
                    if(!reader.isMissing(columnLabel)) {
                        System.out.printf("%s ", reader.get(columnLabel));
                    }
                }
                System.out.println();
            }
            //TEST 4
            reader = new CSVReader("csv" + File.separator + "with-header.csv",";", true);
            try {
                reader.next();
                //System.out.println(reader.getInt("TEST"));
                System.out.println(reader.getInt(20));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            //TEST 5
            String text = "a,b,c\n123.4,567.8,91011.12";
            reader = new CSVReader(new StringReader(text),",",true);
            while (reader.next()){
                for (String s : reader.current) {
                    System.out.println(s);
                }
            }
            //Titanic
            System.out.println("\nTitanic\n");
            reader = new CSVReader("csv" + File.separator + "titanic-part.csv",",",true);
            while(reader.next()){
                int id = reader.getInt("PassengerId");
                String name = reader.get("Name");
                double fare = reader.getDouble("Fare");
                System.out.printf(Locale.US,"%d %s %f\n",id, name, fare);
            }
            reader = new CSVReader("csv" + File.separator + "admin-units.csv",",",true);
            for (int i = 0; i < 100 && reader.next(); i++) {
                for (String s : reader.current) {
                    System.out.printf("%s ", s);
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
