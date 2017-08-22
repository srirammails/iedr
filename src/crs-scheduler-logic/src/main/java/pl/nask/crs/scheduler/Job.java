package pl.nask.crs.scheduler;

import java.util.Date;

public class Job {
    private Integer id;
    private String name;
    private String scheduleId;
    private Date start;
    private Date end;
    private JobStatus status;
    private String failure;

    public Job() {}

    public Job(Integer id, Date start, Date end, JobStatus status, String failure) {
        this.id = id;
        this.scheduleId = "";
        this.start = start;
        this.end = end;
        this.status = status;
        this.failure = failure;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getStatus() {
        return status.getShortName();
    }

    public String getStatusName() {
        return status.getName();
    }

    public void setStatus(String shortName) {
        this.status = JobStatus.fromShortName(shortName);
    }

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String schedulerId) {
        this.scheduleId = schedulerId;
    }

    @Override
    public String toString() {
        return String.format("Job[name=%s, start=%s, end=%s, status=%s]", name, start, end, status);
    }
}
