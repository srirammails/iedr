package pl.nask.crs.commons.config;

import java.sql.SQLException;

import pl.nask.crs.commons.config.ConfigEntry.ConfigValueType;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class EntryTypeHandler implements TypeHandlerCallback {

	@Override
	public void setParameter(ParameterSetter setter, Object parameter)
			throws SQLException {
		setter.setString(((ConfigValueType) parameter).name());
	}

	@Override
	public Object getResult(ResultGetter getter) throws SQLException {
		return ConfigValueType.valueOf(getter.getString());
	}

	@Override
	public Object valueOf(String s) {
		return s;
	}

}
