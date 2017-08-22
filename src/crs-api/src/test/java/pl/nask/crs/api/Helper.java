package pl.nask.crs.api;

import pl.nask.crs.api.vo.NameserverVO;
import pl.nask.crs.api.vo.PaymentRequestVO;
import pl.nask.crs.api.vo.RegistrationRequestVO;
import pl.nask.crs.api.vo.TransferRequestVO;
import pl.nask.crs.app.commons.TicketRequest;

import java.util.Arrays;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public final class Helper {

    public static RegistrationRequestVO createBasicCreateRequest(String domainName, String contact) {
        RegistrationRequestVO request = new RegistrationRequestVO();
        request.setDomainName(domainName);
        request.setDomainHolder("John Doe");
        request.setDomainHolderClass("Natural Person");
        request.setDomainHolderCategory("Personal Name");
        request.setAdminContact1NicHandle(contact);
        request.setTechContactNicHandle(contact);
        request.setNameservers(Arrays.asList(
            new NameserverVO("ns1." + domainName, "1.1.1.1"),
            new NameserverVO("ns1.example-nameserver.ie", null)));
        request.setRequestersRemark("r remark");
        request.setPeriod(1);
        request.setPeriodType(TicketRequest.PeriodType.Y);
        return request;
    }
    
    public static RegistrationRequestVO createCharityRegistrationRequest(String domainName, String contact) {
    	RegistrationRequestVO req = createBasicCreateRequest(domainName, contact);
    	req.setCharityCode("charityCode");    	
    	return req;
	}

    public static PaymentRequestVO createBasicCCPaymentRequest() {
        PaymentRequestVO paymentRequest = new PaymentRequestVO();
        paymentRequest.setCurrency("EUR");
        paymentRequest.setCardNumber("4263971921001307");
        paymentRequest.setCardType("VISA");
        paymentRequest.setCardHolderName("John Doe");
        paymentRequest.setCardExpDate("0115");
        return paymentRequest;
    }

    public static PaymentRequestVO createBasicDebitPaymentRequest() {
        PaymentRequestVO paymentRequest = new PaymentRequestVO();
        paymentRequest.setCurrency("EUR");
        paymentRequest.setCardNumber("6304939304310009610");
        paymentRequest.setCardType("LASER");
        paymentRequest.setCardHolderName("John Doe");
        paymentRequest.setCardExpDate("0115");
        return paymentRequest;
    }

    public static TransferRequestVO createBasicTransferRequest(String domainName, String techContact, boolean charity, int periodInYears, String authCode) {
        TransferRequestVO request = new TransferRequestVO();
        request.setDomainName(domainName);
        request.setTechContactNicHandle(techContact);
        request.setNameservers(Arrays.asList(
                new NameserverVO("ns1." + domainName, "1.1.1.1"),
                new NameserverVO("ns1.example-nameserver.ie", null)));
        request.setRequestersRemark("r remark");
        request.setAuthCode(authCode);
        if (!charity) {
            request.setPeriod(periodInYears);
            request.setPeriodType(TicketRequest.PeriodType.Y);
        }
        return request;
    }
}
