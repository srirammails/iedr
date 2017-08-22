package pl.nask.crs.iedrapi.impl.domain;

import ie.domainregistry.ieapi_domain_1.*;
import pl.nask.crs.commons.config.ApplicationConfig;
import pl.nask.crs.domains.nameservers.Nameserver;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.iedrapi.impl.CommandValidationHelper;

import java.io.*;
import java.util.*;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainValidationHelper extends CommandValidationHelper {

    public static void commandPlainCheck(ModifyType command) throws IedrApiException {
        if (command.getChg() != null && command.getAdd() != null && command.getRem() != null
                && command.getChg().getHolder() == null && command.getChg().getRenewalMode() == null
                && command.getAdd().getContact().size() == 0 && command.getRem().getContact().size() == 0
                && command.getAdd().getNs().size() == 0 && command.getRem().getNs().size() == 0)
            throw new DataManagementPolicyViolationException(ReasonCode.NOTHING_TO_CHANGE);
    }

    public static void commandPlainCheck(ApplicationConfig applicationConfig, CreateType command, String nameSpace) throws IedrApiException {
        // attribute unit of domain:period other than 'y' => PARAMETER_VALUE_POLICY_ERROR (Reason 157)
        if (command.getPeriod() != null && !command.getPeriod().getUnit().value().equals("y"))
            throw new ParamValuePolicyErrorException(ReasonCode.PERIOD_UNIT_MONTH_NOT_ALLOWED);

        if (command.getCreditCard() != null && command.getPayFromDeposit() != null)
            throw new DataManagementPolicyViolationException(ReasonCode.ACCOUNT_CARDHOLDER_AND_FROMDEPOSIT_CANT_BE_USED_SIMULTANEOUSLY);

        if (command.getChy() != null && command.getChy().length() > 10)
            throw new ParameterValueRangeErrorException(ReasonCode.INVALID_CHARITY_CODE);

        // empty or missing domain:name => REQUIRED_PARAMETER_MISSING (Reason 150)
        if (isEmpty(command.getName()))
            throw new RequiredParameterMissingException(ReasonCode.DOMAIN_NAME_MANDATORY);

        // empty or missing domain:holderName => REQUIRED_PARAMETER_MISSING (Reason 151)
        if (command.getHolder() == null || isEmpty(command.getHolder().getHolderName().getValue()))
            throw new RequiredParameterMissingException(ReasonCode.DOMAIN_HOLDER_MANDATORY);

        // empty or missing class attribute of domain:holder => REQUIRED_PARAMETER_MISSING (Reason 152)
        if (isEmpty(command.getHolder().getClazz()))
            throw new RequiredParameterMissingException(ReasonCode.HOLDER_CLASS_MANDATORY);

        // empty or missing category attribute of domain:holderName => REQUIRED_PARAMETER_MISSING (Reason 153)
        if (isEmpty(command.getHolder().getHolderName().getCategory()))
            throw new RequiredParameterMissingException(ReasonCode.HOLDER_CATEGORY_MANDATORY);

        // holder remark too long => COMMAND_SYNTAX_ERROR (Reason 153)
        if (command.getHolder().getHolderRemarks() != null && command.getHolder().getHolderRemarks().length() > 500)
            throw new RequiredParameterMissingException(ReasonCode.HOLDER_REMARK_TOO_LONG);

        // domain:name syntax => PARAMETER_VALUE_SYNTAX_ERROR (Reason 155)
        if (!isDomainNameSyntaxValid(command.getName()))
            throw new ParamValueSyntaxErrorException(ReasonCode.DOMAIN_NAME_SYNTAX_ERROR, new Value("name", nameSpace, command.getName()));

        //Namesevers validation
        checkDomainNss(command.getName(), command.getNs(), applicationConfig.getNameserverMinCount(), applicationConfig.getNameserverMaxCount(), nameSpace);
    }

    public static void commandPlainCheck(MNameType command) throws IedrApiException {
        // empty or missing domain:name => REQUIRED_PARAMETER_MISSING (Reason 150)        
        if (command.getName() == null || command.getName().size() == 0)
            throw new RequiredParameterMissingException(ReasonCode.DOMAIN_NAME_MANDATORY);
    }

    public static void commandPlainCheck(QueryType command) throws IedrApiException {
        // value of page attribute out of range => PARAMETER_VALUE_RANGE_ERROR (Reason 802)
        if (command.getPage() <= 0)
            throw new ParameterValueRangeErrorException(ReasonCode.ATTRIBUTE_PAGE_IS_OUT_OF_RANGE);

        if (command.getTransfer() != null) {
            if (command.getTransfer().getType() == null) {
                throw new RequiredParameterMissingException(ReasonCode.ATTRIBUTE_TYPE_IS_MANDATORY_FOR_TRANSFERS_QRY);
            }
        }
    }

    public static void commandPlainCheck(TransferType command) throws IedrApiException {
        // empty or missing domain:authCode => REQUIRED_PARAMETER_MISSING (Reason 281)
        if (isEmpty(command.getAuthCode()))
            throw new RequiredParameterMissingException(ReasonCode.AUTHCODE_IS_MANDATORY);
    }

    public static List<NsType> prepareNssValidateDelegation(ModifyType command, List<Nameserver> currentNameservers, String nameSpace) throws IedrApiException {
        List<String> domainNssName = new ArrayList<String>();
        List<NsType> domainNssToCheck  = new ArrayList<NsType>();
        for (Nameserver ns : currentNameservers){
            domainNssName.add(ns.getName().toLowerCase());
            NsType nsVO = new NsType();
            nsVO.setNsName(ns.getName().toLowerCase());
            nsVO.setNsAddr(ns.getIpAddressAsString());
            domainNssToCheck.add(nsVO);
        }
        if (isCommandAddNameserver(command)) {
            for (NsType nsType : command.getAdd().getNs()) {
                if (domainNssName.contains(nsType.getNsName().toLowerCase()))
                    throw new DataManagementPolicyViolationException(ReasonCode.DOMAIN_ALREADY_DELEGATED_TO_HOST_TO_ADD, new Value("nsName", nameSpace, nsType.getNsName()));
                domainNssToCheck.add(nsType);
            }
        }
        if (isCommandRemoveNameserver(command)) {
            for (NsNameType nsNameType : command.getRem().getNs()) {
                if (!domainNssName.contains(nsNameType.getNsName().toLowerCase())) {
                    throw new DataManagementPolicyViolationException(ReasonCode.DOMAIN_NOT_DELEGATED_TO_HOST_TO_REMOVE, new Value("nsName", nameSpace, nsNameType.getNsName()));
                }
                NsType nsToDelte = null;
                for (NsType nsType : domainNssToCheck) {
                    if (nsType.getNsName().equalsIgnoreCase(nsNameType.getNsName()))
                        nsToDelte = nsType;
                }
                domainNssToCheck.remove(nsToDelte);
            }
        }
        return domainNssToCheck;
    }

    private static boolean isCommandAddNameserver(ModifyType command) {
        return command.getAdd() != null && command.getAdd().getNs() != null;
    }

    private static boolean isCommandRemoveNameserver(ModifyType command) {
        return command.getRem() != null && command.getRem().getNs() != null;
    }

    public static void checkDomainNss(String domainName, List<NsType> nsList, int min, int max, String nameSpace) throws IedrApiException {
        // count of domain:ns < 2 => DATA_MANAGEMENT_POLICY_VIOLATION (Reason 159)
        if (nsList.size() < min)
            throw new DataManagementPolicyViolationException(ReasonCode.TOO_FEW_DNS);

        // count of domain:ns > 3 => DATA_MANAGEMENT_POLICY_VIOLATION (Reason 160)
        if (max >= 0 && nsList.size() > max)
            throw new DataManagementPolicyViolationException(ReasonCode.TOO_MANY_DNS);

        // duplicate domain:nsName => PARAMETER_VALUE_POLICY_ERROR (Reason 161)
        Set<String> nsSet = new HashSet<String>();
        for (NsType nT : nsList) {
            if (!nsSet.add(nT.getNsName()))
                throw new ParamValuePolicyErrorException(ReasonCode.DUPLICATE_DNS_NAME, new Value("nsName", nameSpace, nT.getNsName()));
        }

        for (NsType nsType : nsList) {
            // check host name according to RFC 1034
            if (!isNsValid(nsType.getNsName()))
                throw new ParamValueSyntaxErrorException(ReasonCode.DNS_SYNTAX_ERROR, new Value("nsName", nameSpace, nsType.getNsName()));

            // for each domain:nsAddr: domain:nsAddr (not_empty && !~ /^([0-9]*\.)+[0-9]+$/) => PARAMETER_VALUE_SYNTAX_ERROR (Reason 163)
            if (!isEmpty(nsType.getNsAddr()) && !nsType.getNsAddr().matches("(\\d{1,3}\\.){3}\\d{1,3}"))
                throw new ParamValueSyntaxErrorException(ReasonCode.IP_SYTAX_ERROR, new Value("nsAddr", nameSpace, nsType.getNsAddr()));

            // for each domain:ns: if domain:nsAddr && domain:nsName not ends with domain:name => DATA_MANAGEMENT_POLICY_VIOLATION (Reason 164)
            if (!isEmpty(nsType.getNsAddr()) && !nsType.getNsName().toLowerCase().endsWith(domainName.toLowerCase()))
                throw new DataManagementPolicyViolationException(ReasonCode.GLUE_NOT_ALLOWED, new Value("nsName", nameSpace, nsType.getNsName()));

            // for each domain:ns: if !domain:nsAddr && domain:nsName ends with domain:name => DATA_MANAGEMENT_POLICY_VIOLATION (Reason 175)
            if (isEmpty(nsType.getNsAddr()) && nsType.getNsName().toLowerCase().endsWith(domainName.toLowerCase()))
                throw new DataManagementPolicyViolationException(ReasonCode.GLUE_IS_REQUIRED, new Value("nsName", nameSpace, nsType.getNsName()));
        }
    }

}
