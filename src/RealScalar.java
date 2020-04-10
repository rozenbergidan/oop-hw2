import java.text.DecimalFormat;

public class RealScalar extends Scalar {
    private double v;

    public RealScalar(double x) {
        v = x;
    }


    public double getValue() {
        return v;
    }

    public Scalar add(Scalar s) {
        if (s == null || !isMatch(s)) return null;
        return new RealScalar(v + ((RealScalar) s).v);
    }

    public Scalar mul(Scalar s) {
        if (s == null || !isMatch(s)) return null;
        return new RealScalar(v * ((RealScalar) s).v);
    }

    public Scalar mul(int i) {
        return new RealScalar(v * i);
    }

    public Scalar power(int exp) {
        double x = 1;
        for (int i = 0; i < exp; i++)
            x *= v;
        return new RealScalar(x);

    }

    public int sign() {
        if (v > 0) return 1;
        else if (v < 0) return -1;
        else return 0; // should not happend
    }

    public String toString() {
        DecimalFormat df3 = new DecimalFormat("#.###");
        String str = df3.format(v);
        return str;
    }
}
