package pl.nask.crs.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class VersionInfo {	
	private static Properties p = new Properties();

	static {
		InputStream is = null;
		try {
			is = VersionInfo.class.getResourceAsStream("/revision.txt");
			p.load(is);		
		} catch (IOException e) {
			Logger.getLogger(VersionInfo.class).warn("Couldn't read revision.txt - revision info will not be available", e);
		} finally {
			if (is != null) {
				try {					
					is.close();
				} catch (IOException e) {
					Logger.getLogger(VersionInfo.class).warn("Couldn't close the stream. Panic!", e);
				}
			}
		}
	}
	
	public static String getRevision() {
		return p.getProperty("revision");
	}
	
	public static String getCrsVersion() {
		return p.getProperty("aversion");
	}

	public static String getBuildProfile() {
		return p.getProperty("buildProfile");
	}
	
	public static String getApiVersion() {
		return p.getProperty("iedrApiVersion");
	}
	
	public static String getApiCommmandsVersion() {
		return p.getProperty("iedrApiCommandsVersion");
	}	
	
	public static String getCrsApiVersion() {
		return p.getProperty("wsApiVersion");
	}
}
