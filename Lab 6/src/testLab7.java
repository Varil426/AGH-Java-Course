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
            System.out.println("\nJaworki na mapie\n");
            System.out.println(testUnitJaworki.bbox.getWKT());
            System.out.println("\nJednostki na K posortowane według nazwy\n");
            unitList.filter(a->a.name.startsWith("K")).sortInplaceByName().list(out);
            System.out.println("\nJednostki będące powiatami w województwie małopolskim\n");
            unitList.filter(a->(a.adminLevel == 6 && a.parent.name.equals("województwo małopolskie"))).list(out);
            System.out.println("\nJednostki administracyjne mające w nazwie powiat z województwa podkarpackiego\n");
            unitList.filter(a->(a.name.contains("powiat") && a.parent.name.equals("województwo podkarpackie"))).list(out);
            System.out.println("\nTest AdminQueryUnit\n");
            AdminUnitQuery query = new AdminUnitQuery()
                    .selectFrom(unitList)
                    .where(a->a.area>1000)
                    .or(a->a.name.startsWith("Sz"))
                    .sort((a,b)->Double.compare(a.area,b.area))
                    .limit(100);
            query.execute().list(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
