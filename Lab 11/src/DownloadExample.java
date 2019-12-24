import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadExample {
    static AtomicInteger count = new AtomicInteger(0);
    static Semaphore sem = new Semaphore(0);
    // lista plików do pobrania
    static String [] toDownload = {
            "http://home.agh.edu.pl/pszwed/wyklad-c/01-jezyk-c-intro.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/02-jezyk-c-podstawy-skladni.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/03-jezyk-c-instrukcje.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/04-jezyk-c-funkcje.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/05-jezyk-c-deklaracje-typy.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/06-jezyk-c-wskazniki.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/07-jezyk-c-operatory.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/08-jezyk-c-lancuchy-znakow.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/09-jezyk-c-struktura-programow.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/10-jezyk-c-dynamiczna-alokacja-pamieci.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/11-jezyk-c-biblioteka-we-wy.pdf",
            "http://home.agh.edu.pl/~pszwed/wyklad-c/preprocesor-make-funkcje-biblioteczne.pdf",
    };

    static class Downloader implements Runnable {
        private final String url;

        Downloader(String url){
            this.url = url;
        }

        public void run(){
            String fileName = url.substring(url.lastIndexOf('/')+1);
            File downloads = new File("downloads");
            if(!downloads.exists())downloads.mkdir();
            try(InputStream in = new URL(url).openStream(); FileOutputStream out = new FileOutputStream("downloads"+ File.separator+fileName) ){
                int data;
                while ((data = in.read()) != -1) {
                    out.write(data);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            count.incrementAndGet();
            sem.release();
            //System.out.println("Done:"+fileName);
        }
    }

    static void sequentialDownload(){
        double t1 = System.nanoTime()/1e6;
        for(String url:toDownload){
            new Downloader(url).run();
        }
        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
        count.set(0);
    }

    public static void main(String[] args) {
        System.out.print("Pobieranie sekwencyjne:");
        sequentialDownload();
        int choice = 3;
        System.out.println("Wybierz wersję pobierania współbieżnego: ");
        Scanner input = new Scanner(System.in);
        choice = input.nextInt();
        System.out.printf("Pobieranie współbieżne %d: ", choice);
        try {
            sem.acquire(toDownload.length);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(choice==1)concurrentDownload();
        else if (choice==2)concurrentDownload2();
        else if (choice==3)concurrentDownload3();
        else System.out.println("ERROR");
    }
    static void concurrentDownload(){
        double t1 = System.nanoTime()/1e6;
        for(String url:toDownload){
            new Thread(new Downloader(url)).start();
        }
        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
        count.set(0);
    }
    static void concurrentDownload2(){
        double t1 = System.nanoTime()/1e6;
        for(String url:toDownload){
            new Thread(new Downloader(url)).start();
        }
        while (count.get()<toDownload.length);
        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
        count.set(0);
    }
    static void concurrentDownload3(){
        double t1 = System.nanoTime()/1e6;
        for(String url:toDownload){
            new Thread(new Downloader(url)).start();
        }
        try {
            sem.acquire(toDownload.length);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"t2-t1=%f\n",t2-t1);
        count.set(0);
    }
}