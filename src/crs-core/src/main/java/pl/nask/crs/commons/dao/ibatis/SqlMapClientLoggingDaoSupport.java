package pl.nask.crs.commons.dao.ibatis;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;
import java.util.Map;

public class SqlMapClientLoggingDaoSupport extends SqlMapClientDaoSupport {
    private final static Logger log = Logger.getLogger(SqlMapClientLoggingDaoSupport.class);

    protected int performUpdate(String queryId, Object dto) {
        try {
            logQuery(queryId);
            int i = getSqlMapClientTemplate().update(queryId, dto);
            log.debug("update (" + queryId + ") returned " + i);
            return i;
        } catch (RuntimeException e) {
            logSqlError(queryId, dto, e);
            throw e;
        }
    }

    protected int performDelete(String queryId, Object param) {
        try {
            logQuery(queryId);
            int i = getSqlMapClientTemplate().delete(queryId, param);
            log.debug("delete (" + queryId + ") returned " + i);
            return i;
        } catch (RuntimeException e) {
            logSqlError(queryId, param, e);
            throw e;
        }
    }

    protected <T> T performInsert(String queryId, Object parameter) {
        try {
            logQuery(queryId);
            return (T) getSqlMapClientTemplate().insert(queryId, parameter);
        } catch (RuntimeException e) {
            logSqlError(queryId, parameter, e);
            throw e;
        }
    }

    protected <T> List<T> performQueryForList(String queryId, Map<String, Object> parameterMap) {
        try {
            logQuery(queryId);
            List<T> res = getSqlMapClientTemplate().queryForList(queryId, parameterMap);
            return res;
        } catch (RuntimeException e) {
            logSqlError(queryId, parameterMap, e);
            throw e;
        }
    }

    protected <T> List<T> performQueryForList(String queryId, Object param) {
        try {
            logQuery(queryId);
            List<T> res = getSqlMapClientTemplate().queryForList(queryId, param);
            return res;
        } catch (RuntimeException e) {
            logSqlError(queryId, param, e);
            throw e;
        }
    }

    protected <T> List<T> performQueryForList(String queryId) {
        try {
            logQuery(queryId);
            List<T> res = getSqlMapClientTemplate().queryForList(queryId);
            return res;
        } catch (RuntimeException e) {
            logSqlError(queryId, null, e);
            throw e;
        }
    }

    protected <T> T performQueryForObject(String queryId, Map<String, Object> parameterMap) {
        try {
            logQuery(queryId);
            Object res = getSqlMapClientTemplate().queryForObject(queryId, parameterMap);
            return (T) res;
        } catch (RuntimeException e) {
            logSqlError(queryId, parameterMap, e);
            throw e;
        }
    }

    protected <T> T performQueryForObject(String queryId, Object parameter) {
        try {
            logQuery(queryId);
            Object res = getSqlMapClientTemplate().queryForObject(queryId, parameter);
            return (T) res;
        } catch (RuntimeException e) {
            logSqlError(queryId, parameter, e);
            throw e;
        }
    }

    protected void logSqlError(String queryId, Map parameterMap, Exception e) {
        log.warn("Error occurred while performing sql query. ");
        log.warn("QueryId: " + queryId);
        log.warn("ParameterMap: " + parameterMap);
        log.warn("Exception: " + e.getMessage());
    }

    protected void logSqlError(String queryId, Object param, Exception e) {
        log.warn("Error occurred while performing sql query. ");
        log.warn("QueryId: " + queryId);
        log.warn("Parameter: " + param);
        log.warn("Exception: " + e.getMessage());
    }

    protected void logQuery(String queryId) {
        log.debug("About to execute mapped query: " + queryId);
    }
}
