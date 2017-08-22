package pl.nask.crs.payment.service.impl;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.payment.CardType;
import pl.nask.crs.payment.ExtendedPaymentRequest;
import pl.nask.crs.payment.ParserException;
import pl.nask.crs.payment.PaymentParser;
import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.PaymentResponse;
import pl.nask.crs.payment.TransactionType;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.payment.service.CardPaymentService;
import pl.nask.crs.payment.service.PaymentSender;

public class RealexCardPaymentService implements CardPaymentService {
	private final static String SUCCESSFULL_REALEX_RESPONSE = "00";
	private final static Logger LOG = Logger.getLogger(RealexCardPaymentService.class);
	
	private final String paymentAccount;
	private final String paymentMerchant;
	private final String paymentPassword;
	
    private final PaymentSender sender;
    
	public RealexCardPaymentService(String paymentAccount,
			String paymentMerchant, String paymentPassword, PaymentSender sender) {
		super();
		this.paymentAccount = paymentAccount;
		this.paymentMerchant = paymentMerchant;
		this.paymentPassword = paymentPassword;
		this.sender = sender;
	}

	@Override
	public void settleRealexAuthorisation(String orderId, CardAuthDetails cardAuthDetails) throws PaymentException {
		ExtendedPaymentRequest request = ExtendedPaymentRequest.authenticatedInstance(this.paymentAccount, this.paymentMerchant, this.paymentPassword, cardAuthDetails.getAuthCode(), cardAuthDetails.getPassRef(), orderId);
        processWithRealexAuthorisation(request, TransactionType.TYPE_SETTLE, "Payment authorisation settled");
    }
	
	@Override
	public void settleRealexAuthorisation(ExtendedPaymentRequest extRequest) throws PaymentException {	
		processWithRealexAuthorisation(extRequest, TransactionType.TYPE_SETTLE, "Payment authorisation settled");
	}

	@Override
	public void cancelRealexAuthorisation(String orderId, CardAuthDetails cardAuthDetails) throws PaymentException {
		ExtendedPaymentRequest request = ExtendedPaymentRequest.authenticatedInstance(this.paymentAccount, this.paymentMerchant, this.paymentPassword, cardAuthDetails.getAuthCode(), cardAuthDetails.getPassRef(), orderId);
		processWithRealexAuthorisation(request, TransactionType.TYPE_VOID, "Payment authorisation cancelled");
	}
	
	@Override
	public void cancelRealexAuthorisation(ExtendedPaymentRequest extRequest) throws PaymentException {
		processWithRealexAuthorisation(extRequest, TransactionType.TYPE_VOID, "Payment authorisation cancelled");		
	}

    private void processWithRealexAuthorisation(ExtendedPaymentRequest request, TransactionType txType, String msgOnSuccess) throws PaymentException {
        Validator.assertNotNull(request, "request");
        if (request.isAutosettle()) {
            throw new PaymentException("Cannot process autosettled transaction!");
        }
        request.setType(txType);
        PaymentResponse response = null;
        try {
            response = processPaymentTransaction(request);
        } catch (ParserException e) {
            throw new PaymentException(e.getMessage(), e);
        }
        if (!SUCCESSFULL_REALEX_RESPONSE.equals(response.getResult())) {
            LOG.error(request.getOrderId() + " Payment error (" + response.getResult() + ") " + response.getMessage());
            throw new PaymentException(response.getMessage());
        } else {
            LOG.info(request.getOrderId() + msgOnSuccess + " (" + response.getResult() + ") " + response.getMessage());
        }
    }

    private PaymentResponse processPaymentTransaction(ExtendedPaymentRequest paymentRequest) throws ParserException, PaymentException {
        String commandXML = PaymentParser.toXML(paymentRequest);
        String responseXML = sender.send(commandXML);
        return PaymentParser.fromXML(responseXML);
    }

	@Override
	public ExtendedPaymentRequest authorisePaymentTransaction(PaymentRequest request, CardType cardType) throws PaymentException {
        Validator.assertNotNull(request, "request");
        LOG.debug("before Realex auth");
        ExtendedPaymentRequest ret = getAuthorisePaymentRequest(request, cardType);
        PaymentResponse response = null;
        try {
            response = processPaymentTransaction(ret);
        } catch (ParserException e) {
            throw new PaymentException("Problem with parsing response", e);
        }
        if (!SUCCESSFULL_REALEX_RESPONSE.equals(response.getResult())) {
            LOG.error(ret.getOrderId() + " Payment error (" + response.getResult() + ") " + response.getMessage());
            throw new PaymentException(response.getMessage());
        } else {
            LOG.info(ret.getOrderId() + " Payment authorised (" + response.getResult() + ") " + response.getMessage());
            ret.setAuthorisationData(response.getAuthcode(), response.getPasref(), response.getBank(), response.getCountry());
        }
        LOG.debug("after Realex auth");
        return ret;
    }

    private ExtendedPaymentRequest getAuthorisePaymentRequest(PaymentRequest request, CardType cardType) {
        ExtendedPaymentRequest ret = null;
        switch (cardType) {
            case CREDIT:
                ret = ExtendedPaymentRequest.newCreditInstance(this.paymentAccount, this.paymentMerchant, this.paymentPassword, request);
                LOG.debug("Credit transaction.");
                break;
            case DEBIT:
                ret = ExtendedPaymentRequest.newDebitInstance(this.paymentAccount, this.paymentMerchant, this.paymentPassword, request);
                LOG.debug("Debit transaction");
                break;
            default:
                throw new IllegalArgumentException("Invalid card type: " + cardType);
        }
        return ret;
    }
}
