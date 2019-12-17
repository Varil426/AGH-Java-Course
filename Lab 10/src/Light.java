import java.awt.*;

public class Light implements XmasShape {
    int x=0;
    int y=0;
    double scaleX = 1;
    double scaleY = 1;
    Color color = new Color(223, 232, 113, 125);

    Light(){};
    Light(int x, int y, double scaleX, double scaleY) {
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
        int[] xP = {-50,-150,150,50,-50};
        int[] yP = {0,200,200,0,0};
        g2d.setColor(color);
        g2d.fillPolygon(xP, yP, xP.length);
    }
}
