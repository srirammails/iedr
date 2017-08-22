package pl.nask.crs.realex.mock;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class RealexServlet extends HttpServlet {
	private static final long serialVersionUID = 517373556511476708L;
	
	private DummyRealexMock realexMock;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	public void init() throws ServletException {	
		this.realexMock = new DummyRealexMock();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// read request
		String request = readRequest(req);
		// process request
		String resultString = processRequest(request);			
		// print answer
		returnResult(resp, resultString);
	}

	private void returnResult(HttpServletResponse resp, String resultString) throws IOException {
		resp.setContentLength(resultString.length());
    	resp.setContentType("application/xml; charset=UTF-8");
    	resp.getWriter().print(resultString);
    	resp.flushBuffer();
	}

	private String processRequest(String request) {
		return realexMock.processMessage(request);
	}

	private String readRequest(HttpServletRequest req) throws IOException {
		return IOUtils.toString(req.getReader());
	}
}
