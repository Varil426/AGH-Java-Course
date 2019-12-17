import java.awt.*;

public class Star implements XmasShape {
    int x=0;
    int y=0;
    double scale=1;
    boolean empty=true;
    Color lineColor=new Color(255,255,255);
    Color fillColor;

    Star(){};
    Star(int x, int y, double scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }
    Star(int x, int y, double scale, Color fillColor, Color lineColor) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.fillColor = fillColor;
        this.lineColor = lineColor;
        empty=false;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        //int xP[] = {0,-10,15,-15,10,0};
        //int yP[] = {-2,20,5,5,20,-2};
        int xP[] = {0,5,15,10,15,5,0,-5,-15,-10,-15,-5,0};
        int yP[] = {0,10,10,20,30,30,40,30,30,20,10,10,0};
        if(!empty) {
            g2d.setColor(fillColor);
            g2d.fillPolygon(xP, yP, xP.length);
        }
        g2d.setColor(lineColor);
        g2d.drawPolygon(xP, yP, xP.length);
    }
}
