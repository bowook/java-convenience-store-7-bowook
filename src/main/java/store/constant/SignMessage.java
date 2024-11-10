package store.constant;

public enum SignMessage {
    COMMA(","),
    LEFT_SQUARE_BRACKET("["),
    RIGHT_SQUARE_BRACKET("]"),
    HYPHEN("-");

    private final String sign;

    SignMessage(final String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

}
