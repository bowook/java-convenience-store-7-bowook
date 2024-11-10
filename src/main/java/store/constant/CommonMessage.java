package store.constant;

public enum CommonMessage {
    YES("Y"),
    NO("N");
    private final String commonMessage;

    CommonMessage(final String commonMessage) {
        this.commonMessage = commonMessage;
    }

    public String getCommonMessage() {
        return commonMessage;
    }

}
