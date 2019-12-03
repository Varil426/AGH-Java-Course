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
            System.out.println("\nPo fixMissingValues\n");
            unitList.selectByName("^Wola [KP].*", true).list(out);
            //Sąsiedzi test
            System.out.println("\nSąsiedzi dla Jaworek\n");
            AdminUnit testUnitJaworki = unitList.selectByName("Jaworki", false).units.get(0);
            unitList.getNeighbours(testUnitJaworki, 10).list(out);
            System.out.println(testUnitJaworki.bbox.getWKT());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
