package pl.nask.crs.payment.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import pl.nask.crs.payment.PaymentMethod;

import java.sql.SQLException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class PaymentMethodHandler implements TypeHandlerCallback {
    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        String value = null;

        if (parameter instanceof PaymentMethod) {
            PaymentMethod type = (PaymentMethod)parameter;
            if (type.equals(PaymentMethod.ADP)) {
                value = PaymentMethod.ADP.getFullName();
            } else if (type.equals(PaymentMethod.CC)) {
                value = PaymentMethod.CC.getFullName();
            } else if (type.equals(PaymentMethod.DEB)) {
                value = PaymentMethod.DEB.getFullName();
            }
        }
        setter.setString(value);
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String type = resultGetter.getString();
        if (PaymentMethod.ADP.getFullName().equalsIgnoreCase(type)) {
        	return PaymentMethod.ADP;
        } else if (PaymentMethod.CC.getFullName().equalsIgnoreCase(type)) {
        	return PaymentMethod.CC;
        } else if (PaymentMethod.DEB.getFullName().equalsIgnoreCase(type)) {
        	return PaymentMethod.DEB;
        } else {
        	return null;
        }
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }
}
