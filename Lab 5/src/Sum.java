import java.util.ArrayList;
import java.util.List;

public class Sum extends Node {

    List<Node> args = new ArrayList<>();

    Sum(){}

    Sum(Node n1, Node n2){
        args.add(n1);
        args.add(n2);
    }


    Sum add(Node n){
        args.add(n);
        return this;
    }

    Sum add(double c){
        args.add(new Constant(c));
        return this;
    }

    Sum add(double c, Node n) {
        Node mul = new Prod(c,n);
        args.add(mul);
        return this;
    }

    @Override
    double evaluate() {
        double result =0;
        for (int i = 0; i < args.size(); i++) {
            result += args.get(i).evaluate();
        }
        return sign*result;
    }

    int getArgumentsCount(){return args.size();}

    public String toString(){
        StringBuilder b =  new StringBuilder();
        if(sign<0)b.append("-(");
        for (int i = 0; i < this.args.size(); i++) {
            b.append(this.args.get(i).toString());
            if(i < this.args.size()-1) {
                b.append("+");
            }
        }
        if(sign<0)b.append(")");
        return b.toString();
    }

    @Override
    Node diff(Variable var) {
        ArrayList<Node> list = new ArrayList<>();
        for(Node n:args){
            if(!n.isZero())list.add(n);
        }
        if(list.size()==0) {
            return new Constant(0);
        }
        Sum r = new Sum();
        for (Node n:list) {
            if(!n.diff(var).isZero()) r.add(n.diff(var));
        }
        return r;
    }

    @Override
    boolean isZero() {
        for (Node n : this.args) {
            if(!n.isZero()) {
                return false;
            }
        }
        return true;
    }
}