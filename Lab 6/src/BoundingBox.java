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
        if(this.isEmpty())return false;
        if(x>=xmin && x<=xmax && y>=ymin && y<=ymax)return true;
        return false;
    }
    boolean contains(BoundingBox bb){
        if(bb.isEmpty() || this.isEmpty())return false;
        if(xmin<=bb.xmin && xmax>=bb.xmax && ymin<=bb.ymin && ymax>=bb.ymax)return true;
        return false;
    }
    boolean intersects(BoundingBox bb){
        if(this.isEmpty() || bb.isEmpty())return false;
        if(xmin>bb.xmax || xmax<bb.xmin)return false;
        if(ymin>bb.ymax || ymax<bb.ymin)return false;
        return true;
    }
    BoundingBox add(BoundingBox bb){
        if(this.contains(bb))return this;
        this.addPoint(bb.xmax, bb.ymax);
        this.addPoint(bb.xmin, bb.ymin);
        return this;
    }
    boolean isEmpty(){
        return empty;
    }
    double getCenterX(){
        if(!this.isEmpty()) {
            return (xmin+xmax)/2;
        } else throw new IllegalStateException("This bounding box is empty");
    }
    double getCenterY(){
        if(!this.isEmpty()) {
            return (ymin+ymax)/2;
        } else throw new IllegalStateException("This bounding box is empty");
    }

    double distanceTo(BoundingBox bbx){
        if(!this.isEmpty() || !bbx.isEmpty()) {
            return Haversine.distance(Math.min(this.getCenterY(), bbx.getCenterY()), Math.min(this.getCenterX(), bbx.getCenterX()), Math.max(this.getCenterY(), bbx.getCenterY()), Math.max(this.getCenterX(), bbx.getCenterX()));
        } else throw new IllegalStateException("At least one of the bounding boxes is empty");
    }
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Bounding Box: MIN(").append(xmin).append(",").append(ymin).append("),MAX(").append(xmax).append(",").append(ymax).append(")");
        return string.toString();
    }
}
