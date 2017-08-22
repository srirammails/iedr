package pl.nask.crs.iedrapi.impl.contact;

import ie.domainregistry.ieapi_1.ResponseType;
import ie.domainregistry.ieapi_contact_1.ModifyType;
import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.country.CountyNotExistsException;
import pl.nask.crs.country.CountyRequiredException;
import pl.nask.crs.country.InvalidCountryException;
import pl.nask.crs.country.InvalidCountyException;
import pl.nask.crs.iedrapi.ApiValidator;
import pl.nask.crs.iedrapi.AuthData;
import pl.nask.crs.iedrapi.ResponseTypeFactory;
import pl.nask.crs.iedrapi.ValidationCallback;
import pl.nask.crs.iedrapi.exceptions.CommandFailed;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.ParamValuePolicyErrorException;
import pl.nask.crs.iedrapi.exceptions.ReasonCode;
import pl.nask.crs.iedrapi.exceptions.Value;
import pl.nask.crs.iedrapi.impl.TypeConverter;
import pl.nask.crs.nichandle.NicHandle;
import pl.nask.crs.nichandle.exception.NicHandleEmailException;
import pl.nask.crs.security.authentication.AccessDeniedException;

public class ContactModifyCommandHandler extends AbstractContactCommandHandler<ModifyType> {

    @Override
    public ResponseType handle(AuthData auth, ModifyType command, ValidationCallback callback)
            throws AccessDeniedException, IedrApiException {
        
        ContactValidationHelper.check(command);
        ApiValidator.assertNoError(callback);
        
        String nhid = command.getId();
        
        NicHandle nh = findValidNh(auth.getUser(), nhid);

        validateCountryChange(command.getCountry(), nh);

        nh.setAddress(command.getAddr());
        nh.setCompanyName(command.getCompanyName());
        nh.setCountry(command.getCountry());
        nh.setCounty(command.getCounty());
        nh.setEmail(command.getEmail());
        nh.setFaxes(TypeConverter.stringToSet(command.getFax()));
        nh.setPhones(TypeConverter.stringToSet(command.getVoice()));
        nh.setName(nh.getName());
        
        try {
            getNicHandleAppService().save(auth.getUser(), nh, "Updated - via API");
        } catch (NicHandleEmailException e) {
            // no error
 		} catch (InvalidCountryException e) {
 			throw new ParamValuePolicyErrorException(ReasonCode.COUNTRY_DOES_NOT_EXIST, new Value("country", CONTACT_NAMESPACE, command.getCountry()));
        } catch (CountyNotExistsException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.COUNTY_DOES_NOT_EXIST, new Value("county", CONTACT_NAMESPACE, command.getCounty()));
        } catch (CountyRequiredException e) {
            throw new ParamValuePolicyErrorException(ReasonCode.COUNTY_IS_REQUIRED_FOR_THE_COUNTRY, new Value("county", CONTACT_NAMESPACE, command.getCounty()));
        } catch (InvalidCountyException e) {
			throw new ParamValuePolicyErrorException(ReasonCode.COUNTY_DOES_NOT_MATCH_COUNTRY, new Value("county", CONTACT_NAMESPACE, command.getCounty()));
        } catch (Exception e) {
           // no other exceptions should be thrown since validation was made earlier
           throw new CommandFailed(e);
        }
        
        return ResponseTypeFactory.success();
        
    }

    /**
     * Vat change validation can not be moved to app service since permissions model can deny to access save method
     * when modifying country
     *
     * @param newCountry
     * @param nh
     */
    private void validateCountryChange(String newCountry, NicHandle nh) throws ParamValuePolicyErrorException {
    	if (Validator.isEqual(newCountry, nh.getCountry()))
    		return; // no country change
        String oldVatCategory = nh.getVatCategory();
        String newVatCategory = getHelper().getCountryFactory().getCountryVatCategory(newCountry);
        if (!Validator.isEqual(oldVatCategory, newVatCategory)) {
            throw new ParamValuePolicyErrorException(ReasonCode.COUNTRY_CHANGE_FORBIDDEN, new Value("country", CONTACT_NAMESPACE, newCountry));
        }
    }


}
