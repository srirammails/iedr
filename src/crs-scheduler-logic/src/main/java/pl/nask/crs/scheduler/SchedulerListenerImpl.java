package pl.nask.crs.scheduler;

import it.sauronsoftware.cron4j.TaskExecutor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.EmailSender;
import pl.nask.crs.commons.email.service.EmailTemplateNamesEnum;
import pl.nask.crs.commons.email.service.EmailTemplateSender;
import pl.nask.crs.commons.email.service.MapBasedEmailParameters;
import pl.nask.crs.commons.email.service.ParameterNameEnum;
import pl.nask.crs.scheduler.dao.SchedulerDAOImpl;

public class SchedulerListenerImpl implements it.sauronsoftware.cron4j.SchedulerListener {
	private final Logger logger = Logger.getLogger(it.sauronsoftware.cron4j.SchedulerListener.class);

	private final SchedulerDAOImpl dao;

	private EmailTemplateSender emailTemplateSender;
	

	private EmailSender fallbackEmailSender;

	public SchedulerListenerImpl(SchedulerDAOImpl dao, EmailTemplateSender emailTemplateSender, EmailSender emailSender) {
		this.dao = dao;
		this.emailTemplateSender = emailTemplateSender;
		this.fallbackEmailSender = emailSender;
	}

	@Override
	public void taskFailed(TaskExecutor executor, Throwable ex) {
		int configId = getJobId(executor);
		String jobName = getJobName(executor);
		logger.error(String.format("Job %s (%s) failed with exception", configId, jobName), ex);
		logger.info("Sending notification about failed job");
		sendNotification(EmailTemplateNamesEnum.SCHEDULER_JOB_FAILED, jobName);
		dao.logJobFailed(jobName, ex.getMessage());
	}

	private void sendNotification(EmailTemplateNamesEnum template, String jobName) {
		if (emailTemplateSender != null) {
			MapBasedEmailParameters templateParameters = new MapBasedEmailParameters();
			templateParameters.set(ParameterNameEnum.SCHEDULER_JOB_NAME, jobName);
			try {
				emailTemplateSender.sendEmail(template.getId(), templateParameters);
			} catch (Exception e) {
				logger.error("Error sending email notification", e);
				sendErrorMessageFallback(jobName, e);
			} 
		} else {
			logger.error("Cannot send email notification: email sender not configured");
		}
	}

	private void sendErrorMessageFallback(String jobName, Exception e) {
		Email email = new Email();
		email.setActive(true);
		email.setToList(Arrays.asList("asd@iedr.ie"));
		email.setSubject("Fatal error while sending notification about errors in job " + jobName + " - check the logs for the details");
		StringWriter writer = new StringWriter();	
		PrintWriter pw = new PrintWriter(writer);
		e.printStackTrace(pw);
		email.setText("Fatal error while sending notification about errors in job " + jobName + " - check the logs for the details. \n\r " + e.getMessage() + "\n\r" + writer.toString() );
		
		try {
			fallbackEmailSender.sendEmail(email);
		} catch (Exception e1) {
			logger.error("Failed to send error message", e);
		} 
	}

	@Override
	public void taskLaunching(TaskExecutor executor) {
		int configId = getJobId(executor);
		String jobName = getJobName(executor);
		logger.info(String.format("Launching job %s (%s)", configId, jobName));
		try {
			dao.logJobStarted(jobName);
		} catch (Exception e) {
			sendErrorMessageFallback(jobName, e);
		}
	}

	private int getJobId(TaskExecutor executor) {
		return ((SchedulerTask) executor.getTask()).getJobId();
	}
	
	private String getJobName(TaskExecutor executor) {
		return ((SchedulerTask) executor.getTask()).getJobName();
	}

	@Override
	public void taskSucceeded(TaskExecutor executor) {
		int configId = getJobId(executor);
		String jobName = getJobName(executor);
		logger.info(String.format("Execution of job %s (%s) finished successfully", configId, jobName));
		dao.logJobSucceeded(jobName);
		if ("ERROR".equals(executor.getStatusMessage())) {
			logger.info("Sending notification about errors in job");
			sendNotification(EmailTemplateNamesEnum.SCHEDULER_JOB_FINISHED_WITH_ERRORS, jobName);		
		}
	}
}
