package pl.nask.crs.commons.email.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.EmailSender;

public class AsyncEmailSender {
	private final static Logger LOG = Logger.getLogger(AsyncEmailSender.class);
	
	private final EmailSender sender;
	private final EmailQueue queue;
	
	private volatile boolean started = false;
	private volatile ExecutorService pool;

	private int poolSize;
	private int sleepTimeBeforeRetryMilis = 5000;

	public AsyncEmailSender(EmailSender sender, EmailQueue queue) {
		this.sender = sender;
		this.queue = queue;
	}
	
	public synchronized void start(int numberOfWorkers) {
		if (!started) {
			this.poolSize = numberOfWorkers;
			pool = Executors.newFixedThreadPool(numberOfWorkers);
			for (int i=0; i<numberOfWorkers; i++) {
				pool.submit(new Runnable() {
					public void run() {	
						while (!Thread.currentThread().isInterrupted()) {
							Email email;
							try {
								email = queue.next();
								send(email);
							} catch (InterruptedException e) {
								LOG.warn("Interrupted while waiting for the email to be send", e);
								Thread.currentThread().interrupt();
							}
						}
						
						if (Thread.currentThread().isInterrupted()) {
							LOG.info("Exiting after being interrupted ");
							int size = queue.size();
							if (size > 0) {
								LOG.error("Exiting after being interrupted, " + size + " messages will NOT be send");
							} 
						}
					}
				});							
			}
		}
		started = true;
	}
	
	public synchronized boolean stop(int timeout) {
		if (started) {
			pool.shutdownNow();
			try {
				pool.awaitTermination(timeout, TimeUnit.SECONDS);
				if (pool.isTerminated()) {
					LOG.info("Email sender thread stopped");
					started = false;
					poolSize = 0;
				} else {
					LOG.warn("Failed to stop email sender thread, it failed to stop in a time of " + timeout + " seconds");
				}
			} catch (InterruptedException e) {
				LOG.warn("Failed to stop email sender thread, it's still alive");					
			}
		}
		return started;
	}
	

	private void send(Email email) {
		try {
			if (email.isHtml()) {
				sender.sendHtmlEmail(email);
			} else {
				sender.sendEmail(email);
			}
		} catch (Exception e) {
			LOG.warn("Error sending email, putting back to the queue / email subject: " + email.getSubject() + " / error was: " + e.getClass().getSimpleName() + ", message: " + e.getMessage());
			LOG.debug("Error sending email, putting back to the queue: " + email, e);
			try {
				Thread.sleep(sleepTimeBeforeRetryMilis);
				queue.queue(email);
			} catch (InterruptedException e1) {
				LOG.error("Interrupted while adding email to the queue, giving up: " + email);
			}
		} 		
	}

	public int getQueueSize() {
		return queue.size();
	}
	
	@Override
	public String toString() {	
		return "AsyncEmailSender [sender=" + sender +"]";
	}

	public String getConfiguration() {
		return "ThreadPool size:" + poolSize + ", " + sender;
	}

	public boolean isRunning() {		
		return started;
	}
}

