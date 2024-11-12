package store.domain;

public class ReceiptItem {
    private final String itemName;
    private final int buyQuantity;
    private final int getQuantity;
    private final int price;

    public ReceiptItem(final String itemName, final int buyQuantity, final int getQuantity, final int price) {
        this.itemName = itemName;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getTotalBuyQuantity() {
        return buyQuantity + getQuantity;
    }

    public int getGetQuantity() {
        return getQuantity;
    }

    public int getPrice() {
        return price;
    }

}
