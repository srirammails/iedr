package pl.nask.crs.payment.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import pl.nask.crs.payment.CustomerType;

import java.sql.SQLException;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class CustomerTypeHandler implements TypeHandlerCallback {

    private final static int DIRECT_ACCOUNT_ID = 1;

    @Override
    public void setParameter(ParameterSetter setter, Object o) throws SQLException {
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        Integer type = resultGetter.getInt();
        if (type == DIRECT_ACCOUNT_ID) {
            return CustomerType.DIRECT;
        } else {
            return CustomerType.REGISTRAR;
        }
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }
}
