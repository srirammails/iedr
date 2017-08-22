package pl.nask.crs.web.service;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import pl.nask.crs.app.payment.PaymentAppService;
import pl.nask.crs.payment.exceptions.InvoiceNotFoundException;
import pl.nask.crs.security.authentication.AuthenticatedUser;
import pl.nask.crs.security.authentication.WsAuthenticationService;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public class AccessInvoiceServlet extends HttpServlet {	
	private static final long serialVersionUID = -6787561054873634742L;
	
	private final static Logger LOGGER = Logger.getLogger(AccessInvoiceServlet.class);
    private final static String XML_FILE_REQUEST = "xml";
    private final static String PDF_FILE_REQUEST = "pdf";
    private final static String XML_MIME_TYPE = "application/xml";
    private final static String PDF_MIME_TYPE = "application/pdf";

    private ApplicationContext springContext;
    private WsAuthenticationService authenticationService;
    private PaymentAppService paymentAppService;

    @Override
    public void init() throws ServletException {
        springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        authenticationService = (WsAuthenticationService) springContext.getBean("wsAuthenticationService");
        paymentAppService = (PaymentAppService) springContext.getBean("paymentAppService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String token = request.getParameter("token");
        String invoiceNumber = request.getParameter("invoiceNumber");
        String fileType = request.getParameter("fileType");
        try {
//            validateParameters()
            AuthenticatedUser user = authenticationService.getAuthenticatedUser(userName, token);

            InputStream stream = null;
            if (fileType.equalsIgnoreCase(XML_FILE_REQUEST)) {
                stream = paymentAppService.viewXmlInvoice(user, invoiceNumber);
                response.setContentType(XML_MIME_TYPE);
            } else if (fileType.equalsIgnoreCase(PDF_FILE_REQUEST)) {
                stream = paymentAppService.viewPdfInvoice(user, invoiceNumber);
                response.setContentType(PDF_MIME_TYPE);
            } else {
                LOGGER.error("Invalid file type: " + fileType);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            writeStreamToResponse(stream, response);
        }catch (InvoiceNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void writeStreamToResponse(InputStream inputStream, HttpServletResponse response) throws IOException {
        try {
            IOUtils.copyLarge(inputStream, response.getOutputStream());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

}