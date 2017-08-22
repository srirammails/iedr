package pl.nask.crs.commons.dao.ibatis.handlers;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;

public class NullableCharToBooleanHandler extends CharToBooleanHandler {
	@Override
	public Object getResult(ResultGetter resultGetter) throws SQLException {
		if (resultGetter.getString() == null || resultGetter.getString().trim().length() == 0) {
			return null;
		} else {
			return super.getResult(resultGetter);
		}
	}
	
	@Override
	public void setParameter(ParameterSetter setter, Object o)
			throws SQLException {
		if (o == null) {
			setter.setObject(null);
		} else {
			super.setParameter(setter, o);
		}
	}
}
