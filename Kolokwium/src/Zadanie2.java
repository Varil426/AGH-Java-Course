import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Zadanie2 {
    static int[] numbers = new int[100000000];
    static BlockingQueue<Integer> results = new ArrayBlockingQueue<Integer>(100);

    public static void main(String[] args) {
        Random rand = new Random();
        int k=7;
        for (int i = 0; i < numbers.length; i++) {
            numbers[i]=rand.nextInt(100) + 1;
        }
        double t1 = System.nanoTime() / 1e6;
        System.out.println(countDivisible(k));
        double t2 = System.nanoTime() / 1e6;
        System.out.println(countDivisible(k, 4));
        double t3 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "t2-t1=%f t3-t1=%f\n",
                t2 - t1,
                t3 - t1);
    }

    static int countDivisible(int k) {
        int count = 0;
        for (int i = 0; i < numbers.length; i++) {
            if(numbers[i]%k==0)count++;
        }
        return count;
    }

    static class countCalc extends Thread {
        int start;
        int end;
        int k;
        countCalc(int start, int end, int k) {
            this.start=start;
            this.end=end;
            this.k=k;
        }
        public void run() {
            int count = 0;
            for (int i = start; i < end; i++) {
                if(numbers[i]%k==0)count++;
            }
            try {
                results.put(count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    static int countDivisible(int k, int cnt) {
        int sum=0;
        countCalc threads[] = new countCalc[cnt];
        for (int i = 0; i < cnt; i++) {
            threads[i] = new countCalc(i * (numbers.length/cnt), (i + 1) * (numbers.length/cnt), k);
        }
        for (countCalc t : threads) {
            t.start();
        }
        for (countCalc t : threads) {
            try {
                sum += results.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sum;
    }
}
