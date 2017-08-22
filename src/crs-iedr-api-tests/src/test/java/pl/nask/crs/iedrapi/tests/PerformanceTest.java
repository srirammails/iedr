package pl.nask.crs.iedrapi.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class PerformanceTest extends TestUtil {
	private final int MAX_CALLS = 100;
	private final int MAX_THREADS = 100;
	
	
	/**
	 * send a bunch of requests and see if the application manages to handle them all
	 */
	@Test
	public void testDomainInfoSerial() {
		loginIDL2();
		String request = xmlFileToString("/commands/domain_info.xml");
		String response = xmlFileToString("/commands/domain_info_response.xml");
		
		new RunnableCmd(wc, request, response, MAX_CALLS).run();
		
		logout();
	}
	
	/**
	 * send a bunch of requests and see if the application manages to handle them all
	 * @throws InterruptedException 
	 */
	@Test
	public void testDomainInfoParallel() throws InterruptedException {
		String request = xmlFileToString("/commands/domain_info.xml");
		String response = xmlFileToString("/commands/domain_info_response.xml");
		
		Thread[] threads = new Thread[MAX_THREADS];
		
		for (int i = 0; i < MAX_THREADS; i++) {
			int calls = MAX_CALLS;
			threads[i] = new Thread(new RunnableCmd(wc, request, response, calls), "(calls=" + calls + ")");
		}

		loginIDL2();
		
		for (Thread t: threads)
			t.start();
				
		for (Thread t: threads)
			t.join();
		
		logout();
	}
	

	public class RunnableCmd implements Runnable {
		private final WebConversation localWc = new WebConversation();
		private final String command;
		private final String exampleResponse;
		private final int numberOfCalls;
		
		public RunnableCmd(WebConversation wc, String cmd, String exampleResponse, int numberOfCalls) {
			this.numberOfCalls = numberOfCalls;
			String jsessionId = wc.getCookieValue("JSESSIONID");
			localWc.putCookie("JSESSIONID", jsessionId);
			command = cmd;
			this.exampleResponse = exampleResponse;
			System.out.printf("RunnableCmd with %s number of calls\n", numberOfCalls);
		}
		
		public void run() {
			for (int i=0; i < numberOfCalls; i++) {
				WebResponse res = doSendRequest(command, localWc);
				String response = getText(res);
				Assert.assertTrue(resultStringsMatch(response, exampleResponse));
			}					
		}
		
	}
}
