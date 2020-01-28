import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BouncingBallsPanel extends JPanel {
    AnimationThread thread = new AnimationThread();

    static class Ball{
        int x;
        int y;
        double vx;
        double vy;
        Color color;

        public Ball(int x, int y, double vx, double vy, Color color) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.color = color;
        }
    }

    List<Ball> balls = new ArrayList<>();

    //TODO Losowanie kilkunastu kulek na początek
    //TODO funkcjonalność przycisków
    //TODO Podwójne bufforowanie
    //TODO Zderzenia kulek

    class AnimationThread extends Thread{
        boolean paused = true;
        public void run(){
            for(;;){
                synchronized (thread){
                    if(paused) {
                        try {
                            thread.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println(thread.getState());
                //przesuń kulki
                //wykonaj odbicia od ściany
                //wywołaj repaint
                //uśpij
            }
        }
    }

    BouncingBallsPanel(){
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
        thread.start();
    }

    void onStart(){
        System.out.println("Start or resume animation thread");
        thread.paused = false;
        synchronized (thread) {
            thread.notifyAll();
        }
    }

    void onStop(){
        System.out.println("Suspend animation thread");
        thread.paused = true;
    }

    void onPlus(){
        System.out.println("Add a ball");
        balls.add(new Ball(10,10,1,1,new Color(255, 0,0)));
    }

    void onMinus(){
        System.out.println("Remove a ball");
        balls.remove(balls.size()-1);
    }
}