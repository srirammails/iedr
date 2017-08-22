package pl.nask.crs.api.validation;

import java.util.List;

import pl.nask.crs.api.vo.*;
import pl.nask.crs.commons.dns.validator.DnsEntryValidator;
import pl.nask.crs.commons.dns.validator.InvalidDomainNameException;
import pl.nask.crs.commons.dns.validator.InvalidIPv4AddressException;
import pl.nask.crs.commons.dns.validator.NsGlueNotAllowedException;
import pl.nask.crs.commons.dns.validator.NsGlueRequiredException;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.domains.search.DomainSearchCriteria;
import pl.nask.crs.payment.PaymentMethod;
import pl.nask.crs.ticket.operation.SimpleDomainFieldChange;

public final class ValidationHelper {
	
	private ValidationHelper() {}
	
	public static void validate(AuthenticatedUserVO token) {
		Validator.assertNotNull(token, "user");
		Validator.assertNotEmpty(token.getUsername(), "user.username");
		Validator.assertNotEmpty(token.getAuthenticationToken(), "user.authenticationToken");
	}


    public static void validate(NicHandleEditVO nicHandle) {
        Validator.assertNotEmpty(nicHandle.getName(), "nicHanlde.name");
        Validator.assertNotEmpty(nicHandle.getCompanyName(), "nicHanlde.companyName");
        Validator.assertNotEmpty(nicHandle.getEmail(), "nicHanlde.email");
        Validator.assertNotEmpty(nicHandle.getAddress(), "nicHanlde.address");
        Validator.assertNotEmpty(nicHandle.getCounty(), "nicHanlde.county");
        Validator.assertNotEmpty(nicHandle.getCountry(), "nicHanlde.country");
    }

    public static void validate(PaymentRequestVO paymentRequest) {
    	Validator.assertNotNull(paymentRequest, "paymentRequest");
    	Validator.assertNotEmpty(paymentRequest.getCurrency(), "paymentRequest.currency");
    	Validator.assertNotEmpty(paymentRequest.getCardNumber(), "paymentRequest.cardNumber");
    	Validator.assertNotNull(paymentRequest.getCardExpDate(), "paymentRequest.cardExpDate");
    	Validator.assertNotEmpty(paymentRequest.getCardType(), "paymentRequest.cardType");
    	Validator.assertNotEmpty(paymentRequest.getCardHolderName(), "paymentRequest.cardHolderName");
    	validateCvn(paymentRequest.getCvnNumber(), paymentRequest.getCvnPresenceIndicator());
    }

    private static void validateCvn(Integer cvnNumber, Integer cvnPresenceIndicator) {
        if (cvnNumber != null) {
            Validator.assertNotNull(cvnPresenceIndicator, "paymentRequest.cvnPresenceIndicator");
            if (cvnNumber > 999 || cvnNumber <0)
                throw new IllegalArgumentException("Invalid CVN Number");
            if (!cvnPresenceIndicator.toString().matches("^[1234]{1}$"))
                throw new IllegalArgumentException("Invalid CVN Presence Indicator");
        } else {
            Validator.assertNull(cvnPresenceIndicator, "paymentRequest.cvnPresenceIndicator");
        }
    }

	public static void validate(FailureReasonsEditVO domainOperation) {
		validateNotEmpty(domainOperation.getDomainNameField(), "domainNameField");
		validateNameservers(domainOperation.getDomainNameField().getNewValue(), domainOperation.getNameservers());
		validateNotEmpty(domainOperation.getAdminContactsField(), "adminContactsField");
		validateNotEmpty(domainOperation.getTechContactsField(), "techContactsField");
		validateNotEmpty(domainOperation.getBillingContactsField(), "billingContactsField");
		validateNotEmpty(domainOperation.getDomainHolderField(), "domainHolderField");
		validateNotEmpty(domainOperation.getDomainHolderClassField(), "domainHolderClassField");
		validateNotEmpty(domainOperation.getDomainHolderCategoryField(), "domainHolderCategoryField");
		validateNotEmpty(domainOperation.getResellerAccountField(), "resellerAccountField");
		try {
			Long.parseLong(domainOperation.getResellerAccountField().getNewValue());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("resellerAccountField.newValue must be a number");
		}
	}

	private static void validateNotEmpty(List<SimpleDomainFieldChangeVO> fields, String fieldName) {
		if (Validator.isEmpty(fields)) {
			throw new IllegalArgumentException(fieldName + " required");
		} else {
            // only first element is required
            validateNotEmpty(fields.get(0), fieldName + "[" + 0 + "]");
		}
	}

	private static void validateNotEmpty(SimpleDomainFieldChangeVO field, String fieldName) {
		if (field == null) {
			throw new IllegalArgumentException(fieldName + " required");
		} else if (Validator.isEmpty(field.getNewValue())) {
			throw new IllegalArgumentException(fieldName + ".newValue cannot be empty");
		}
			
	}

	private static void validateNameservers(String domainName, List<NameserverChangeVO> nameservers) {		
		Validator.assertNotEmpty(nameservers, "nameservers");
		if (nameservers.size() <2)
			throw new IllegalArgumentException("at least 2 nameservers expected");
		
		for (NameserverChangeVO nameserver: nameservers) {
			try {
				String name = null;
				String ip = null;
				if (nameserver.getName() != null)
					name = nameserver.getName().getNewValue();
				
				if (nameserver.getIpAddress() != null)
					ip = nameserver.getIpAddress().getNewValue();
				
				DnsEntryValidator.checkNameserver(domainName, name, ip);
			} catch (NsGlueNotAllowedException e) {
				throw new IllegalArgumentException(e);
			} catch (NsGlueRequiredException e) {
				throw new IllegalArgumentException(e);
			} catch (InvalidIPv4AddressException e) {
				throw new IllegalArgumentException(e);
			} catch (InvalidDomainNameException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

    public static void validate(DomainSearchCriteria criteria) {
        Validator.assertNotNull(criteria, "criteria");
        if (!Validator.isEmpty(criteria.getContactType()) && Validator.isEmpty(criteria.getNicHandle())) {
            throw new IllegalArgumentException("criteria.nicHandle cannot be empty when criteria.contactType is set");
        }
    }

    public static boolean isEmptySimpleDomainFieldChange(SimpleDomainFieldChange<String> fieldChange) {
        return fieldChange == null
                || (Validator.isEmpty(fieldChange.getCurrentValue())
                && Validator.isEmpty(fieldChange.getNewValue()));
    }

    public static void validate(List<DomainWithPeriodVO> domains) {
        Validator.assertNotEmpty(domains, "domains");
        for (DomainWithPeriodVO d : domains)
            Validator.assertNotEmpty(d.getDomainName(), "domains.domainName");
    }

    public static void validate(PaymentMethod paymentMethod, PaymentRequestVO paymentRequestVO) {
        Validator.assertNotNull(paymentMethod, "payment method");
        switch (paymentMethod) {
            case CC:
            case DEB:
                ValidationHelper.validate(paymentRequestVO);
                break;
            case ADP:
                Validator.assertNull(paymentRequestVO, "payment request");
                break;
            default:
                throw new IllegalArgumentException("Invalid payment method");
        }
    }

    public static void validate(ReservationCriteriaVO criteriaVO) {
        Validator.assertNotNull(criteriaVO, "ReservationCriteriaVO");
        Validator.assertNotNull(criteriaVO.getPaymentMethod(), "criteriaVO.paymentMethod");
    }
}
