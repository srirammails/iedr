package pl.nask.crs.ticket.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import pl.nask.crs.ticket.FinancialStatusEnum;

import java.sql.SQLException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class FinancialStatusHandler implements TypeHandlerCallback {
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        int value = 0;
                                                                    
        if (parameter instanceof FinancialStatusEnum) {
            FinancialStatusEnum status = (FinancialStatusEnum) parameter;            
            value = status.getId();
        }
        setter.setInt(value);
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        int status = resultGetter.getInt();
        try {
        	return FinancialStatusEnum.valueForId(status);
        } catch (IllegalArgumentException e) {
        	return null;
        }      
    }

    public Object valueOf(String s){
        return null;
    }
}
