package store.view.output;

public enum OutputMessage {
    WELCOME_MESSAGE("안녕하세요. W편의점입니다."),
    SHOW_CURRENT_ITEMS("현재 보유하고 있는 상품입니다."),
    NEW_LINE(System.lineSeparator());

    private final String outputMessage;

    OutputMessage(String outputMessage) {
        this.outputMessage = outputMessage;
    }

    public String getOutputMessage() {
        return outputMessage;
    }
}
