package pl.nask.crs.iedrapi.tests;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import ie.domainregistry.ieapi_1.ErrValueType;
import ie.domainregistry.ieapi_1.IeapiType;
import ie.domainregistry.ieapi_1.ResponseType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.w3c.dom.Element;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
/**
 * @author: Marcin Tkaczyk
 */
public abstract class TestUtil {
	private static final Logger log = Logger.getLogger(TestUtil.class);
	// cache for xml commands
	private static final Map<String, String> cache = new HashMap<String, String>(); 
	
	private String logoutCmd = xmlFileToString("/commands/logout.xml");
	// IH4-IEDR, account: IEDR (2)
	private String loginCmd = xmlFileToString("/commands/login.xml");
	// account API TESTS (666)
	private String loginApiTestCmd = xmlFileToString("/commands/login_APITEST-IEDR.xml");
    // API TESTS S (670), superregistrar
    private String loginApits1Cmd = xmlFileToString("/commands/login_APITS1-IEDR.xml");
	private String loginApit2Cmd = xmlFileToString("/commands/login_APIT2.xml");
	// API TESTS 2 (667)
	private String loginApit3Cmd = xmlFileToString("/commands/login_APIT3.xml");
    // API TESTS 3 (668)
    private String loginApit4Cmd = xmlFileToString("/commands/login_APIT4.xml");
    // Digiweb (124)
    private String loginDad1Cmd = xmlFileToString("/commands/login_DAD1-IEDR.xml");
    // API TESTS 4 (669)
    private String loginApit5Cmd = xmlFileToString("/commands/login_APIT5.xml");

    private String loginGeorgeCmd = xmlFileToString("/commands/login_George.xml");
    private String loginAAH905Cmd = xmlFileToString("/commands/login_AAH905.xml");
    private String loginIdl2Cmd = xmlFileToString("/commands/login_IDL2-IEDR.xml");
    private String loginAHL863Cmd = xmlFileToString("/commands/login_AHL863-IEDR.xml");
    private String loginACB865Cmd = xmlFileToString("/commands/login-ACB865.xml");

    private static final String API_URL = "http://localhost:5556/crs-api/";
//    private static final String API_URL = "http://localhost:8080/crs-iedr-api-1.1.0-Sprint36.10/";
    private static final int HTTP_OK = 200;

    protected WebConversation wc = null;

    static JAXBContext ctx = null;
    static Unmarshaller um;

    @BeforeMethod
    public void before() throws JAXBException {
        wc = new WebConversation();
        if (ctx == null) {
        	ctx = JAXBContext.newInstance(
                "ie.domainregistry.ieapi_1" +
                        ":ie.domainregistry.ieapi_account_1" +
                        ":ie.domainregistry.ieapi_contact_1" +
                        ":ie.domainregistry.ieapi_domain_1" +
                        ":ie.domainregistry.ieapi_ticket_1" +
                        ":ie.domainregistry.ieapicom_1");
        }
        if (um == null) {
        	um = ctx.createUnmarshaller();
//        	um.setValidating(true);
//        	um.setEventHandler(new MyValidationEventHandler());
//        	Schema schema;
        }
    }

    // performs a simple login
    protected void login() {
    	sendFirstRequest(loginCmd);
    }
    
    protected void loginDad1() {
    	sendFirstRequest(loginDad1Cmd);
    }
    
    protected void loginApiTest() {
    	sendFirstRequest(loginApiTestCmd);
    }

    protected void loginApits1() {
        sendFirstRequest(loginApits1Cmd);
    }
    protected void loginIDL2() {
    	sendFirstRequest(loginIdl2Cmd);
    }

    protected void loginApit2() {
    	sendFirstRequest(loginApit2Cmd);
    }

    protected void loginApit3() {
    	sendFirstRequest(loginApit3Cmd);
    }

    protected void loginApit4() {
    	sendFirstRequest(loginApit4Cmd);
    }

    protected void loginGeorge() {
        sendFirstRequest(loginGeorgeCmd);
    }

    protected void loginAAH905() {
        sendFirstRequest(loginAAH905Cmd);            
    }

    protected void loginApit5() {
        sendFirstRequest(loginApit5Cmd);
    }

    protected void logout() {
    	sendRequest(logoutCmd);
    }

    protected void loginAHL863() {
        sendFirstRequest(loginAHL863Cmd);
    }

    protected void loginACB865() {
        sendFirstRequest(loginACB865Cmd);
    }

    //First request puts session id into cookies
    protected  String sendFirstRequest(String command) {
        WebResponse response = doSendRequest(command);

        String tmp = response == null ? null : response.getHeaderField("SET-COOKIE");
        if (tmp != null) {
            tmp = tmp.substring(tmp.indexOf("=") + 1, tmp.indexOf(";"));
            wc.putCookie("JSESSIONID", tmp);
        }
        return getText(response);
    }

    protected String sendRequest(String command) {
    	WebResponse res = doSendRequest(command);
    	return getText(res);	    
    }

    protected String getText(WebResponse res) {
    	try {
			return (res == null ? null : res.getText());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}    
	}

