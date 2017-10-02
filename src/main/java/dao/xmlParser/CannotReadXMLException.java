package dao.xmlParser;

/**
 * Created by jacksparrow on 02.10.17.
 */
public class CannotReadXMLException extends Exception {
    public CannotReadXMLException(Exception cause) {
        super(cause);
    }

    public CannotReadXMLException(String message) {
        super(message);
    }

    public CannotReadXMLException(Exception cause, String message) {
        super(message, cause);
    }
}
