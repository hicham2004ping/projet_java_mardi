package ma.prodenta.common.exceptions;

public class NotEnoughArguments extends RuntimeException {
    public NotEnoughArguments(String message) {
        super(message);
    }
}
