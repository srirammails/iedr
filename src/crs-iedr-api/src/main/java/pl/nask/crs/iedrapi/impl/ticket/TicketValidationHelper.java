package pl.nask.crs.iedrapi.impl.ticket;

import ie.domainregistry.ieapi_ticket_1.NsType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.nask.crs.commons.dns.validator.DnsEntryValidator;
import pl.nask.crs.commons.dns.validator.InvalidDomainNameException;
import pl.nask.crs.commons.dns.validator.InvalidIPv4AddressException;
import pl.nask.crs.commons.dns.validator.NsGlueNotAllowedException;
import pl.nask.crs.commons.dns.validator.NsGlueRequiredException;
import pl.nask.crs.iedrapi.exceptions.DataManagementPolicyViolationException;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ParamValuePolicyErrorException;
import pl.nask.crs.iedrapi.exceptions.ParamValueSyntaxErrorException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.exceptions.Value;
import pl.nask.crs.iedrapi.impl.CommandValidationHelper;

/**
 * @author: Marcin Tkaczyk
 */
public class TicketValidationHelper extends CommandValidationHelper {

    public static void checkTicketNss(String domainName, List<NsType> nsList, int min, int max, String nameSpace) throws IedrApiException {
        // count of domain:ns < 2 => DATA_MANAGEMENT_POLICY_VIOLATION (Reason 159)
        if (nsList.size() < min)
            throw new DataManagementPolicyViolationException(ReasonCode.TOO_FEW_DNS);

        // count of domain:ns > 7 => DATA_MANAGEMENT_POLICY_VIOLATION (Reason 160)
        if (nsList.size() > max)
            throw new DataManagementPolicyViolationException(ReasonCode.TOO_MANY_DNS);

        // duplicate domain:nsName => PARAMETER_VALUE_POLICY_ERROR (Reason 161)
        Set<String> nsSet = new HashSet<String>();
        for (NsType nT : nsList) {
            if (!nsSet.add(nT.getNsName()))
                throw new ParamValuePolicyErrorException(ReasonCode.DUPLICATE_DNS_NAME, new Value("nsName", nameSpace, nT.getNsName()));
        }

        for (NsType nsType : nsList) {
        		try {
					DnsEntryValidator.checkNameserver(domainName, nsType.getNsName(), nsType.getNsAddr());
				} catch (NsGlueNotAllowedException e) {
					throw new DataManagementPolicyViolationException(ReasonCode.GLUE_NOT_ALLOWED, new Value("nsName", nameSpace, nsType.getNsName()));
				} catch (NsGlueRequiredException e) {
					throw new DataManagementPolicyViolationException(ReasonCode.GLUE_IS_REQUIRED, new Value("nsName", nameSpace, nsType.getNsName()));
				} catch (InvalidIPv4AddressException e) {
					throw new ParamValueSyntaxErrorException(ReasonCode.IP_SYTAX_ERROR, new Value("nsAddr", nameSpace, nsType.getNsAddr().get(0)));
				} catch (InvalidDomainNameException e) {
					throw new ParamValueSyntaxErrorException(ReasonCode.DNS_SYNTAX_ERROR, new Value("nsName", nameSpace, nsType.getNsName()));
				}
        }
    }

}
