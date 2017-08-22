package pl.nask.crs.iedrapi;

import pl.nask.crs.commons.utils.Validator;
import pl.nask.crs.iedrapi.exceptions.CommandSyntaxErrorException;
import pl.nask.crs.iedrapi.exceptions.IedrApiException;
import pl.nask.crs.iedrapi.exceptions.RequiredParameterMissingException;

public class ApiValidator {

    public static void paramNotEmpty(String paramName, String value) throws RequiredParameterMissingException {
        if (Validator.isEmpty(value))
            throw new RequiredParameterMissingException(paramName);
        
    }

	public static void assertNoError(ValidationCallback callback) throws CommandSyntaxErrorException {
		if (callback.hasSchemaErrors()) {
			throw new CommandSyntaxErrorException(callback.getErrorMessages());
		}
	}

}
