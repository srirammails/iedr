package pl.nask.crs.iedrapi;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.log4j.NDCAwareFileAppender;

// TODO: refactoring needed - all settings should be stored in a configuration file and only accessed by this class. 
public class IedrApiConfig {		
	
    public static int getPageSize() {
        return 20;
    }

	public static NDCAwareFileAppender getUserAwareAppender() {
		return (NDCAwareFileAppender) Logger.getRootLogger().getAppender("userAware");
	}
}
