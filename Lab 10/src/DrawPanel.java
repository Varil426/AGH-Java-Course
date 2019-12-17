import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DrawPanel extends JPanel {
    List<XmasShape> shapes = new ArrayList<>();

    Tree generateTree(int x, int y, double scale, int parts) {
        Random rand = new Random();
        Color[] colors = {new Color(255,0,0), new Color(0,0,255)};
        Tree tree = new Tree(x, y, scale);
        tree.addPart(new Stump(0,10,4));
        for (int i = 0; i < parts; i++) {
            tree.addPart(new Branch(0, -8 - (i*30), 1.8-i*0.2>0.4?1.8-i*0.2:0.4, 1.2-i*0.05>0.8?1.2-i*0.05:0.8));
        }
        for (int i = 0; i < parts-1; i++) {
            for (int j = 0; j < 3; j++) {
                if(rand.nextBoolean()){
                    int bubbleX = (-60 + i*6) + ((60 - i*6)*j);
                    int bubbleY = 40-(i*30+j*5);
                    if(rand.nextInt(100)<80) {
                        tree.addPart(new Bubble(bubbleX,bubbleY,0.2,colors[j%2],colors[j%2]));
                    } else {
                        tree.addPart(new Bubble(bubbleX,bubbleY,0.2,colors[j%2]));
                    }
                }
            }
        }
        tree.addPart(new Star(0,parts*(-30),1.2,new Color(255, 234, 112),new Color(255, 249, 52)));
        return tree;
    }

    DrawPanel() {
        addShape(generateTree(100, 200, 0.6,9));
        addShape(generateTree(400, 200, 0.6,9));
        addShape(generateTree(600, 200, 0.6,9));
        addShape(generateTree(900, 200, 0.6,9));
        addShape(generateTree(300, 300, 0.9,7));
        addShape(generateTree(700, 300, 0.9,7));
        addShape(generateTree(500, 400, 1,8));
        addShape(new Light(500,0,1.5,3.2));
    }

    DrawPanel addShape(XmasShape shape) {
        shapes.add(shape);
        return this;
    }

    public void paintComponent(Graphics g){
        setBackground(new Color(32, 145, 139));
        super.paintComponent(g);
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            new Star(rand.nextInt()%1000,rand.nextInt()%250,rand.nextDouble()+0.2,new Color(255, 255, 255),new Color(255, 255, 255)).draw((Graphics2D)g);
        }
        g.setColor(new Color(23, 79, 73));
        int[] xP = {0,250,500,1000,1000,0,0};
        int[] yP = {200,150,220,180,800,800,400};
        g.fillPolygon(xP, yP, xP.length);
        for(XmasShape s:shapes){
            s.draw((Graphics2D)g);
        }
        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.ITALIC, 60));
        g.drawString("Merry Christmas", 290, 600);
    }
}