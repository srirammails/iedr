package pl.nask.crs.commons.config;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;


public class ExportConfiguration {
	String outputRootDir;
	boolean useDateSubdir;

	public ExportConfiguration(String outputRootDir, boolean useDateSubdir) {
		super();
		this.outputRootDir = outputRootDir;
		this.useDateSubdir = useDateSubdir;
	}

    /**
     * default read/write location
     */
    public TargetFileInfo fileConfig(String fileName, Date date) {
		return new TargetFileInfo (directoryFor(outputRootDir, useDateSubdir, date), fileName);
	}

//	private String filenameFor(String name, String suffix) {
//		return name + "." + suffix;
//	}

	protected File directoryFor( String rootDir, boolean useSubdirWithDate, Date date) {
		File root = new File(rootDir);
		if (!useSubdirWithDate) {
			return root;
		} else {
			return new File(root, subdir(date));
		}
	}

	private String subdir(Date date) {
		return DateFormatUtils.format(date, "yyyy-MM-dd");
	}
}
