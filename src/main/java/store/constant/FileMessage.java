package store.constant;

public enum FileMessage {
    PRODUCTS_FILE_NAME("products.md"),
    PROMOTION_FILE_NAME("promotions.md"),
    NULL("null"),
    SOFT_DRINK("탄산2+1"),
    MD_RECOMMEND_PRODUCT("MD추천상품"),
    FLASH_DISCOUNT("반짝할인"),
    FILE_START_WORD("name");

    private final String fileMessage;

    FileMessage(final String fileMessage) {
        this.fileMessage = fileMessage;
    }

    public String getFileMessage() {
        return fileMessage;
    }

}
