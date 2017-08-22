package pl.nask.crs.history;

import pl.nask.crs.commons.utils.Validator;

import java.util.Date;

/**
 * @author Kasia Fulara
 */
public class HistoricalObject<OBJ> {

    public static final int UNSET_ID = -1;
    private long changeId;
    private OBJ object;
    private Date changeDate;
    private String changedBy;

    public HistoricalObject() {
        changeId = UNSET_ID;
    }

    public HistoricalObject(OBJ object, Date changeDate, String changedBy) {
        this(UNSET_ID, object, changeDate, changedBy);
    }

    public HistoricalObject(long changeId, OBJ object, Date changeDate, String changedBy) {
        Validator.assertNotNull(object, "historical object");
        Validator.assertNotNull(changeDate, "historical change date");
        Validator.assertNotNull(changedBy, "historical changed by");
        this.changeId = changeId;
        this.object = object;
        this.changeDate = changeDate;
        this.changedBy = changedBy;
    }

    public long getChangeId() {
        return changeId;
    }

    public void setChangeId(long changeId) {
        this.changeId = changeId;
    }

    public OBJ getObject() {
        return object;
    }

    public void setObject(OBJ object) {
        this.object = object;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
}
