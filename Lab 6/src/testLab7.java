import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

public class testLab7 {
    public static void main(String[] args) {
        try{
            System.out.println("\nAdmin Units\n");
            AdminUnitList unitList = new AdminUnitList();
            unitList.read("csv" + File.separator + "admin-units.csv");
            PrintStream out = new PrintStream(System.out);
            System.out.println("Wszystkie elementy:\n");
            unitList.list(out);
            System.out.println("\nOgraniczona ilość elementów:\n");
            unitList.list(out, 2, 5);
            System.out.println("\nSelect By Name\n");
            unitList.selectByName("^Wola [KP].*", true).list(out);
            unitList.fixMissingValues();
            unitList.list(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
