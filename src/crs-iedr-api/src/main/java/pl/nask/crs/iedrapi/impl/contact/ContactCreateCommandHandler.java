package pl.nask.crs.iedrapi.impl.contact;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_contact_1.CreDataType;
import ie.domainregistry.ieapi_contact_1.CreateType;

import java.util.Date;

import pl.nask.crs.accounts.exceptions.AccountNotActiveException;
import pl.nask.crs.app.nichandles.wrappers.NewNicHandle;
import pl.nask.crs.country.CountyNotExistsException;
import pl.nask.crs.country.CountyRequiredException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.*;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.security.authentication.AccessDeniedException;

public class ContactCreateCommandHandler extends AbstractContactCommandHandler<CreateType> {
    
    @Override
    public ResponseType handle(AuthData auth, CreateType command, ValidationCallback callback) throws AccessDeniedException, IedrApiException {
        
        ContactValidationHelper.check(command);
        ApiValidator.assertNoError(callback);
        
        NewNicHandle nnh = new NewNicHandle();        
        
        nnh.setName(command.getName());
        nnh.setAddress(command.getAddr());
        nnh.setCompanyName(command.getCompanyName());
        nnh.setEmail(command.getEmail());
        nnh.setCountry(command.getCountry());
        nnh.setCounty(command.getCounty() == null ? "" : command.getCounty());
        nnh.setFaxes(TypeConverter.stringToSet(command.getFax()));
        nnh.setPhones(TypeConverter.stringToSet(command.getVoice()));

        String hostmastersRemark = "Created - via API";
        try {
            NicHandle loggedIn = getNicHandleAppService().get(auth.getUser(), auth.getUsername());
            
            nnh.setAccountNumber(loggedIn.getAccount().getId());
            
            NicHandle createdNH = null;
            try {
            	createdNH = getNicHandleAppService().create(auth.getUser(), nnh, hostmastersRemark, false);
            } catch (NicHandleEmailException e) {
            	createdNH = getNicHandleAppService().get(auth.getUser(), e.getNicHandleId());
            }
            
            CreDataType res = new CreDataType();
            res.setId(createdNH.getNicHandleId());
            Date creationDate = createdNH.getRegistrationDate();            
            res.setCrDate(creationDate);
            
            return ResponseTypeFactory.success(res);            
            
        } catch (AccountNotActiveException e) {
            // can not add nichandle to inactive account  
            throw new DataManagementPolicyViolationException(ReasonCode.INACTIVE_ACCOUNT);        
 		} catch (InvalidCountryException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.COUNTRY_DOES_NOT_EXIST, new Value("country", CONTACT_NAMESPACE, command.getCountry()));
       } catch (CountyNotExistsException e) {
           throw new ParamValuePolicyErrorException(ReasonCode.COUNTY_DOES_NOT_EXIST, new Value("county", CONTACT_NAMESPACE, command.getCounty()));
        } catch (CountyRequiredException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.COUNTY_IS_REQUIRED_FOR_THE_COUNTRY, new Value("county", CONTACT_NAMESPACE, command.getCounty()));
       } catch (InvalidCountyException e) {
           throw new ParamValuePolicyErrorException(ReasonCode.COUNTY_DOES_NOT_MATCH_COUNTRY, new Value("county", CONTACT_NAMESPACE, command.getCounty()));
        } catch (Exception e) {
            throw new CommandFailed(e);
        }               
    }

}
