package pl.nask.crs.scheduler.servlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import pl.nask.crs.scheduler.JobRegistry;
import pl.nask.crs.scheduler.SchedulerCron;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class SchedulerMonitor extends HttpServlet {

    private enum Action {
        START_ACTION("start"), STOP_ACTION("stop"), RELOAD_ACTION("reload"), LIST_TASK("list"), RUN_TASK("run");

        private String name;

        private Action(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Action fromName(String name) {
            if ("start".equalsIgnoreCase(name)) {
                return Action.START_ACTION;
            } else if ("stop".equalsIgnoreCase(name)) {
                return STOP_ACTION;
            } else if ("reload".equalsIgnoreCase(name)) {
                return RELOAD_ACTION;
            } else if ("list".equalsIgnoreCase(name)) {
                return LIST_TASK;
            } else if ("run".equalsIgnoreCase(name)) {
                return RUN_TASK;
            } else {
                return null;
            }
        }
    }

    private Logger LOGGER = Logger.getLogger(SchedulerMonitor.class);
    private ApplicationContext springContext;
    private SchedulerCron schedulerCron;
    private JobRegistry jobRegistry;

    @Override
    public void init() throws ServletException {
        springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        schedulerCron = (SchedulerCron) springContext.getBean("schedulerCron");
        jobRegistry = (JobRegistry) springContext.getBean("jobRegistry");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        disableCache(resp);
        performAction(req, resp);
    }

    private void performAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String msg = getStatus();//default response
        String actionName = req.getParameter("action");
        Action action = Action.fromName(actionName);
        if (action != null) {
            switch (action) {
                case START_ACTION:
                    schedulerCron.start();
                    msg = getStatus();
                    break;
                case STOP_ACTION:
                    schedulerCron.stop();
                    msg = getStatus();
                    break;
                case LIST_TASK:
                    msg = getTaskList();
                    break;
                case RELOAD_ACTION:
                    schedulerCron.reload();
                    msg = getStatus();
                    break;
                case RUN_TASK:
                    if (!schedulerCron.isRunning()) {
                        msg = "The process cannot be manually run if the scheduler is not running";
                    } else {
                        String taskName = req.getParameter("job");
                        if (taskNameIsValid(taskName)) {
                            schedulerCron.invoke(taskName);
                            msg = taskName + " started";
                        } else {
                            msg = "Invalid job name";
                        }
                    }
            }
            LOGGER.info(action.getName() + " action invoked.");
        }
        resp.getWriter().append(msg);
        resp.flushBuffer();
        LOGGER.info("Response: " + msg);
    }

    private boolean taskNameIsValid(String taskName) {
        if (taskName == null) {
            return false;
        } else {
            for (String name : jobRegistry.getTaskNames()) {
                if (name.equals(taskName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getStatus() {
        boolean isRunning = schedulerCron.isRunning();
        return  "Scheduler is running: " + isRunning;
    }

    private String getTaskList() {
        StringBuilder sb = new StringBuilder();
        sb.append("Available tasks:");
        for (String taskName : jobRegistry.getTaskNames()) {
            sb.append("\n").append(taskName);
        }
        return sb.toString();
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
    
    @Override
    public void destroy() {   
    	super.destroy();
    	schedulerCron.stop();
    }
}
