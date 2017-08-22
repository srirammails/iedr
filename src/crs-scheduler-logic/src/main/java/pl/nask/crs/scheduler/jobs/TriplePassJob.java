package pl.nask.crs.scheduler.jobs;

import pl.nask.crs.app.triplepass.TriplePassAppService;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class TriplePassJob extends AbstractJob {

    private final TriplePassAppService service;

    public TriplePassJob(TriplePassAppService service) {
        this.service = service;
    }

    @Override
    public void runJob() {
        service.triplePass();
    }

    @Override
    public String getJobName() {
        return this.getClass().getSimpleName();
    }
}
