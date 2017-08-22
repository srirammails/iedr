package pl.nask.crs.ticket.dao.ibatis.objects;

import pl.nask.crs.ticket.AdminStatus;
import pl.nask.crs.ticket.FinancialStatus;
import pl.nask.crs.ticket.TechStatus;
import pl.nask.crs.ticket.operation.FailureReason;

/**
 * @author Patrycja Wegrzynowicz
 */
public class InternalStatus implements AdminStatus, TechStatus, FailureReason, FinancialStatus {

    private int id;

    private String description;

    private int dataField;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDataField() {
        return dataField;
    }

    public void setDataField(int dataField) {
        this.dataField = dataField;
    }

    public String toString() {
        return "id: [" + id + "] description: [" + description + "]";
    }
}
