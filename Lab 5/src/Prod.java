import java.util.ArrayList;
import java.util.List;

public class Prod extends Node {
    List<Node> args = new ArrayList<>();

    Prod(){}

    Prod(Node n1){
        args.add(n1);
    }
    Prod(double c){
        args.add(new Constant(c)) ;
    }

    Prod(Node n1, Node n2){
        args.add(n1);
        args.add(n2);
    }
    Prod(double c, Node n){
        args.add(new Constant(c));
        args.add(n);
    }



    Prod mul(Node n){
        args.add(n);
        return this;
    }

    Prod mul(double c){
        args.add(new Constant(c));
        return this;
    }


    @Override
    double evaluate() {
        double result =1;
        for (int i = 0; i < this.args.size(); i++) {
            result *= args.get(i).evaluate();
        }
        return sign*result;
    }
    int getArgumentsCount(){return args.size();}


    public String toString(){
        StringBuilder b =  new StringBuilder();
        Prod simple = this.simplify();
        if(sign<0)b.append("-");
        for (int i = 0; i < simple.args.size(); i++) {
            b.append(simple.args.get(i).toString());
            if(i<simple.args.size()-1) {
                b.append("*");
            }
        }
        return b.toString();
    }

    @Override
    Node diff(Variable var) {
        Sum r = new Sum();
        for(int i=0;i<args.size();i++){
            Prod m= new Prod();
            for(int j=0;j<args.size();j++){
                Node f = args.get(j);
                if(j==i)m.mul(f.diff(var));
                else m.mul(f);
            }
            if(!m.isZero())r.add(m);
        }
        if (r.getArgumentsCount() == 0 || r.isZero()) {
            return new Constant(0);
        }
        return r;
    }

    private Prod simplify() {
        Prod ans = new Prod();
        ans.sign = this.sign;
        Constant newCon = new Constant(1);
        for (Node n : args) {
            if(n instanceof Constant) {
                newCon.value = newCon.value*((Constant) n).value;
            }
        }
        ans.args.add(newCon);
        for (Node n: args) {
            if(!(n instanceof Constant)) {
                ans.args.add(n);
            }
        }
        return ans;
    }

    @Override
    boolean isZero() {
        for (Node n:this.args) {
            if(n.isZero()) return true;
        }
        return false;
    }
}