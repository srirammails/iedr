package pl.nask.crs.price;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PriceNotFoundException extends Exception {

    private String id;

    public PriceNotFoundException(String id) {
        super("Price not found");
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
