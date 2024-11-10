package store.view.output;

public enum OutputMessage {
    WELCOME_MESSAGE("안녕하세요. W편의점입니다."),
    SHOW_CURRENT_ITEMS("현재 보유하고 있는 상품입니다."),
    SHOW_RECEIPT_HEADER("==============W 편의점================="),
    SHOW_RECEIPT_MENU_NAME("상품명\t\t\t\t수량\t\t\t금액"),
    SHOW_RECEIPT_USER_PAYMENT_PRODUCT("%s\t\t\t\t%d\t\t\t%,d"),
    SHOW_RECEIPT_PRESENTATION_HEADER("===============증\t정================"),
    SHOW_RECEIPT_PRESENTATION_AMOUNT("%s\t\t\t\t%d"),
    PERFORATION_LINE("======================================"),
    SHOW_TOTAL_PURCHASE_AMOUNT("총구매액\t\t\t\t%d\t\t\t%,d"),
    SHOW_TOTAL_PROMOTION_DISCOUNT_AMOUNT("행사할인\t\t\t\t\t\t\t-%,d"),
    SHOW_TOTAL_MEMBERSHIP_DISCOUNT_AMOUNT("멤버십할인\t\t\t\t\t\t-%,d"),
    SHOW_TOTAL_PAYMENT_AMOUNT("내실돈\t\t\t\t\t\t\t%,d"),
    NEW_LINE(System.lineSeparator());

    private final String outputMessage;

    OutputMessage(String outputMessage) {
        this.outputMessage = outputMessage;
    }

    public String getOutputMessage() {
        return outputMessage;
    }
}
