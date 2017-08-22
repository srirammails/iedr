package pl.nask.crs.commons.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class ClasspathResourceReader {
	public static String readString(String path) {
		InputStream is = ClasspathResourceReader.class.getResourceAsStream("/" + path);		
		try {			
			return readString(is);				
		} catch (IOException e) {
			Logger.getLogger(ClasspathResourceReader.class).warn("Couldn't read " + path + " : " + e.getMessage());
			return null;
		} finally {
			IOUtils.closeQuietly(is);	
		}
	}

	private static String readString(InputStream is) throws IOException {
		if (is == null) {
			throw new IOException("Cannot read from the null stream");
		}
		return IOUtils.toString(is);
	}
}
