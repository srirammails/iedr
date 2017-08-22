package pl.nask.crs.commons.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.Calendar;

public class TruncatedDateHandler implements TypeHandlerCallback {

    @Override
    public void setParameter(ParameterSetter parameterSetter, Object o) throws SQLException {
        Object val = o;
        if (o != null) {
            try {
                val = DateUtils.truncate(o, Calendar.SECOND);
            } catch (ClassCastException e) {
                Logger.getLogger(TruncatedDateHandler.class).error("TruncatedDateHandler used with data that is not a Date or a Calendar", e);
            }
        }
        parameterSetter.setObject(val);
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        return resultGetter.getObject();
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }

}
