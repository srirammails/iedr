package pl.nask.crs.payment.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import pl.nask.crs.payment.DepositTransactionType;

import java.sql.SQLException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class DepositTransactionTypeHandler implements TypeHandlerCallback {
    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        String value = null;

        if (parameter instanceof DepositTransactionType) {
            DepositTransactionType type = (DepositTransactionType)parameter;
            if (type.equals(DepositTransactionType.INIT)) {
                value = DepositTransactionType.INIT.name();
            } else if (type.equals(DepositTransactionType.TOPUP)) {
                value = DepositTransactionType.TOPUP.name();
            } else if (type.equals(DepositTransactionType.SETTLEMENT)) {
                value = DepositTransactionType.SETTLEMENT.name();
            } else if (type.equals(DepositTransactionType.MANUAL)) {
                value = DepositTransactionType.MANUAL.name();
            }
        }
        setter.setString(value);
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String type = resultGetter.getString();
        if (DepositTransactionType.INIT.name().equalsIgnoreCase(type)) {
            return DepositTransactionType.INIT;
        } else if (DepositTransactionType.TOPUP.name().equalsIgnoreCase(type)) {
            return DepositTransactionType.TOPUP;
        } else if (DepositTransactionType.SETTLEMENT.name().equalsIgnoreCase(type)) {
            return DepositTransactionType.SETTLEMENT;
        } else  if (DepositTransactionType.MANUAL.name().equalsIgnoreCase(type)) {
            return DepositTransactionType.MANUAL;
        } else {
            return null;
        }
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }
}
