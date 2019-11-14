import java.util.Scanner;

public class Problem610A {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        if (n >= 1 && n <= 2*Math.pow(10,9)) {
            if(n%2!=0) {
                System.out.println(0);
            } else {
                if(n%4!=0) {
                    System.out.println((int)((n/4)-1));
                }
                else {
                    System.out.println(n/4);
                }
            }
        }
    }
}
