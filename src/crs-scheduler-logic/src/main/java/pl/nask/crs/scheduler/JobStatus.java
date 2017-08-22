package pl.nask.crs.scheduler;

public enum JobStatus {
	RUNNING ("R", "Running"),
	FAILED ("E", "Failed"),
	FINISHED ("F", "Finished"),
    ACTIVE ("A", "Active");

    private String name;
	private String shortName;

	JobStatus (String shortName, String name) {
		this.shortName = shortName;
        this.name = name;
	}
	
	public String getShortName() {
		return shortName;
	}

    public String getName() {
        return name;
    }

    public static JobStatus fromShortName(String name) {
		for (JobStatus s: values()) {
			if (s.getShortName().equals(name))
				return s;
		}
		
		throw new IllegalArgumentException("Illegal short name for a JobStatus: " + name);
	}
}
