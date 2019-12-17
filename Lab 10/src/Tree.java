import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.ArrayList;

public class Tree implements XmasShape {
    List<XmasShape> treeParts = new ArrayList<>();
    double scale = 1;
    int x=0;
    int y=0;

    Tree() {}
    Tree(int x, int y, double scale) {
        this.scale = scale;
        this.x = x;
        this.y = y;
    }

    Tree addPart(XmasShape part) {
        treeParts.add(part);
        return this;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        for (XmasShape shape: treeParts) {
            shape.draw(g2d);
        }
    }
}
