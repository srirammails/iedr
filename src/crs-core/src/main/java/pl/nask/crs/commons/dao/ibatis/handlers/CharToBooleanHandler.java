package pl.nask.crs.commons.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * @author Kasia Fulara
 */
public class CharToBooleanHandler implements TypeHandlerCallback {
    public void setParameter(ParameterSetter setter, Object o) throws SQLException {
        
        String val = "N";
        if (o instanceof String) {
        	switch(((String)o).charAt(0)) {
            	case't':
            	case'T':
            	case'y':
            	case'Y':
            	case'1':
            		val="Y";
                }
        } else if (o instanceof Boolean && (Boolean) o) {
        	val = "Y";
        }        
    
        setter.setString(val);
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String value = resultGetter.getString();
        if (value == null || value.length() == 0)
            return false;
        switch (value.toUpperCase().toCharArray()[0]) {
            case '1':
            case 'Y':
                return true;
            case '0':
            case 'N':
            default:
                return false;
        }
    }

    public Object valueOf(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
