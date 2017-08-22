package pl.nask.crs.payment;

/**
 * @author: Marcin Tkaczyk
 */
public class LimitsPair {

    private double min;
    private double max;

    public LimitsPair(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