    protected WebResponse doSendRequest(String command) {
    	return doSendRequest(command, wc);
    }
	protected WebResponse doSendRequest(String command, WebConversation wc) {
    	log.info("Sending request: " + command);
        WebRequest request = new PostMethodWebRequest(API_URL);
        request.setParameter("content", command);
        WebResponse response = null;
        try {
            response = wc.getResource(request);
            if (response != null && response.getResponseCode() == HTTP_OK) {
            	log.info("Got response: " + response.getText());
                return response;
            } else {
                log.warn("Someting wrong with response: " + response);                   
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
	}

	protected String xmlFileToString(String fileName) {
    	// first check the cache
    	String res = cache.get(fileName);
    	if (res != null)
    		return res;
    	
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = getClass().getResourceAsStream(fileName);
            if (is == null)
            	throw new IllegalArgumentException("Cannot open " + fileName);
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            
        	} finally {
        		if (is != null)
        			is.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        res = sb.toString();
        cache.put(fileName, res);
        return res;
    }

//    public Document stringToDom(String xmlSource) throws SAXException, ParserConfigurationException, IOException {
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        return builder.parse(new InputSource(new StringReader(xmlSource)));
//    }

	protected boolean resultMatch(String response, String exampleResponsePath) {
		if (response == null) {
			throw new IllegalArgumentException("'response' is null");
		}		
		String expectedResponse = xmlFileToString(exampleResponsePath);
		
		return resultStringsMatch(response, expectedResponse);
	}
	
    protected boolean resultStringsMatch(String response, String example) {
    	JAXBElement<IeapiType> exampleResponseType = unmarshal(example);
    	ResponseType exampleRsp = exampleResponseType.getValue().getResponse();
    	
    	JAXBElement<IeapiType> responseType = unmarshal(response);
    	ResponseType rsp = responseType.getValue().getResponse();

    	if (rsp.getTid() != null ^ exampleRsp.getTid() != null) {
    		return false;
    	} else
    		if (rsp.getTid() != null && exampleRsp.getTid() != null && !rsp.getTid().equals(exampleRsp.getTid())) {
    			return false;
    		}
    	if (rsp.getResult() != null ^ exampleRsp.getResult() != null) {
    		return false;
    	} else if (rsp.getResult() != null && exampleRsp.getResult() != null) {
    		if (rsp.getResult().getCode() != exampleRsp.getResult().getCode())
    			return false;
    		if (rsp.getResult().getValue() != null ^ exampleRsp.getResult().getValue() != null) {
    			return false;
    		} else if (rsp.getResult().getValue() != null && exampleRsp.getResult().getValue() != null
                    && !compareResultValues(exampleRsp.getResult().getValue(), rsp.getResult().getValue())) {
    			return false;
    		}
    		if (rsp.getResult().getReason() != null ^ exampleRsp.getResult().getReason() != null) {
    			return false;
    		} else if (rsp.getResult().getReason() != null && exampleRsp.getResult().getReason() != null) {
    			return rsp.getResult().getReason().getCode().equals(exampleRsp.getResult().getReason().getCode());
    		}
    	}
    	if (rsp.getResData() != null ^ exampleRsp.getResData() != null) {
    		return false;
    	}
    	return true;        
    }

    private boolean compareResultValues(ErrValueType expected, ErrValueType current) {
    	Element elExpected = filterForElement(expected.getContent());
    	Element elCurrent = filterForElement(current.getContent());
    	
    	if (elExpected != null ^ elCurrent != null)
    		return false;
    	else if (
    			elExpected != null 
    			&& !(elExpected.getLocalName().equals(elCurrent.getLocalName()) 
    					&&
    				 elExpected.getNamespaceURI().equals(elCurrent.getNamespaceURI()))) 
    		{
    			return false;
    		}
    	else 
    		return true; // both Elements are null
    		
	}

	private Element filterForElement(List<Object> content) {
		for (Object o: content) {
			if (o instanceof Element)
				return (Element) o;
		}
		return null;
	}

	protected Object getResultDataValueFromString(String response) {
        JAXBElement<IeapiType> responseType = unmarshal(response);
        return ((JAXBElement) responseType.getValue().getResponse().getResData().getAny().get(0)).getValue();
    }

    protected Object getResultFromString(String response) {
        JAXBElement<IeapiType> responseType = unmarshal(response);
        return responseType.getValue().getResponse().getResult();
    }
    
    synchronized protected JAXBElement<IeapiType> unmarshal(String content) {
    	try {
    		StringReader sr = new StringReader(content);
    		JAXBElement<IeapiType> responseType;
			responseType = (JAXBElement<IeapiType>) um.unmarshal(sr);
			return responseType;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
    }

    protected Object getResultDataValueFromFile(String path) {
    	return getResultDataValueFromString(xmlFileToString(path));    	
    }

    protected Object getResultFromFile(String path) {
        return getResultFromString(xmlFileToString(path));
    }

    protected void assertResultMatch(String msg, String response, String exampleResponsePath) {
    	assertTrue(resultMatch(response, exampleResponsePath), msg + ", unexpected result:\n" + response + "\nexpected:\n" + xmlFileToString(exampleResponsePath));
    }
    
    protected void assertResultDontMatch(String msg, String response, String exampleResponsePath) {
    	assertFalse(resultMatch(response, exampleResponsePath), msg + ", unexpected result:\n" + response+ "\nexpected:\n" + xmlFileToString(exampleResponsePath));
    }

    protected String testRR(String message, String requestPath, String responsePath) {
    	String result = sendRequest(xmlFileToString(requestPath));
    	assertResultMatch(message, result,  responsePath);
    	return result;
    }
}

