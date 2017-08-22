package pl.nask.crs.iedrapi.impl.account;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_account_1.CreditCardType;
import ie.domainregistry.ieapi_account_1.DepositFundsDataType;
import ie.domainregistry.ieapi_account_1.DepositFundsType;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import pl.nask.crs.commons.MoneyUtils;
import pl.nask.crs.commons.exporter.ExportException;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ParamValuePolicyErrorException;
import pl.nask.crs.iedrapi.exceptions.ParameterValueRangeErrorException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.payment.Deposit;
import pl.nask.crs.payment.LimitsPair;
import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.exceptions.PaymentException;
import pl.nask.crs.security.authentication.AccessDeniedException;

/**
 * @author: Marcin Tkaczyk
 */
public class AccountDepositFundsCommandHandler extends AbstractAccountCommandHandler<DepositFundsType> {

    protected static Logger log = Logger.getLogger(AccountDepositFundsCommandHandler.class);

    public ResponseType handle(AuthData auth, DepositFundsType command, ValidationCallback callback) throws AccessDeniedException, IedrApiException {
        AccountValidationHelper.commandPlainCheck(command);

        LimitsPair limits = getPaymentAppService().getDepositLimits(auth.getUser());
        if (command.getValue() < limits.getMin()) {
            throw new ParameterValueRangeErrorException(ReasonCode.TOO_LOW_VALUE_TO_DEPOSIT);
        }
        if (command.getValue() > limits.getMax()) {
            throw new ParameterValueRangeErrorException(ReasonCode.TOO_HIGH_VALUE_TO_DEPOSIT);
        }
        
        ApiValidator.assertNoError(callback);

        CreditCardType card = command.getCard();
        PaymentRequest preq = new PaymentRequest("EUR",
                command.getValue(),
                card.getCardNumber(),
                DateUtils.getCardExpDateAsString(card.getExpiryDate()),
                card.getCardType().value(),
                card.getCardHolderName(),
                !Validator.isEmpty(card.getCvnNumber()) ? Integer.valueOf(card.getCvnNumber()) : null,
                !Validator.isEmpty(card.getCvnPresInd()) ? Integer.valueOf(card.getCvnPresInd()) : null);

        Deposit newDeposit = null;
        try {
        	// FIXME: remove reference to PaymentRequestVO
            newDeposit = getPaymentAppService().depositFunds(auth.getUser(), preq);
        } catch (PaymentException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.ACCOUNT_PAYMENT_SYSTEM_ERROR, e.getMessage());
        } catch (ExportException e) {
        	throw new ParamValuePolicyErrorException(ReasonCode.ACCOUNT_PAYMENT_SYSTEM_ERROR, e.getMessage());
		}

        BigDecimal oldValue = BigDecimal.valueOf(0);
        BigDecimal commandValue = BigDecimal.valueOf(command.getValue());
        BigDecimal depositCloseBal = BigDecimal.valueOf(newDeposit.getCloseBal());

        if (isDepositUpdated(depositCloseBal, commandValue))
            oldValue = depositCloseBal.subtract(commandValue);

        DepositFundsDataType res = new DepositFundsDataType();
        res.setOldValue(MoneyUtils.getRoudedAndScaledValue(oldValue));
        res.setNewValue(MoneyUtils.getRoudedAndScaledValue(newDeposit.getCloseBal()));
        res.setTransDate(new Date());
 
        return ResponseTypeFactory.success(res);
    }

    private boolean isDepositUpdated(BigDecimal closeBal, BigDecimal commandValue) {
        return closeBal.compareTo(commandValue) != 0;

    }
}
