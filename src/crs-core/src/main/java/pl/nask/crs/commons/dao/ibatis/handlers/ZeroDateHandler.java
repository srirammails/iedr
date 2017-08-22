package pl.nask.crs.commons.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Calendar;

/**
 * @author Kasia Fulara
 */
public class ZeroDateHandler implements TypeHandlerCallback {

    public void setParameter(ParameterSetter parameterSetter, Object o)
            throws SQLException {
        if (o == null) {
            parameterSetter.setString("0000-00-00");
        } else {
            try {
                o = DateUtils.truncate(o, Calendar.SECOND);
            } catch (ClassCastException e) {
                Logger.getLogger(ZeroDateHandler.class).error("ZeroDateHandler passed in a object that is neither a Date nor a Calendar", e);
            }
            parameterSetter.setObject(o);
        }
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        try {
            return resultGetter.getObject();
        } catch (SQLException e) {
            return null;
        }
    }

    public Object valueOf(String s) {
        return null;
    }
}
