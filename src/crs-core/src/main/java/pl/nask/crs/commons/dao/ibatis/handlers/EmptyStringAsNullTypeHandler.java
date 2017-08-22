package pl.nask.crs.commons.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;

public class EmptyStringAsNullTypeHandler implements TypeHandlerCallback {
    public void setParameter(ParameterSetter setter, Object o) throws SQLException {

        String val = null;
        if (o instanceof String && !((String) o).isEmpty()) {
            val = (String) o;
        }

        setter.setString(val);
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String value = resultGetter.getString();
        if (value == null || value.isEmpty())
            return null;
        return value;
    }

    public Object valueOf(String s) {
        return null;
    }
}
