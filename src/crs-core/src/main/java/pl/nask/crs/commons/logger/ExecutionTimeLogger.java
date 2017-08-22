package pl.nask.crs.commons.logger;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

public class ExecutionTimeLogger  {
    static Logger logger = Logger.getLogger(ExecutionTimeLogger.class);

    public void logBefore(JoinPoint call){
        if(logger.isDebugEnabled()){
            logger.debug("before, "+call.toShortString() +" class: "+call.getTarget().toString());
        }
    }
    public void logAfterReturning(JoinPoint call){
        if(logger.isDebugEnabled()){
            logger.debug("after returning, "+call.toShortString() +" class: "+call.getTarget().toString());
        }
    }
    public void logAfterThrowing(JoinPoint call){
        if(logger.isDebugEnabled()){
            logger.debug("after throwing, "+call.toShortString() +" class: "+call.getTarget().toString());
        }
    }

}
