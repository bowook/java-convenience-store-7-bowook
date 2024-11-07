package store.exception;

public class ConvenienceStoreException extends IllegalArgumentException {
    private ConvenienceStoreException(final ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
    }

    public static ConvenienceStoreException from(final ErrorMessage errorMessage) {
        return new ConvenienceStoreException(errorMessage);
    }
}
