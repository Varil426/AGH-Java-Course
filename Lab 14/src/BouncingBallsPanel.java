import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BouncingBallsPanel extends JPanel {
    AnimationThread thread = new AnimationThread();

    static class Ball{
        int x;
        int y;
        int d;
        double vx;
        double vy;
        Color color;

        public Ball() {
            Random r = new Random();
            x = r.nextInt(700);
            y = r.nextInt(700);
            d = r.nextInt(20)+5;
            vx = r.nextInt(5)+1;
            vy = r.nextInt(5)+10;
            color = new Color(r.nextInt(255), r.nextInt(255),r.nextInt(255));
        }

        public void draw(Graphics2D g2d){
            AffineTransform saveAT = g2d.getTransform();
            this.render(g2d);
            g2d.transform(saveAT);
        }
        public void render(Graphics2D g2d){
            g2d.setColor(this.color);
            g2d.fillOval(this.x, this.y, this.d, this.d);
        }
    }

    List<Ball> balls = new ArrayList<>();

    //TODO Podwójne bufforowanie
    //TODO Zderzenia kulek
    //TODO Zderzenia ze ścianami - poprawić

    class AnimationThread extends Thread{
        boolean paused = true;
        public void run(){
            for(;;){
                synchronized (this){
                    if(paused) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                balls.forEach(ball -> {
                    ball.x += ball.vx;
                    ball.y += ball.vy;
                    if(ball.x + ball.d >= 700 || ball.x <= 0)ball.vx *= -1;
                    if(ball.y + ball.d >= 700 || ball.y <= 0)ball.vy *= -1;
                });
                repaint();
                //BouncingBallsPanel.this.repaint();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        balls.forEach(ball -> ball.draw((Graphics2D) g));
    }

    BouncingBallsPanel(){
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
        thread.start();
    }

    void onStart(){
        System.out.println("Start or resume animation thread");
        if(balls.isEmpty()){
            for (int i = 0; i < 8; i++) {
                balls.add(new Ball());
            }
        }
        thread.paused = false;
        synchronized (thread) {
            thread.notify();
        }
    }

    void onStop(){
        System.out.println("Suspend animation thread");
        thread.paused = true;
    }

    void onPlus(){
        System.out.println("Add a ball");
        balls.add(new Ball());
    }

    void onMinus(){
        if(!balls.isEmpty()){
            System.out.println("Remove a ball");
            balls.remove(balls.size()-1);
        }
        else System.out.println("Nothing to remove");
    }
}