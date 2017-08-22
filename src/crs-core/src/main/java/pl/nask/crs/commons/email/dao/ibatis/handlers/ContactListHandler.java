package pl.nask.crs.commons.email.dao.ibatis.handlers;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Kasia Fulara
 */
public class ContactListHandler implements TypeHandlerCallback {

    public void setParameter(ParameterSetter parameterSetter, Object o) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Parses addressee list
     *
     * @param resultGetter
     * @return List<String> returns list, if there was no addressee list is empty
     * @throws SQLException
     */
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        List<String> addresseeList = new ArrayList<String>();
        String getter = resultGetter.getString();
        if (getter == null)
            return addresseeList;
        StringTokenizer tokenizer = new StringTokenizer(getter, ",");
        while (tokenizer.hasMoreTokens()) {
            addresseeList.add(tokenizer.nextToken());
        }
        return addresseeList;
    }

    public Object valueOf(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
