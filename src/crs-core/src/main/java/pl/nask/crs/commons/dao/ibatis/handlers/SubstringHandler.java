package pl.nask.crs.commons.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;

import java.sql.SQLException;

/**
 * @author Kasia Fulara
 */
public class SubstringHandler implements TypeHandlerCallback {

    public void setParameter(ParameterSetter arg0, Object arg1) throws SQLException {
        arg0.setString((arg1 == null ? "" : "%" + arg1.toString()) + "%");
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object valueOf(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
