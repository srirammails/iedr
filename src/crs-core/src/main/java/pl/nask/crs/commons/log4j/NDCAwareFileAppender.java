package pl.nask.crs.commons.log4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.FileAppender;
import org.apache.log4j.MDC;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.rolling.RollingFileAppender;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.LoggingEvent;

import pl.nask.crs.commons.Pair;

/**
 * Log Appender that creates log files for the values from the MDC.
 * 
 * @author Artur Gniadzik
 *
 */
public class NDCAwareFileAppender extends WriterAppender {

	private static final String APPENDER_NAME = "APPENDER_NAME";
	private static final String EVENT_BUFFER = "EVENT_BUFFER_NAME";
	private static final String EVENT_OBSERVER= "EVENT_OBSERVER_NAME";
    private static final String DEFAULT_FILE_NAME_PATTERN = ".%d.log.gz";
    
	private String activeFile;
    private String fileNamePattern;

	private final static Object lock = new Object();
	
	// appenders global registry
	private static Map<String, Pair<FileAppender, Integer>> appenders = new HashMap<String, Pair<FileAppender,Integer>>();
	private static Map<String, CustomRollingPolicy> rollingTriggeringPolicies = new HashMap<String, CustomRollingPolicy>();
	
	@Override
	public void append(LoggingEvent event) {
		synchronized(lock) {
			FileAppender appender = getFileAppender();
			if (appender != null) {				
				flushEventBuffer(appender);				
				appender.append(event);
				EventObserver observer = (EventObserver) MDC.get(EVENT_OBSERVER);
				if (observer != null) {
					observer.append(event);
				}
			} else {
				storeInEventBuffer(event);
			}
		}		
	}
	
	private void storeInEventBuffer(LoggingEvent event) {
		List<LoggingEvent> l = (List<LoggingEvent>) MDC.get(EVENT_BUFFER);
		if (l != null) {
			l.add(event);		
		}
	}

	public void initEventBuffer() {
		// replace old event buffer with the old one
		MDC.put(EVENT_BUFFER, new ArrayList<LoggingEvent>());
	}
	
	private void flushEventBuffer(FileAppender appender) {
		EventObserver observer = (EventObserver) MDC.get(EVENT_OBSERVER);
		List<LoggingEvent> l = (List<LoggingEvent>) MDC.get(EVENT_BUFFER);
		if (l == null)
			return;
		
		for (LoggingEvent e: l) {
			appender.append(e);
			if (observer != null) {
				observer.append(e);
			}
		}
		l.clear();
		MDC.remove(EVENT_BUFFER);
	}

	private FileAppender getFileAppender() {
		Pair<FileAppender, Integer> p = getAppenderPair();
		if (p == null)
			return null;
		else
			return p.getLeft();
	}	
	
	private Pair<FileAppender, Integer> getAppenderPair() {
		String n = (String) MDC.get(APPENDER_NAME);
		
		if (n == null)
			return null;
		else
			return appenders.get(n);
	}

	private void setAppenderPair(Pair<FileAppender, Integer> p) {
		String n = (String) MDC.get(APPENDER_NAME);
		if (n == null) {
			errorHandler.error("APPENDER_NAME not set in MDC");
		} else {
			if (p == null) {
				appenders.remove(n);
			} else {
				appenders.put(n, p);
			}
		}
	}

    private CustomRollingPolicy getRollingTriggeringPolicy() {
        String name = (String) MDC.get(APPENDER_NAME);
        CustomRollingPolicy policy = rollingTriggeringPolicies.get(name);
        if (policy == null) {
            policy = initPolicy(name);
            rollingTriggeringPolicies.put(name, policy);
        }
        return policy;
    }

    private CustomRollingPolicy initPolicy(String name) {
        CustomRollingPolicy policy = new CustomRollingPolicy();
        policy.setFileNamePattern(makeFileNamePattern(name));
        policy.activateOptions();
        return policy;
    }

    public void registerAppender(String name) {
    	registerAppender(name, null);
    }
    
    /**
	 * Registers appender with the given name in the global registry (or increase count if the appender was already registered).
	 * A thread may register appender once. When the appender is registered, it must be unregistered before new registration may take place.
	 * @param name
	 */
	public void registerAppender(String name, EventObserver eo) {
		if (name == null) {
			throw new IllegalArgumentException("Appender name cannot be null");
		}
        name = name.toUpperCase();
		
		synchronized (lock) {	
			String oldn = (String) MDC.get(APPENDER_NAME);

			// fresh registration
			if (oldn == null) {
				MDC.put(APPENDER_NAME, name);
				if (eo != null) {
					MDC.put(EVENT_OBSERVER, eo);
				}
				
				Pair<FileAppender, Integer> p = getAppenderPair();

				if (p == null) {
					// no other thread uses this appender
                    CustomRollingPolicy policy = getRollingTriggeringPolicy();
					FileAppender f = createFileAppender(name, policy);
					setAppenderPair(new Pair<FileAppender, Integer>(f, 1));			
					flushEventBuffer(f);					
				} else {
					p.setRight(p.getRight() + 1);				
				}
					
			} else {
				// 	this thread has already registered an appender, retreat			
				return;
			}
		}
	}
	
	private FileAppender createFileAppender(String name, CustomRollingPolicy policy) {
		RollingFileAppender fa = new RollingFileAppender();
        fa.setRollingPolicy(policy);
		fa.setLayout(getLayout());
		fa.setAppend(true);
		fa.setBufferedIO(true);
		fa.setName(name);
		fa.setFile(makeActiveFileName(name));
		fa.activateOptions();
		return fa;
	}

	private String makeActiveFileName(String name) {
		if (activeFile == null) {
			return name + ".log";
		} else {
			if (activeFile.contains("#{context}")) {
				return activeFile.replaceAll("#\\{context\\}", name);
			} else {
				return activeFile.replaceFirst("\\.log", name+".log");
			}
		}
	}

    private String makeFileNamePattern(String name) {
        if (fileNamePattern == null) {
            return name + DEFAULT_FILE_NAME_PATTERN;
        } else {
            if (fileNamePattern.contains("#{context}")) {
                return fileNamePattern.replaceAll("#\\{context\\}", name);
            } else {
                return fileNamePattern.replaceFirst("\\.log", name + DEFAULT_FILE_NAME_PATTERN);
            }
        }
    }

	public void unregisterAppender() {
		synchronized (lock) {
			Pair<FileAppender, Integer> p = getAppenderPair();
			if (p != null) {				
				if (p.getRight() == 1) {
					setAppenderPair(null);
					p.getLeft().close();
				} else {
					p.setRight(p.getRight() - 1);
				}
			}
			MDC.remove(APPENDER_NAME);
			MDC.remove(EVENT_OBSERVER);
		}
	}

    public String getActiveFile() {
        return activeFile;
    }

    public void setActiveFile(String activeFile) {
        this.activeFile = activeFile;
    }

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    /**
	 * unregisters all appenders
	 */
	public static void clear() {
		synchronized(lock) {
			for (Pair<FileAppender, Integer> fa: appenders.values()) {
				fa.getLeft().close();
			}
			appenders.clear();
			if (MDC.getContext() != null)
				MDC.getContext().clear();
		}
		
	}
}
