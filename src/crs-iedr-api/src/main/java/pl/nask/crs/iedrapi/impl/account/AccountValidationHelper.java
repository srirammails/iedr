package pl.nask.crs.iedrapi.impl.account;

import ie.domainregistry.ieapi_account_1.*;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.iedrapi.impl.CommandValidationHelper;
import java.util.*;

/**
 * @author: Marcin Tkaczyk
 */
public class AccountValidationHelper extends CommandValidationHelper {

    private static void domainsPlainCheck(List<String> domains) throws IedrApiException {
        for (String domain : domains) {
            if (Validator.isEmpty(domain)) {
                throw new RequiredParameterMissingException(ReasonCode.ACCOUNT_DOMAIN_IS_MANDATORY_FIELD);
            }
        }
    }

    public static void commandPlainCheck(DepositFundsType command) throws IedrApiException {
        checkCreditCard(command.getCard());
    }

    public static void checkCreditCard(CreditCardType card) throws IedrApiException {
        if (isEmpty(card.getCardHolderName()))
            throw new RequiredParameterMissingException(ReasonCode.ACCOUNT_CARDHOLDER_IS_MANDATORY_FIELD);

        if (isEmpty(card.getCardNumber()))
            throw new RequiredParameterMissingException(ReasonCode.ACCOUNT_CARDNUMBER_IS_MANDATORY_FIELD);

        if (card.getCardType() == null)
            throw new RequiredParameterMissingException(ReasonCode.ACCOUNT_CARDTYPE_IS_MANDATORY_FIELD);

        if (!isEmpty(card.getCvnNumber())) {
            if (isEmpty(card.getCvnPresInd())) {
                throw new RequiredParameterMissingException(ReasonCode.CVN_PRESIND_IS_MANDATORY);
            } else  {
                if (!card.getCvnNumber().matches("^[0-9]{3}$"))
                    throw new ParamValueSyntaxErrorException(ReasonCode.CVN_NUMBER_SYNTAX_ERROR);
                if (!card.getCvnPresInd().matches("^[1234]{1}$"))
                    throw new ParamValueSyntaxErrorException(ReasonCode.CVN_PRESIND_SYNTAX_ERROR);
            }
        }

        //TODO what about ACCOUNT_UNSUPPORTED_CARDTYPE
    }

	public static void commandPlainCheck(PayType command) throws IedrApiException {
        domainsPlainCheck(command.getDomain());

        if (command.getMethod().getCard() != null && command.getMethod().getDeposit() != null)
            throw new DataManagementPolicyViolationException(ReasonCode.ACCOUNT_CARDHOLDER_AND_FROMDEPOSIT_CANT_BE_USED_SIMULTANEOUSLY);

        if (command.getMethod().getCard() != null)
            checkCreditCard(command.getMethod().getCard());
	}
}
