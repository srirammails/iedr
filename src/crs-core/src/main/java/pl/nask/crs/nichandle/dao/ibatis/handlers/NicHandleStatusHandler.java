package pl.nask.crs.nichandle.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;

import java.sql.SQLException;

import pl.nask.crs.nichandle.NicHandle;

/**
 * @author Marianna Mysiorska, Artur Gniadzik
 */
public class NicHandleStatusHandler implements TypeHandlerCallback {

    final static String ACTIVE = "Active";
    final static String DELETED = "Deleted";
    final static String RENEW = "Renew";
    final static String SUSPENDED = "Suspended";
    final static String NEW = "New";


    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
    	String value = null;
    	if (parameter instanceof NicHandle.NHStatus) {
    		NicHandle.NHStatus status = (NicHandle.NHStatus) parameter;
          if (status.equals(NicHandle.NHStatus.Active)) {
            value = ACTIVE;
          } else if (status.equals(NicHandle.NHStatus.Deleted)) {
            value = DELETED;
          } else if (status.equals(NicHandle.NHStatus.Renew)) {
            value = RENEW;
          } else if (status.equals(NicHandle.NHStatus.Suspended)) {
            value = SUSPENDED;
          } else if (status.equals(NicHandle.NHStatus.New)) {
        	value = NEW;
          }
    	}
    	setter.setString(value);
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String status = resultGetter.getString();
        if (ACTIVE.equalsIgnoreCase(status)) {
        	return NicHandle.NHStatus.Active;
        } else if (DELETED.equalsIgnoreCase(status)) {
        	return NicHandle.NHStatus.Deleted;
        } else if (RENEW.equalsIgnoreCase(status)) {
        	return NicHandle.NHStatus.Renew;
        } else if (SUSPENDED.equalsIgnoreCase(status)) {
        	return NicHandle.NHStatus.Suspended;
        } else if (NEW.equalsIgnoreCase(status)) {
        	return NicHandle.NHStatus.New;
        } else {
        	return null;
        }
    }

    public Object valueOf(String s){
        return null;
    }
}
 