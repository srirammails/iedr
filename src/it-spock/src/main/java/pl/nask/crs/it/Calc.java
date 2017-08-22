package pl.nask.crs.it;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class Calc {
    private int totalWeight = 0;

    public void addItem(int itemWeight) {
        totalWeight = totalWeight + itemWeight;
    }

    public int getTotalWeight() {
        return totalWeight;
    }
}
