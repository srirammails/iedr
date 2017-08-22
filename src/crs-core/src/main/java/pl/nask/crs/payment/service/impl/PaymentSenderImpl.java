package pl.nask.crs.payment.service.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.service.PaymentSender;

/**
 * @author: Marcin Tkaczyk
 */

public class PaymentSenderImpl implements PaymentSender {

    private static Logger log = Logger.getLogger(PaymentSenderImpl.class);
    private String strURL;
    private String proxyHost;
    private int proxyPort;

    public void setStrURL(String strURL) {
        this.strURL = strURL;
        log.info("Using URL: " + strURL);
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String send(String commandXML) throws PaymentException {
    	if (this.strURL == null) {
    		throw new IllegalStateException("strURL not set: use setStrURL before sending any command");
    	}
    	log.info("Sending command: \n" + commandXML);    	
        HttpClient httpClient = new HttpClient();
        if (!Validator.isEmpty(proxyHost))
            setProxy(httpClient);
        httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "API System of IEDR");
        httpClient.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 30000);
        PostMethod post = new PostMethod(strURL);
        post.setRequestEntity(new StringRequestEntity(commandXML));
        String response = null;
        try {
            int statusCode = httpClient.executeMethod(post);
            if (statusCode == HttpStatus.SC_OK) {
                response = post.getResponseBodyAsString();
                log.info("Received response:\n " + response);
            } else {
                log.error("Method failed: " + post.getStatusLine());
            }
        } catch (IOException e) {
            log.error("Exception in PaymentSenderImpl", e);
            throw new PaymentException("Error sending payment command to the remote system", e);
        } finally {
            post.releaseConnection();
        }
        return response;
    }

    private void setProxy(HttpClient httpClient) {
        HostConfiguration config = httpClient.getHostConfiguration();
        config.setProxy(proxyHost, proxyPort);
        log.info("Using proxy : " + proxyHost + ":" + proxyPort);
    }

    public static void main(String[] args) throws PaymentException {

        PaymentSenderImpl sender = new PaymentSenderImpl();
        sender.setStrURL("https://epage.payandshop.com/epage-remote.cgi");

        String command = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<request timestamp=\"20110429162828\" type=\"settle\"><merchantid>iedr</merchantid><account>internettest</account><orderid>20110429162828-9740564</orderid><pasref>13040873105044</pasref><authcode>152830</authcode><md5hash>682796e373792188dd373fb216d97f22</md5hash></request>";
        String oldCommand = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request timestamp=\"20120420141219\" type=\"auth\"><merchantid>iedr</merchantid><account>internettest</account><orderid>20120420141219-8360992</orderid><amount currency=\"EUR\">100050</amount><card><number>4263971921001307</number><expdate>0121</expdate><chname>John Doe</chname><type>VISA</type><cvn><number>111</number><presind>1</presind></cvn></card><autosettle flag=\"0\"/><tssinfo><address type=\"billing\"><country>ie</country></address></tssinfo><md5hash>e1e0b802072f77663888a412869cdba4</md5hash></request>";
        String newCommand = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><request timestamp=\"20120420141845\" type=\"auth\"><merchantId>iedr</merchantId><account>internettest</account><orderid>20120420141845-594613</orderid><amount currency=\"EUR\">100050</amount><card><number>4263971921001307</number><expdate>0121</expdate><chname>John Doe</chname><type>VISA</type><cvn><number>111</number><presind>1</presind></cvn></card><autosettle flag=\"0\"/><tssinfo><address type=\"billing\"><country>ie</country></address></tssinfo><md5hash>5e2d8e4311bb8c0df63a62b7cc287bea</md5hash></request>";

        System.out.println(sender.send(newCommand));
    }

}
