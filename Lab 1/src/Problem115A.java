import java.util.ArrayList;
import java.util.Scanner;

public class Problem115A {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        if (n>=1 && n<=2000) {
            int[] employees = new int[n];
            for (int i = 0; i < n; i++) {
                //Indexuje pracowników od 0, -2 nie mają szefa
                employees[i] = input.nextInt()-1;
            }
            int max = 0;
            for (int i = 0; i < employees.length; i++) {
                int z=employees[i];
                int count=1;
                while (z!=-2) {
                    z=employees[z];
                    count++;
                }
                if(max<count)max=count;
            }
            System.out.println(max);
        }
    }


}
