package pl.nask.crs.payment.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import pl.nask.crs.payment.OperationType;

import java.sql.SQLException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class ReservationTypeHandler implements TypeHandlerCallback {
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
    	String value = null;

    	if (parameter instanceof OperationType) {
    		OperationType type = (OperationType) parameter;
    		if (type.equals(OperationType.REGISTRATION)) {
    			value = OperationType.REGISTRATION.getTypeName();
    		} else if (type.equals(OperationType.RENEWAL)) {
                value = OperationType.RENEWAL.getTypeName();
    		} else if (type.equals(OperationType.TRANSFER)) {
                value = OperationType.TRANSFER.getTypeName();
    		}
    	}
    	setter.setString(value);
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String type = resultGetter.getString();
        if (OperationType.REGISTRATION.getTypeName().equalsIgnoreCase(type)) {
        	return OperationType.REGISTRATION;
        } else if (OperationType.RENEWAL.getTypeName().equalsIgnoreCase(type)) {
        	return OperationType.RENEWAL;
        } else if (OperationType.TRANSFER.getTypeName().equalsIgnoreCase(type)) {
        	return OperationType.TRANSFER;
        } else {
        	return null;
        }
    }

    public Object valueOf(String s){
        return null;
    }

}
