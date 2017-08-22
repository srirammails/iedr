package pl.nask.crs.ticket.dao.ibatis.handlers;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import pl.nask.crs.ticket.operation.DomainOperation;
import pl.nask.crs.ticket.operation.DomainOperation.DomainOperationType;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * @author Patrycja Wegrzynowicz
 */
public class DomainOperationTypeHandler implements TypeHandlerCallback {
    final static private Logger logger = Logger.getLogger(DomainOperationTypeHandler.class);
    
    final static String REGISTRATION = "R";

    final static String DELETION = "D";

    final static String MODIFICATION = "M";

    final static String TRANSFER = "T";

    public void setParameter(ParameterSetter parameterSetter, Object o) throws SQLException {
        if (!(o instanceof DomainOperationType))
            return;

        DomainOperationType t = (DomainOperationType) o;
        
        switch (t) {
        case DEL:
            parameterSetter.setString(DELETION);
            break;
        case MOD:
            parameterSetter.setString(MODIFICATION);
            break;
        case REG:
            parameterSetter.setString(REGISTRATION);
            break;
        case XFER:
            parameterSetter.setString(TRANSFER);
            break;
        default:
            logger.warn("Unhandled DomainOperation.Type (Ticket type):" + t);        
        }
    }

    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String type = resultGetter.getString();
        if (REGISTRATION.equalsIgnoreCase(type)) {
        	return DomainOperationType.REG;
        } else if (DELETION.equalsIgnoreCase(type)) {
        	return DomainOperationType.DEL;
        } else if (MODIFICATION.equalsIgnoreCase(type)) {
        	return DomainOperationType.MOD;
        } else if (TRANSFER.equalsIgnoreCase(type)) {
            return DomainOperationType.XFER;
        } else {
            logger.warn("Cannot convert to DomainOperation.Type (Ticket type). Value: " + type);
            return null;
        }
    }

    public Object valueOf(String s) {
        return null;
    }
}
