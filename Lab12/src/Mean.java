import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Mean {
    static double[] array;
    static BlockingQueue<Double> results = new ArrayBlockingQueue<Double>(100);
    static void initArray(int size){
        array = new double[size];
        for(int i=0;i<size;i++){
            array[i]= Math.random()*size/(i+1);
        }
    }

    public static void main(String[] args) {
        initArray(128000000);
        for(int cnt:new int[]{1,2,4,8,16,32,64,128}){
            parallelMean(cnt);
        }
    }

    static class MeanCalc extends Thread{
        private final int start;
        private final int end;
        double mean = 0;

        MeanCalc(int start, int end){
            this.start = start;
            this.end=end;
        }
        public void run(){
            for (int i = start; i < end; i++) {
                mean+=array[i];
            }
            mean=mean/(end-start);
            try {
                results.put(mean);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.printf(Locale.US,"%d-%d mean=%f\n",start,end,mean);
        }
    }
    static void parallelMean(int cnt) {
        // utwórz tablicę wątków
        MeanCalc threads[] = new MeanCalc[cnt];
        for (int i = 0; i < cnt; i++) {
            threads[i] = new MeanCalc(i * (array.length/cnt), (i + 1) * (array.length/cnt));
        }
        // utwórz wątki, podziel tablice na równe bloki i przekaż indeksy do wątków
        // załóż, że array.length dzieli się przez cnt)
        double t1 = System.nanoTime() / 1e6;
        for (MeanCalc t : threads) {
            t.start();
        }
        double t2 = System.nanoTime() / 1e6;
        double mean = 0;
        for (MeanCalc t : threads) {
            try {
                mean += results.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // czekaj na ich zakończenie używając metody ''join''
        //for (MeanCalc mc : threads) {
        //    try {
        //        mc.join();
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    }
        //}
        // oblicz średnią ze średnich
        double t3 = System.nanoTime() / 1e6;
        //for (MeanCalc t : threads) {
        //    mean += t.mean;
        //}
        mean = mean / threads.length;
        System.out.printf(Locale.US, "size = %d cnt=%d >  t2-t1=%f t3-t1=%f mean=%f\n",
                array.length,
                cnt,
                t2 - t1,
                t3 - t1,
                mean);
    }
}