package pl.nask.crs.nichandle.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;


/**
 * @author Marianna Mysiorska
 */
public class BillCIndHandler implements TypeHandlerCallback {

    private static final String billCY = "Y";
    private static final String billCN = "";

    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
          if (parameter instanceof Boolean && ((Boolean) parameter)) {
            setter.setString(billCY);
          } else {
            setter.setString(billCN);
          }
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String billCInd = resultGetter.getString();
        if (billCInd == null) {
            return false;
        } else { 
        	return billCInd.equalsIgnoreCase(billCY);
        }
    }

    public Object valueOf(String s) {
        return null;
    }
}

