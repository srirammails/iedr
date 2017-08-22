package pl.nask.crs.entities.dao.ibatis;

import pl.nask.crs.entities.EntityCategory;

public class InternalEntityCategory implements EntityCategory {
    private long id;
    private String name;

    public InternalEntityCategory() {
    }

    public InternalEntityCategory(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
