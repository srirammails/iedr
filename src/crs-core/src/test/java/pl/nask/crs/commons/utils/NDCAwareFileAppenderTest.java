package pl.nask.crs.commons.utils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.io.File;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.LoggingEvent;

import pl.nask.crs.commons.log4j.EventObserver;
import pl.nask.crs.commons.log4j.NDCAwareFileAppender;


public class NDCAwareFileAppenderTest {

	@BeforeClass
	@AfterClass
	public static void cleanAll() {
		if (MDC.getContext() != null)
			MDC.getContext().clear();
		delete("appender1.log");
		delete("appender2.log");
		delete("appender3.log");
		delete("appender4.log");
		delete("appender5.log");
		delete("appender6.log");
	}
	
	@AfterMethod
	public void clean() {
		NDCAwareFileAppender.clear();
	}

	private static void delete(String filename) {
		File f = new File(filename);
		if (f.exists())
			f.delete();
	}

	@Test
	public void testRegisterAppender() {
		NDCAwareFileAppender nap = new NDCAwareFileAppender();
		nap.registerAppender("appender1");
		
		AssertJUnit.assertTrue("File exists", new File("APPENDER1.log").exists());
	}
	
	@Test
	public void testRegisterTwice() {
		NDCAwareFileAppender nap = new NDCAwareFileAppender();
		nap.registerAppender("appender2");
		nap.registerAppender("appender2");
		
		AssertJUnit.assertTrue("File exists", new File("APPENDER2.log").exists());
	}
	
	@Test
	public void testRegisterTwiceErr() {
		NDCAwareFileAppender nap = new NDCAwareFileAppender();
		nap.registerAppender("appender3");
		nap.registerAppender("appender4");
		
		AssertJUnit.assertTrue("File exists", new File("APPENDER3.log").exists());
		AssertJUnit.assertFalse("File doesn't exist", new File("APPENDER4.log").exists());
	}
	
	@Test
	public void testRegisterUnregister() {
		NDCAwareFileAppender nap = new NDCAwareFileAppender();
		nap.registerAppender("appender5");
		
		// file is created when sth is appended				
		AssertJUnit.assertTrue("File exists", new File("APPENDER5.log").exists());
		nap.unregisterAppender();			
	}
	
	@Test
	public void testUnregisterError() {
		NDCAwareFileAppender nap = new NDCAwareFileAppender();
		nap.unregisterAppender();			
	}
	
	// just a link test
	@Test
	public void testAppend1() {
		NDCAwareFileAppender nap = new NDCAwareFileAppender();
		log(nap);
	}
	
	@Test
	public void testAppend2() {
		NDCAwareFileAppender nap = new NDCAwareFileAppender();
		log(nap);
		nap.registerAppender("appender6");
		log(nap);
		AssertJUnit.assertTrue("File exists", new File("APPENDER6.log").exists());
		nap.unregisterAppender();
	}
	
	@Test
	public void eventObserverShouldGetEventsWithAProperLevel() {
		NDCAwareFileAppender nap = new NDCAwareFileAppender();
		EventObserver observer = new EventObserver(Priority.INFO);
		nap.registerAppender("appenderEh1", observer);
		Assert.assertEquals(0, observer.eventCount());
		debug(nap);
		Assert.assertEquals(0, observer.eventCount());
		log(nap);
		Assert.assertEquals(1, observer.eventCount());
		warn(nap);
		Assert.assertEquals(2, observer.eventCount());
		error(nap);
		Assert.assertEquals(3, observer.eventCount());
	}
	
	@Test
	public void eventObserverHandlindErrorMessages() {
		NDCAwareFileAppender nap = new NDCAwareFileAppender();
		EventObserver observer = new EventObserver(Priority.ERROR);
		nap.registerAppender("appenderEh1", observer);
		Assert.assertEquals(0, observer.eventCount());
		debug(nap);
		Assert.assertEquals(0, observer.eventCount());
		log(nap);
		Assert.assertEquals(0, observer.eventCount());
		warn(nap);
		Assert.assertEquals(0, observer.eventCount());
		error(nap);
		Assert.assertEquals(1, observer.eventCount());
	}
	
	@Test
	public void eventObserverShouldNotLogMessagesAfterUnregistered() {
		NDCAwareFileAppender nap = new NDCAwareFileAppender();
		EventObserver observer = new EventObserver(Priority.ERROR);
		nap.registerAppender("appenderEh1", observer);
		error(nap);
		Assert.assertEquals(1, observer.eventCount());
		nap.unregisterAppender();
		error(nap);
		Assert.assertEquals(1, observer.eventCount());
	}
	
	private void log(NDCAwareFileAppender nap) {
		nap.append(new LoggingEvent("java.lang.Object", Logger.getRoot(), Priority.INFO, "testMessage", null));
	}
	
	private void debug(NDCAwareFileAppender nap) {
		nap.append(new LoggingEvent("java.lang.Object", Logger.getRoot(), Priority.DEBUG, "testMessage", null));
	}
	
	private void error(NDCAwareFileAppender nap) {
		nap.append(new LoggingEvent("java.lang.Object", Logger.getRoot(), Priority.ERROR, "testMessage", null));
	}
	
	private void warn(NDCAwareFileAppender nap) {
		nap.append(new LoggingEvent("java.lang.Object", Logger.getRoot(), Priority.WARN, "testMessage", null));
	}
}
