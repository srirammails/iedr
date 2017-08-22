package pl.nask.crs.commons.dao.ibatis.handlers;

import java.sql.SQLException;
import java.util.Calendar;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 * @author Marianna Mysiorska
 */
public class ZeroDateNotNullHandler implements TypeHandlerCallback {

    public void setParameter(ParameterSetter parameterSetter, Object o)
            throws SQLException {
        if (o == null) {
            parameterSetter.setString("0000-00-00");
        } else {
            try {
                o = DateUtils.truncate(o, Calendar.SECOND);
            } catch (ClassCastException e) {
                Logger.getLogger(ZeroDateNotNullHandler.class).error("ZeroDateNotNullHandler passed in a object that is neither a Date nor a Calendar", e);
            }
            parameterSetter.setObject(o);
        }
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        try {
            return resultGetter.getObject();
        } catch (SQLException e) {
            Calendar cal = Calendar.getInstance();
            cal.set(1970,0,1,1,1,1);
            // getting the date only will cause getting milliseconds too even,
            // if they were not read from the database!
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
    }

    public Object valueOf(String s) {
        return null;
    }
}
