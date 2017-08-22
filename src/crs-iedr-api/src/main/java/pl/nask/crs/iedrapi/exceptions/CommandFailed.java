package pl.nask.crs.iedrapi.exceptions;



public class CommandFailed extends IedrApiException {
//    public CommandFailed() {
//        super(2400);
//    }

    public CommandFailed(Exception e) {
        super(2400, e);
    }
    
    public CommandFailed(String message) {
    	super(2400, message);
    }
    
    
}
