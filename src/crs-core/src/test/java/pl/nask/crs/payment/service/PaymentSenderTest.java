package pl.nask.crs.payment.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.service.impl.PaymentSenderImpl;

public class PaymentSenderTest {
	int serverPort = 43323;
	@SuppressWarnings("restriction")
	HttpServer server;
	private String realexPath = "/realex/";
	
	@Before
	public void startServer() throws IOException {
		stopServer();
		server = HttpServer.create(new InetSocketAddress("localhost", serverPort), 0);
		HttpHandler realexHandler = new HttpHandler() {
			@Override
			public void handle(HttpExchange exchange) throws IOException {
				String response ="some random text"; 
				exchange.sendResponseHeaders(200, response.length());
				PrintWriter w = new PrintWriter(exchange.getResponseBody());
				w.print(response);
				w.flush();
				exchange.close();
			}
		};
		server.createContext(realexPath, realexHandler);
		server.start();
	}
	
	@SuppressWarnings("restriction")
	@After
	public void stopServer() {
		if (server != null) {
			server.stop(0);
		}
	}
	
	@Test
	public void sendingShouldFailIfNoUrlIsProvided() throws PaymentException {
		try {
			PaymentSender sender = new PaymentSenderImpl();
			sender.send("anyCommand");
			Assert.fail("Sending should fail: no URL was set");
		} catch (IllegalStateException e) {
			// not fully initialized
		}
	}
	
	// this one assumes that the realex mock is running 
	@Test
	public void sendingShouldSucceedIfRealexIsRunning() throws PaymentException {		
		PaymentSenderImpl sender = new PaymentSenderImpl();
		sender.setStrURL(getServerAddress());
		String result = sender.send("anyCommand");
		Assert.assertNotNull(result);
	}

	private String getServerAddress() {
		String addr = "http:/" + server.getAddress() + realexPath ;
		System.out.println(addr);
		return addr;
	}
}
