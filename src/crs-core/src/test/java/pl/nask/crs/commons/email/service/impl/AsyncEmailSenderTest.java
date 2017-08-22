package pl.nask.crs.commons.email.service.impl;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import pl.nask.crs.commons.email.Email;
import pl.nask.crs.commons.email.service.EmailSender;

public class AsyncEmailSenderTest {
	private EmailSender sender = new MockedEmailSender();
	private EmailQueue queue = new EmailQueue();
	private AsyncEmailSender asyncSender = new AsyncEmailSender(sender, queue );
	
	@BeforeMethod
	public void initQueue() throws InterruptedException {
		while (queue.size() != 0) {
			queue.next();
		}
		addMessages(1000);
	}
	
	private void addMessages(int count) throws InterruptedException {
		for (int i=0; i<count; i++) {
			queue.queue(new Email());
		}
	}

	@Test(timeOut=5000)
	public void senderShouldBeRunningAfterStart() {		
		asyncSender.start(10);
		AssertJUnit.assertTrue(asyncSender.isRunning());
		// check if the queue is emptied after some time
		waitUntillQueueSizeIsZero();
	}
	
	@Test(timeOut=5000)
	public void senderShouldBeStoppedAfterStop() {		
		asyncSender.start(10);
		AssertJUnit.assertTrue(asyncSender.isRunning());
		asyncSender.stop(1000);
		AssertJUnit.assertFalse(asyncSender.isRunning());
	}
	
	@Test(timeOut=5000)
	public void senderShouldBeAbleToStopWithEmailsInTheQueue() throws Exception {		
		addMessages(5000);
		asyncSender.start(2);
		AssertJUnit.assertTrue(asyncSender.isRunning());
		asyncSender.stop(1000);
		AssertJUnit.assertFalse(asyncSender.isRunning());
		AssertJUnit.assertTrue(queue.size() > 0);
	}

	private void waitUntillQueueSizeIsZero() {
		while (queue.size()!=0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
