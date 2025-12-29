package ma.prodenta.common.exceptions;

public class EmailExisteException extends RuntimeException {
    public EmailExisteException(String message) {
        super(message);
    }
}
