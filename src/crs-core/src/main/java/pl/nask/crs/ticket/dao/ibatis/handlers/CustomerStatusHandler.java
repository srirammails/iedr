package pl.nask.crs.ticket.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import pl.nask.crs.ticket.CustomerStatusEnum;

import java.sql.SQLException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class CustomerStatusHandler implements TypeHandlerCallback {

    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        int value = 0;

        if (parameter instanceof CustomerStatusEnum) {
            CustomerStatusEnum status = (CustomerStatusEnum) parameter;
            if (status.equals(CustomerStatusEnum.NEW)) {
                value = CustomerStatusEnum.NEW.getId();
            } else if (status.equals(CustomerStatusEnum.CANCELLED)) {
                value = CustomerStatusEnum.CANCELLED.getId();
            }
        }
        setter.setInt(value);
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        int status = resultGetter.getInt();
        if (CustomerStatusEnum.NEW.getId() == status) {
            return CustomerStatusEnum.NEW;
        } else if (CustomerStatusEnum.CANCELLED.getId() == status) {
            return CustomerStatusEnum.CANCELLED;
        } else {
            return null;
        }
    }

    public Object valueOf(String s){
        return null;
    }

}
