package pl.nask.crs.payment.dao.ibatis.objects;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class InternalReservationTotals {

    private long totalResults;

    private double totalAmount;

    private double totalVat;

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalVat() {
        return totalVat;
    }

    public void setTotalVat(double totalVat) {
        this.totalVat = totalVat;
    }
}
