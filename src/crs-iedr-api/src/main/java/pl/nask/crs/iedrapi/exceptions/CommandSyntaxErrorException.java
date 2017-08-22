package pl.nask.crs.iedrapi.exceptions;

import java.util.List;


public class CommandSyntaxErrorException extends IedrApiException {
    public CommandSyntaxErrorException() {
        super(2000);
    }

    public CommandSyntaxErrorException(Exception e) {
        super(2000, e);
    }

	public CommandSyntaxErrorException(List<String> errorMessages) {
		super(2000, errorMessages.toString());
	}
}
