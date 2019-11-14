public class Exp extends Node{
    double e = 2.718281828459045235360287471353;
    Variable arg;

    Exp(Variable arg) {
        this.arg=arg;
    }

    @Override
    double evaluate() {
        return arg.value!=null?sign*Math.pow(e,arg.value):0;
    }

    @Override
    Node diff(Variable var) {
        Exp ans = new Exp(this.arg);
        if(this.arg.sign == -1)ans.minus();
        return ans;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if(sign<0)b.append("-");
        b.append(e);
        boolean useBracket = false;
        if(arg.sign<0)useBracket = true;
        String argString = arg.toString();
        b.append("^");
        if(useBracket)b.append("(");
        b.append(argString);
        if(useBracket)b.append(")");
        return b.toString();
    }

    @Override
    boolean isZero() {
        return false;
    }
}
