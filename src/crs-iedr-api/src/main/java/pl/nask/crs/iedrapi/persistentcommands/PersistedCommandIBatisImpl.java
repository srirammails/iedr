package pl.nask.crs.iedrapi.persistentcommands;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import pl.nask.crs.commons.dao.ibatis.SqlMapClientLoggingDaoSupport;

public class PersistedCommandIBatisImpl extends SqlMapClientLoggingDaoSupport implements PersistedCommandDAO {
	private static final String FIND_QUERY = "persistedCommands.findResponse";
	private static final String INSERT_QUERY = "persistedCommands.insertResponse";
	private final Logger log = Logger.getLogger(PersistedCommandIBatisImpl.class);
	
	@Override
	public String getResponse(String nh, String request) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("nichandle", nh);
		param.put("request", hash(request));
		
		return performQueryForObject(FIND_QUERY, param);
	}

	@Override
	public void storeResponse(String nicHandle, String request, String response) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nichandle", nicHandle);
		param.put("request", hash(request));
		param.put("response", response);
		param.put("date", new Date());
		
		performInsert(INSERT_QUERY, param);
	}

	private String hash(String request) {
		return DigestUtils.md5Hex(request);
	}


}
