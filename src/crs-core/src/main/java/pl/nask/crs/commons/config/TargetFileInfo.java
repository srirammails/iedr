package pl.nask.crs.commons.config;

import java.io.File;

public class TargetFileInfo {

	private final String filename;
	private final File directory;

	public TargetFileInfo(File directory, String fileName) {
		this.directory = directory;
		this.filename = fileName;
	}

	public File getDirectory() {
		return directory;
	}

	public String getFilename() {
		return filename;
	}
	
	@Override
	public String toString() {
		return "TargetFileInfo [filename=" + filename + ", directory="
				+ directory + "]";
	}

	public File getTargetFile(boolean createDirs) {
		if (createDirs && !directory.exists())
			directory.mkdirs();
		
		return new File(directory, filename);
	}
}
