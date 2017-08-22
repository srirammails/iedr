package pl.nask.crs.domains.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import pl.nask.crs.domains.NotificationType;

import java.sql.SQLException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class NotificationTypeHandler implements TypeHandlerCallback {
    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        String value = null;

        if (parameter instanceof NotificationType) {
            NotificationType type = (NotificationType)parameter;
            if (type.equals(NotificationType.RENEWAL)) {
                value = NotificationType.RENEWAL.getDesc();
            } else if (type.equals(NotificationType.SUSPENSION)) {
                value = NotificationType.SUSPENSION.getDesc();
            }
        }
        setter.setString(value);
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String type = resultGetter.getString();
        if (NotificationType.RENEWAL.getDesc().equalsIgnoreCase(type)) {
            return NotificationType.RENEWAL;
        } else if (NotificationType.SUSPENSION.getDesc().equalsIgnoreCase(type)) {
            return NotificationType.SUSPENSION;
        } else {
            return null;
        }
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }
}
