package pl.nask.crs.commons.email.service.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.email.Email;

public class EmailQueue {
	private static final Logger LOG = Logger.getLogger(EmailQueue.class);
	private final BlockingQueue<Email> queue = new LinkedBlockingQueue<Email>();

	public Email next() throws InterruptedException {
		return queue.take();
	}

	public void queue(Email email) throws InterruptedException {
		queue.put(email);
		int size = size();
		if (size > 100) {
			LOG.warn("Email Queue is growing (" + size + "), is there any consumer running?");
		}
	}

	public int size() {		
		return queue.size();
	}
}
