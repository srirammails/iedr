package pl.nask.crs.commons.email;

import java.util.Date;

public class EmailGroup {

    public static final long EMPTY_ID = -1l;

    protected long id;
    protected String name;
    protected Boolean visible;
    protected Date changeDate;

    public EmailGroup() {
        id = EMPTY_ID;
    }

    public EmailGroup(long id, String name, Boolean visible) {
        this.id = id;
        this.name = name;
        this.visible = visible;
    }

    public EmailGroup(String name) {
        this(EMPTY_ID, name, true);
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

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
