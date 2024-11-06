package store.view.input;

public enum InputMessage {
    READ_ITEMS("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");

    private final String inputMessage;

    InputMessage(final String inputMessage) {
        this.inputMessage = inputMessage;
    }

    public String getInputMessage() {
        return inputMessage;
    }
}
