package store.view.output;

public enum OutputMessage {
    WELCOME_MESSAGE("안녕하세요. W편의점입니다."),
    SHOW_CURRENT_ITEMS("현재 보유하고 있는 상품입니다."),
    SHOW_RECEIPT_HEADER("==============W 편의점=================="),
    SHOW_RECEIPT_MENU_NAME("상품명\t\t\t\t 수량\t\t 금액"),
    SHOW_RECEIPT_INIT_FORMAT("%-14s%-10s%-10s"),
    SHOW_RECEIPT_PRESENTATION_HEADER("===============증\t정================="),
    SHOW_PRESENTATION_FORMAT("%-14s%-10s"),
    PERFORATION_LINE("======================================="),
    SHOW_DISCOUNT_FORMAT("%-14s%15s"),
    SHOW_PAYMENT_FORMAT("%-14s%15s"),
    NEW_LINE(System.lineSeparator());

    private final String outputMessage;

    OutputMessage(String outputMessage) {
        this.outputMessage = outputMessage;
    }

    public String getOutputMessage() {
        return outputMessage;
    }

}
