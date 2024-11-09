package store.view.input;

public enum InputMessage {
    READ_ITEMS_MESSAGE("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"),
    ONE_MORE_FREE_MESSAGE("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)"),
    NO_PROMOTION_DISCOUNT_MESSAGE("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"),
    MEMBERSHIP_MESSAGE("멤버십 할인을 받으시겠습니까? (Y/N)");

    private final String inputMessage;

    InputMessage(final String inputMessage) {
        this.inputMessage = inputMessage;
    }

    public String getInputMessage() {
        return inputMessage;
    }
}
