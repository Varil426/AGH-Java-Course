import java.awt.*;
import java.awt.geom.Point2D;

public class Bubble implements XmasShape {
    int x=0;
    int y=0;
    double scale=1;
    Color lineColor=new Color(0,255,0);
    Color fillColor=new Color(255,0,0);
    boolean empty = false;

    Bubble() {}
    Bubble(int x, int y, double scale, Color lineColor, Color fillColor) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.lineColor = lineColor;
        this.fillColor = fillColor;
    }
    Bubble(int x, int y, double scale, Color lineColor) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.empty = true;
        this.lineColor = lineColor;
    }

    @Override
    public void render(Graphics2D g2d) {
        RadialGradientPaint grad = new RadialGradientPaint(new Point2D.Float(25, 25), 100, new float[]{0.1f, 1.0f}, new Color[]{new Color(255,255,255), fillColor});
        if(!empty) {
            g2d.setPaint(grad);
            g2d.fillOval(0,0,100,100);
        }
        grad = new RadialGradientPaint(new Point2D.Float(25, 25), 100, new float[]{0.1f, 1.0f}, new Color[]{new Color(255,255,255), lineColor});
        g2d.setPaint(grad);
        g2d.drawOval(0,0,100,100);
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }
}