public class Variable extends Node {
    String name;
    Double value;
    Variable(String name){
        this.name = name;
    }
    Variable(String name, double value){
        this.name=name;
        this.value=value;
        if(this.value<0) {
            minus();
            this.value=this.value*(-1);
        }
    }
    void setValue(double d){
        value = d;
        if(value<0) {
            minus();
            value=value*(-1);
        }
    }


    @Override
    double evaluate() {
        return value!=null?sign*value:0;
    }


    @Override
    public String toString() {
        String sgn=sign<0?"-":"";
        return sgn+name;
    }

    @Override
    Node diff(Variable var) {
        if(var.name.equals(name))return new Constant(sign);
        else return new Constant(0);
    }

    @Override
    boolean isZero() {
        if(this.value==null)return false;
        if(this.value==0)return true;
        return false;
    }
}