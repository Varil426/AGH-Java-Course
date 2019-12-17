import java.awt.*;

public class Stump implements XmasShape {
    int x=0;
    int y=0;
    double scale=1;

    Stump(){}
    Stump(int x, int y, double scale) {
        this.x=x;
        this.y=y;
        this.scale=scale;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setPaint(new GradientPaint(0,0, new Color(62,31,9),0,100, new Color(0,0,0)));
        g2d.fillRect(-5,0,10, 30);
    }
}
