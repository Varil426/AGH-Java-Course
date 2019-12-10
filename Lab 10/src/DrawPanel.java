import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class DrawPanel extends JPanel {
    List<XmasShape> shapes = new ArrayList<>();

    DrawPanel() {
        shapes.add(new Bubble());
    }

    public void paintComponent(Graphics g) {
        setBackground(new Color(32, 145, 139));
        setOpaque(true);
        super.paintComponent(g);

        for(XmasShape s:shapes){
            s.draw((Graphics2D)g);
        }
        System.out.println("test");
    }
}