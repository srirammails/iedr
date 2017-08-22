package pl.nask.crs.commons.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;

public class StringPrefixHandler implements TypeHandlerCallback {

    public Object getResult(ResultGetter arg0) throws SQLException {
        return null;
    }

    public void setParameter(ParameterSetter arg0, Object arg1)
            throws SQLException {
        arg0.setString((arg1 == null ? "" : arg1.toString()) + "%");
    }

    public Object valueOf(String arg0) {
        return null;
    }

}
