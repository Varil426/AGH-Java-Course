public class BoundingBox {
    boolean empty=true;
    double xmin;
    double ymin;
    double xmax;
    double ymax;

    void addPoint(double x, double y){
        if(empty) {
            xmin=x;
            xmax=x;
            ymax=y;
            ymin=y;
            empty=false;
        } else {
            if(xmin>x)xmin=x;
            if(xmax<x)xmax=x;
            if(ymin>y)ymin=y;
            if(ymax<y)ymax=y;
        }
    }
    boolean contains(double x, double y){
        if(x>=xmin && x<=xmax && y>=ymin && y<=ymax)return true;
        return false;
    }
    boolean contains(BoundingBox bb){
        if(bb.empty || empty) {
            return false;
        }
        if(xmin<=bb.xmin && xmax>=bb.xmax)
        return false;
    }
}
