package pl.nask.crs.iedrapi;

import ie.domainregistry.ieapi_1.ErrValueType;
import ie.domainregistry.ieapi_1.ExtAnyType;
import ie.domainregistry.ieapi_1.ReasonType;
import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_1.ResultType;
import ie.domainregistry.ieapi_account_1.DepositFundsDataType;
import ie.domainregistry.ieapi_account_1.PayDataType;
import ie.domainregistry.ieapi_contact_1.CreDataType;
import ie.domainregistry.ieapi_contact_1.InfDataType;
import ie.domainregistry.ieapi_contact_1.ObjectFactory;
import ie.domainregistry.ieapi_contact_1.ResDataType;
import ie.domainregistry.ieapi_domain_1.ChkDataType;

import java.math.BigInteger;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.exceptions.Value;

public final class ResponseTypeFactory {
	
	private static final Logger log = Logger.getLogger(ResponseTypeFactory.class);

	private static final ObjectFactory contactFactory = new ObjectFactory();
    private static final ie.domainregistry.ieapi_domain_1.ObjectFactory domainFactory = new ie.domainregistry.ieapi_domain_1.ObjectFactory();
    private static final ie.domainregistry.ieapi_ticket_1.ObjectFactory ticketFactory = new ie.domainregistry.ieapi_ticket_1.ObjectFactory();
    private static final ie.domainregistry.ieapi_account_1.ObjectFactory accountFactory = new ie.domainregistry.ieapi_account_1.ObjectFactory();

    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("response_msg", Locale.getDefault());

    private ResponseTypeFactory() {
    	
    }
    
    /**
     * 1000
     * 
     * @return
     */
    private static ResponseType successWithValues(Object... values) {
        if (values == null || values.length == 0)
        	return createResponse(1000);
        
    	ExtAnyType d = new ExtAnyType();
        for (Object value: values) {
            d.getAny().add(value);
        }
        return createResponse(1000, d);
    }
    
    /**
     * 1400
     * 
     * @return
     */
    public static ResponseType successNoRes() {
        return createResponse(1400);
    }
    
    /**
     * 1500
     * 
     * @return
     */
    public static ResponseType createCCSEndSession() {
        return createResponse(1500);  
    }
    
    /**
     * 2201
     * 
     * @return
     */
    public static ResponseType authorizationError() {
        return createResponse(2201);
    }

    /**
     * Creates a response with the given response code and the valid response message.
     * No resData or tid is set, resultType contains only a responseCode and the response message 
     * @param responseCode
     * @return
     */
    public static ResponseType createResponse(int responseCode) {
        ResponseType res = new ResponseType();
        
        ResultType t = typeFor(responseCode);            

        res.setResult(t);
        
        return res;
    }
    
    
    public static ResponseType createResponse(int responseCode, ExtAnyType resData) {
        ResponseType t = createResponse(responseCode);
        t.setResData(resData);
        
        return t;
    }

    private static ResultType typeFor(int responseCode) {
        ResultType t = new ResultType();
        t.setCode(responseCode);
       
        t.setMsg(getResultMsg(responseCode));
      
        return t;
    }


    public static ResponseType success(CreDataType res) {                
        return successWithValues(contactFactory.createCreData(res));
    }

    public static ResponseType success(ie.domainregistry.ieapi_domain_1.AppDataType res) {
        return successWithValues(domainFactory.createAppData(res));
    }

    public static ResponseType success(ie.domainregistry.ieapi_domain_1.CreDataType res) {
        return successWithValues(domainFactory.createCreData(res));
    }
    
    public static ResponseType success(InfDataType res) {
        return successWithValues(contactFactory.createInfData(res));
    }

    public static ResponseType success(ie.domainregistry.ieapi_domain_1.InfDataType res) {
        return successWithValues(domainFactory.createInfData(res));
    }

    public static ResponseType success(ie.domainregistry.ieapi_ticket_1.InfDataType res) {
        return successWithValues(ticketFactory.createInfData(res));
    }

    public static ResponseType success(ChkDataType res) {
        return successWithValues(domainFactory.createChkData(res));
    }

    public static ResponseType success() {
       return successWithValues();
    }


    public static ResponseType success(ResDataType res) {
        return successWithValues(contactFactory.createResData(res));
    }

    public static ResponseType success(ie.domainregistry.ieapi_domain_1.ResDataType res) {
        return successWithValues(domainFactory.createResData(res));
    }

    public static ResponseType success(ie.domainregistry.ieapi_account_1.ChkDepositDataType res) {
        return successWithValues(accountFactory.createChkDepositData(res));
    }

    public static ResponseType success(ie.domainregistry.ieapi_account_1.ResDataType res) {
        return successWithValues(accountFactory.createResData(res));
    }
 
    public static ResponseType success(DepositFundsDataType res) {
        return successWithValues(accountFactory.createDepositFundsData(res));
    }
	public static ResponseType success(PayDataType res) {	
		return successWithValues(accountFactory.createPayData(res));
	}

    public static ResponseType createResponse(int resultCode, ReasonCode reasonCode, String reasonMessage, Value value) {
        ResponseType resType = createResponse(resultCode);
        if (reasonCode != null) {
            ReasonType rt = new ReasonType();
            rt.setCode(BigInteger.valueOf(reasonCode.getCode()));
            if (reasonMessage == null)
                rt.setValue(getReasonMsg(reasonCode.getCode()));
            else
                rt.setValue(reasonMessage);
            resType.getResult().setReason(rt);
        }
        if (value != null) {
            ErrValueType errValue = new ErrValueType();            
			errValue.getContent().add(new JAXBElement<String>(new QName(value.getNamespace(), value.getTag(), ""), String.class, null, value.getValue()));
            resType.getResult().setValue(errValue);
        }
        return resType;
    }


    private static String getReasonMsg(int code) {
        try {
            return resourceBundle.getString("reason_" + code);
        } catch (Exception e) {            
        	log.warn("Couldn't find the message for the reason code: " + code, e);
            return null;
        }
    }
    
    
    private static String getResultMsg(int code) {
        try {
            return resourceBundle.getString("result_" + code);
        } catch (Exception e) {
        	log.warn("Couldn't find the message for the result code: " + code, e);
            return null;
        }
    }


	public static ResponseType success(ie.domainregistry.ieapi_ticket_1.ResDataType resDataType) {
		return successWithValues(ticketFactory.createResData(resDataType));
	}


}
