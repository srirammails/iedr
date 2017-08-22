package pl.nask.crs.iedrapi.impl.domain;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_domain_1.CheckNameType;
import ie.domainregistry.ieapi_domain_1.CheckReasonType;
import ie.domainregistry.ieapi_domain_1.CheckType;
import ie.domainregistry.ieapi_domain_1.ChkDataType;
import ie.domainregistry.ieapi_domain_1.MNameType;

import java.math.BigInteger;
import java.util.List;

import pl.nask.crs.app.domains.DomainAvailability;
import pl.nask.crs.iedrapi.APICommandHandler;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.impl.AbstractCommandHandler;
import pl.nask.crs.security.authentication.AccessDeniedException;

/**
 * @author: Marcin Tkaczyk
 */
public class DomainCheckCommandHandler extends AbstractCommandHandler implements APICommandHandler<MNameType> {

    public ResponseType handle(AuthData auth, MNameType command, ValidationCallback callback) throws AccessDeniedException, IedrApiException {
        DomainValidationHelper.commandPlainCheck(command);
        List<String> domains = command.getName();

        ChkDataType response = new ChkDataType();
        CheckType ct = null;
        CheckNameType cnt = null;
        CheckReasonType crt = null;

        ApiValidator.assertNoError(callback);

        for (String domainName : domains) {
            ct = new CheckType();
            cnt = new CheckNameType();
            cnt.setValue(domainName);
            if (!DomainValidationHelper.isDomainNameSyntaxValid(domainName)) {
                cnt.setAvail(false);
                ct.setName(cnt);
                crt = new CheckReasonType();
                crt.setCode(BigInteger.valueOf(ReasonCode.DOMAIN_NAME_SYNTAX_ERROR.getCode()));
                crt.setValue(ReasonCode.DOMAIN_NAME_SYNTAX_ERROR.name());
                ct.setReason(crt);
                response.getCd().add(ct);
            } else {
                    DomainAvailability availability = getDomainAppService().checkAvailability(auth.getUser(), domainName);
                    if (availability.isDomainCreated()) {
                        cnt.setAvail(false);
                        ct.setName(cnt);
                        crt = new CheckReasonType();
                        crt.setCode(BigInteger.valueOf(ReasonCode.DOMAIN_REGISTERED.getCode()));
                        crt.setValue(ReasonCode.DOMAIN_REGISTERED.name());
                        ct.setReason(crt);
                        response.getCd().add(ct);
                    } else if (availability.isRegTicketCreated()) {
                        cnt.setAvail(false);
                        ct.setName(cnt);
                        crt = new CheckReasonType();
                        crt.setCode(BigInteger.valueOf(ReasonCode.APPLICATION_IN_PROGRESS.getCode()));
                        crt.setValue(ReasonCode.APPLICATION_IN_PROGRESS.name());
                        ct.setReason(crt);
                        response.getCd().add(ct);
                    } else if (availability.isInternalError()) {
                        cnt.setAvail(false);
                        ct.setName(cnt);
                        crt = new CheckReasonType();
                        crt.setCode(BigInteger.valueOf(ReasonCode.DOMAIN_INCORRECT_STATE_FOR_OPERATION.getCode()));
                        crt.setValue(ReasonCode.DOMAIN_INCORRECT_STATE_FOR_OPERATION.name());
                        ct.setReason(crt);
                        response.getCd().add(ct);
                    } else {
                        cnt.setAvail(true);
                        ct.setName(cnt);
                        response.getCd().add(ct);
                    }
            }
        }
        return ResponseTypeFactory.success(response);
    }
}
