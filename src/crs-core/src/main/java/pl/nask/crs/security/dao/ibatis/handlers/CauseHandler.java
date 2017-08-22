package pl.nask.crs.security.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import pl.nask.crs.security.Cause;

import java.sql.SQLException;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class CauseHandler implements TypeHandlerCallback {
    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        String value = null;
        if (parameter instanceof Cause) {
            Cause type = (Cause) parameter;
            if (type.equals(Cause.INVALID_NIC)) {
                value = Cause.INVALID_NIC.name();
            } else if (type.equals(Cause.INVALID_PASSWORD)) {
                value = Cause.INVALID_PASSWORD.name();
            } else if (type.equals(Cause.INVALID_GA_PIN)) {
                value = Cause.INVALID_GA_PIN.name();
            }
        }
        setter.setString(value);
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String type = resultGetter.getString();
        if (Cause.INVALID_NIC.name().equalsIgnoreCase(type)) {
            return Cause.INVALID_NIC;
        } else if (Cause.INVALID_PASSWORD.name().equalsIgnoreCase(type)) {
            return Cause.INVALID_PASSWORD;
        } else if (Cause.INVALID_GA_PIN.name().equalsIgnoreCase(type)) {
            return Cause.INVALID_GA_PIN;
        } else {
            return null;
        }
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }
}
