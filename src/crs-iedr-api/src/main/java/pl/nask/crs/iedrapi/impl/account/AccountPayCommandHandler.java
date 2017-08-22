package pl.nask.crs.iedrapi.impl.account;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_account_1.CreditCardType;
import ie.domainregistry.ieapi_account_1.PayDataType;
import ie.domainregistry.ieapi_account_1.PayType;
import ie.domainregistry.ieapi_account_1.PayType.Method;

import pl.nask.crs.commons.Period;
import pl.nask.crs.commons.utils.DateUtils;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.payment.PaymentRequest;
import pl.nask.crs.payment.PaymentSummary;
import pl.nask.crs.security.authentication.AccessDeniedException;

import java.util.Map;

public class AccountPayCommandHandler extends AbstractAccountCommandHandler<PayType>{

    @Override
    public ResponseType handle(AuthData auth, PayType command, ValidationCallback callback) throws AccessDeniedException, IedrApiException {
        AccountValidationHelper.commandPlainCheck(command);

        Period period = Period.fromYears(command.getPeriod() == null ? 1 : command.getPeriod());

        PaymentMethod paymentMethod;
        Method m = command.getMethod();
        boolean isCard = (m.getCard() != null);
        boolean isDeposit = (m.getDeposit() != null);
        if (!isCard && isDeposit) {
            paymentMethod = PaymentMethod.ADP;
        } else if (isCard && !isDeposit) {
            paymentMethod = PaymentMethod.CC;
        } else {
            // TODO: handle this error somehow
            throw new IllegalArgumentException("Unhandled payment method");
        }
        ApiValidator.assertNoError(callback);
        PaymentRequest request = null;
        if (paymentMethod == PaymentMethod.CC) {
            CreditCardType card = m.getCard();
            request = new PaymentRequest("EUR",
                    card.getCardNumber(),
                    DateUtils.getCardExpDateAsString(card.getExpiryDate()),
                    card.getCardType().value(),
                    card.getCardHolderName(),
                    !Validator.isEmpty(card.getCvnNumber()) ? Integer.valueOf(card.getCvnNumber()) : null,
                    !Validator.isEmpty(card.getCvnPresInd()) ? Integer.valueOf(card.getCvnPresInd()) : null);
        }

        Map<String, Period> domainsWithPeriods = TypeConverter.convertToDomainsPeriodMap(command.getDomain(), period);
        try {
            PaymentSummary methodRes = getPaymentAppService().pay(auth.getUser(), domainsWithPeriods, paymentMethod, request, command.isTest());
            PayDataType res = new PayDataType();
            res.setFee(methodRes.getFee());
            res.setTotal(methodRes.getTotal());
            res.setVat(methodRes.getVat());
            res.setOrderId("" + methodRes.getOrderId()); //FIX ME ""?

            return ResponseTypeFactory.success(res);
        } catch (Exception e) {
            throw handleException(e);
        }
    }
}
