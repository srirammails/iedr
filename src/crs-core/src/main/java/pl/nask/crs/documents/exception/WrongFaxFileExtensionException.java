package pl.nask.crs.documents.exception;

/**
 * @author Piotr Tkaczyk
 */
public class WrongFaxFileExtensionException extends DocumentGeneralException {
    
    public WrongFaxFileExtensionException() {
    }

    public WrongFaxFileExtensionException(String s) {
        super(s);
    }

    public WrongFaxFileExtensionException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public WrongFaxFileExtensionException(Throwable throwable) {
        super(throwable);
    }
}
