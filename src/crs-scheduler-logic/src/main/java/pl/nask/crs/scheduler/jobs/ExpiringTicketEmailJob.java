package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.tickets.TicketAppService;
import pl.nask.crs.security.authentication.AuthenticatedUser;

/**
 * (C) Copyright 2013 NASK
 * Software Research & Development Department
 */
public class ExpiringTicketEmailJob extends AbstractJob {

    private final TicketAppService ticketAppService;

    public ExpiringTicketEmailJob(TicketAppService ticketAppService) {
        this.ticketAppService = ticketAppService;
    }

    @Override
    public void runJob() {
        AuthenticatedUser user = null;
        ticketAppService.sendTicketExpirationEmails(user);
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
