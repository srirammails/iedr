package pl.nask.crs.scheduler.jobs;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.commons.log4j.EventObserver;
import pl.nask.crs.commons.log4j.NDCAwareFileAppender;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public abstract class AbstractJob {
	private static final Logger LOG = Logger.getLogger(AbstractJob.class);
	
    private static NDCAwareFileAppender APPENDER =(NDCAwareFileAppender) Logger.getRootLogger().getAppender("processAware");

    public final void run(EventObserver observer) throws Exception {
        try {
            registerAppender(observer);
            LOG.info("Starting job: " + getJobName());
            runJob();
            LOG.info("Job ended: " + getJobName());      
        } finally {
            unregisterAppender();
        }
    }

	public abstract void runJob() throws Exception;

    public abstract String getJobName();

    private void registerAppender(EventObserver observer) {
        APPENDER.registerAppender(getJobName(), observer);
    }

    private void unregisterAppender() {
        APPENDER.unregisterAppender();
    }
}
