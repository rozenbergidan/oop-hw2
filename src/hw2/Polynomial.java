package hw2;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Polynomial {
    private List<Monomial> monomials;

    public Polynomial() {
        monomials = new LinkedList<>();
    }

    // return polynomial as describe with "type" and "input"
    public Polynomial build(char type, String input) {
        Polynomial ans=new Polynomial();
        String[] coefficients = input.split(" ");
        int exp = 0;
        for (String coefficient : coefficients) {
            if (coefficient.length() > 0) {
                if (type == 'Q') {
                    String[] n = coefficient.split("/");
                    int a = Integer.parseInt(n[0]);
                    if (a != 0) {
                        int b = 1;
                        if (n.length > 1) b = Integer.parseInt(n[1]);
                        RationalScalar c = new RationalScalar(a, b);
                        ans.monomials.add(new Monomial(c, exp));
                    }
                } else {
                    double v = Double.parseDouble(coefficient);
                    if (v != 0) {
                        RealScalar c = new RealScalar(v);
                        ans.monomials.add(new Monomial(c, exp));
                    }
                }
                exp++;
            }

        }
        return ans;
    }

    // Check is "p" is the same type polynomial as "this"
    public boolean isMatch(Polynomial p) {
        if (p == null) throw new IllegalArgumentException();
        Iterator<Monomial> iterThis = monomials.iterator();
        Iterator<Monomial> iterP = p.monomials.iterator();
        while (iterP.hasNext() & iterThis.hasNext()) {
            if (!iterThis.next().isMatch(iterP.next()))
                return false;
        }
        return true;

    }

    // function to assist add function
    private Polynomial copy(){
        Polynomial ans=new Polynomial();
        for(Monomial mono: monomials)
            ans.monomials.add(mono);
        return ans;
    }

    // return the sum of two polynomials
    public Polynomial add(Polynomial p) {
        if (!isMatch(p)) return null; // If true, the polynoms not matching.
        Polynomial ans = new Polynomial();
        if (p.monomials.isEmpty()) ans = copy(); // If true, return polynom equals to "this"
        else { //using iterators, add the polynoms
            Iterator<Monomial> thisIter = monomials.iterator();
            Iterator<Monomial> pIter = p.monomials.iterator();
            Monomial thisCurrent = null;
            Monomial pCurrent = null;
            if (thisIter.hasNext()){
                thisCurrent = thisIter.next();
            }

            if (pIter.hasNext()){
                pCurrent = pIter.next();
            }

            while (thisCurrent != null & pCurrent != null){
                if (thisCurrent.getExp() < pCurrent.getExp()) {
                    ans.monomials.add(thisCurrent);
                    if (thisIter.hasNext()) thisCurrent = thisIter.next();
                    else thisCurrent = null;
                }
                else {
                    if (thisCurrent.getExp() > pCurrent.getExp()) {
                        ans.monomials.add(pCurrent);
                        if (pIter.hasNext()) pCurrent = pIter.next();
                        else pCurrent = null;
                    }
                    else {
                        if (thisCurrent.getExp() == pCurrent.getExp()) {
                            Monomial temp = pCurrent.add(thisCurrent);
                            ans.monomials.add(temp);
                            if (pIter.hasNext()) pCurrent = pIter.next();
                            else pCurrent = null;
                            if (thisIter.hasNext()) thisCurrent = thisIter.next();
                            else thisCurrent = null;
                        }
                    }
                }
            }
            while (thisCurrent != null){
                ans.monomials.add(thisCurrent);
                if (thisIter.hasNext()) thisCurrent = thisIter.next();
                else thisCurrent = null;
            }
            while (pCurrent != null){
                ans.monomials.add(pCurrent);
                if (pIter.hasNext()) pCurrent = pIter.next();
                else pCurrent = null;
            }

        }
        return ans;
    }

    // return the multiply of two polynomials
    public Polynomial mul(Polynomial p) {
        if (!isMatch(p)) return null;
        Polynomial ans = new Polynomial();
        Iterator<Monomial> thisIter = monomials.iterator();
        while (thisIter.hasNext()) { // For each monomial in this, multiply with each monomial if p
            Monomial thismono = thisIter.next();
            Polynomial temp = new Polynomial();
            for (Monomial mono : p.monomials) {
                temp.monomials.add(thismono.mul(mono));
            }
            ans = temp.add(ans);
        }
        return ans;

    }
    //evaluate the polynomial value
    public Scalar evaluate(Scalar scalar) {
        Scalar ans = scalar.mul(1);
        Scalar nega = scalar.mul(-1);
        for (Monomial mono : monomials) {
            ans = ans.add(mono.evalute(scalar));
        }
        ans = ans.add(nega);
        return ans;
    }

    // return the derivative of the polynomial
    public Polynomial derivative() {
        Polynomial ans = new Polynomial();
        for (Monomial mono : monomials) {
            if (mono.derivative() != null)
                ans.monomials.add(mono.derivative());
        }
        return ans;
    }

    public String toString() {
        String str = "";
        for (Monomial mono : monomials) {
            if (mono.sign() == 1)
                str = str + "+" + mono.toString();
            else
                str = str + mono.toString();
        }
        if (str.length() == 0){
            str = "0";
        }
        else {
            if (str.charAt(0) == '+') str = str.substring(1);
        }
        return str;
    }


}
