package pl.nask.crs.entities.dao.ibatis;

import pl.nask.crs.entities.EntityClass;

public class InternalEntityClass implements EntityClass {

    private long id;
    private String name;

    public InternalEntityClass() {
    }

    public InternalEntityClass(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
