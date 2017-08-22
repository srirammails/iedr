package pl.nask.crs.commons.email.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import pl.nask.crs.commons.email.service.impl.AsyncEmailSender;


public class EmailSenderServlet extends HttpServlet {	
	private static final long serialVersionUID = 8577322184519238133L;

	private Logger LOGGER = Logger.getLogger(EmailSenderServlet.class);

	private ApplicationContext springContext;
	private AsyncEmailSender sender;
	// number of workers to be used by the AsyncEmailSender
	private int workerThreadsCount = 1;
	// number of seconds to wait for the AsyncEmailSender to stop it's workers
	private int waitTimeout = 20;

	@Override
	public String getServletInfo() {
		return "Email sending worker management";
	}

	@Override
	public void destroy() {
		super.destroy();
		stopSenderThread();
		String msg = getSenderThreadInfo();
		LOGGER.info(msg);
	}	

    @Override
    public void init() throws ServletException {
    	super.init();
        springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.sender = (AsyncEmailSender) springContext.getBean("emailSenderJob");
        startSenderThread(getWorkerThreadsCount());        
    }

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        disableCache(resp);
        String attr = req.getParameter("action");
        performAction(attr);
        String msg = getSenderThreadInfo();
        resp.getWriter().append(msg);
        resp.flushBuffer();
        LOGGER.info(msg);
    }

	private void performAction(String attr) {
		if ("start".equalsIgnoreCase(attr)) {
			startSenderThread(getWorkerThreadsCount());
		} else if ("stop".equalsIgnoreCase(attr)) {
			stopSenderThread();
		}
	}

	private int getWorkerThreadsCount() {
		// check the system properties first.
		String key = "javamail.asyncsender.threads";
		String threads = System.getProperty(key);
		int maxThreads = workerThreadsCount;
		if (threads != null) {
			try {
				maxThreads = Integer.parseInt(threads);
			} catch (NumberFormatException e) {
				LOGGER.warn("Illegal value provided in system property " + key + ": " + threads + ". Using " + maxThreads + " as the worker pool size");
			}
		}
		
		return maxThreads;
	}

	private boolean startSenderThread(int workerThreads) {
		if (sender.isRunning()) {
			LOGGER.warn("Email sender thread will not be started: a thread is already created. Stop it first to run another one.");
			return false;
		} else {
			LOGGER.info("Starting email sender thread");    		
			sender.start(workerThreads);
			LOGGER.info("Async Email sender started with " + sender);
			return true;
		}
	}

	private synchronized boolean stopSenderThread() {		
		if (sender.isRunning()) {
			return sender.stop(waitTimeout);
		} else {
			LOGGER.warn("Email sender thread is already stopped");
			return true;
		}
	}

	private synchronized String getSenderThreadInfo() {
		String running = sender.isRunning() ? "running" : "stopped";
		int emails = sender.getQueueSize();
		String configuration = sender.getConfiguration();
		return String.format("Email sender thread is %s with %s emails in the queue. Configuration: %s.", running, emails, configuration);
	}

	private void disableCache(HttpServletResponse resp) {
        resp.setDateHeader("Expires", 1);
        // Set standard HTTP/1.0 no-cache header
        resp.setHeader("Pragma", "no-cache");
        // Set standard HTTP/1.1 no-cache headers
        resp.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate, max-age=0");
        // Set IE extended HTTP/1.1 no-cache headers
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
    }
}
