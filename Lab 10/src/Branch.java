import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Branch implements XmasShape{
    int x=10;
    int y=10;
    double scaleX=1;
    double scaleY=1;
    GradientPaint grad = new GradientPaint(0,0,new Color(0,0,0),0,100, new Color(0,255,0));

    Branch() {}

    Branch(int x, int y, double scaleX, double scaleY) {
        this.x = x;
        this.y = y;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scaleX,scaleY);
    }

    @Override
    public void render(Graphics2D g2d) {
        int xP[] = {0,-100,-50,0,50,100,0};
        int yP[] = {0,100,75,100,75,100,0};
        g2d.setPaint(grad);
        g2d.fillPolygon(xP, yP, xP.length);
    }

}
