public class Monomial {
    private Scalar coe;
    private int exp;

    public Monomial(Scalar x, int expo){
        coe=x;
        exp=expo;
    }
    public boolean isMatch(Monomial m){
        if(m==null) throw new IllegalArgumentException();
        return this.coe.isMatch(m.coe);
    }
    public boolean canAdd(Monomial m){
        return isMatch(m)&&this.exp==m.exp;
    }
    public Monomial add(Monomial m){
        if(canAdd(m)) return null;
        return new Monomial(coe.add(m.coe),exp);
    }
    public Monomial mul(Monomial m){
        if(canAdd(m)) return null;
        return new Monomial(coe.mul(m.coe),exp+m.exp);
    }
    public Scalar evalute(Scalar scalar){
        if(scalar==null) throw new IllegalArgumentException("");
        return scalar.power(exp).mul(coe);
    }
    public Monomial derivative(){
        return new Monomial(coe.mul(exp),exp-1);
    }
    public int sign(){
        return coe.sign();
    }
    public String toString(){
        String str="";
        if(exp==0) str=coe+"";
        else if(exp ==1) str=coe+"x";
        else str=coe+"x^"+exp;
        return str;
    }
}
