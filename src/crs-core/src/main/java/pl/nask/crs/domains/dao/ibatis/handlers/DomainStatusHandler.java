package pl.nask.crs.domains.dao.ibatis.handlers;

import java.sql.SQLException;

import pl.nask.crs.domains.Domain;
import pl.nask.crs.domains.DomainStatus;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * @author Kasia Fulara
 */
public class DomainStatusHandler implements TypeHandlerCallback {

    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
    	String value = null;
    	
    	if (parameter instanceof DomainStatus) {
    		DomainStatus status = (DomainStatus) parameter;
    		if (status.equals(DomainStatus.Active)) {
    			value = DomainStatus.Active.getStatusName();
    		} else if (status.equals(DomainStatus.Deleted)) {
            	value = DomainStatus.Deleted.getStatusName();
    		} else if (status.equals(DomainStatus.Suspended)) {
    			value = DomainStatus.Suspended.getStatusName();
    		} else if (status.equals(DomainStatus.PRA)) {
    			value = DomainStatus.PRA.getStatusName();
    		}
    	}
    	setter.setString(value);
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String status = resultGetter.getString();
        if (DomainStatus.Active.getStatusName().equalsIgnoreCase(status)) {
        	return DomainStatus.Active;
        } else if (DomainStatus.Deleted.getStatusName().equalsIgnoreCase(status)) {
        	return DomainStatus.Deleted;
        } else if (DomainStatus.Suspended.getStatusName().equalsIgnoreCase(status)) {
        	return DomainStatus.Suspended;
        } else if (DomainStatus.PRA.getStatusName().equalsIgnoreCase(status)) {
        	return DomainStatus.PRA;
        } else {
        	return null;
        }
    }

    public Object valueOf(String s){
        return null;
    }
}
