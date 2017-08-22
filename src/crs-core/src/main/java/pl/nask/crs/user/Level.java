package pl.nask.crs.user;

/**
 * @author Kasia Fulara
 */
public enum Level {
	Default(0, "Default"),
    Registrar(2, "Registrar"),
    Finance(8, "Finance"),
    Hostmaster(16, "Hostmaster"),
    HostmasterLead(32, "HostmasterLead"),
    Batch(64, "Batch"),
    TechnicalLead(128, "TechnicalLead"),
    Technical(256, "Technical"),
    SuperRegistrar(1024, "SuperRegistrar"),
    Direct(2048, "Direct");

    private int level;
    private String name;

    Level(int level, String name) {
        this.level = level;
        this.name = name;
    }

    public boolean isContained(int l) {
        int i = level & l;
        return i == level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

	public static Level levelForName(String name2) {
		for (Level l : values()) {
			if (l.name.equals(name2)) {
				return l;
			}
		}

		throw new IllegalArgumentException("Illegal name for Level: " + name2);
	}
}
