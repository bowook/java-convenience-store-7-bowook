package store.domain;

public class GeneralProduct {
    private final String name;
    private final String price;
    private int quantity;

    public GeneralProduct(final String name, final String price, final int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        if (quantity == 0) {
            return String.format("- %s %,d원 재고 없음", name, Integer.parseInt(price));
        }
        return String.format("- %s %,d원 %s개", name, Integer.parseInt(price), quantity);
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public void subtraction(int value) {
        this.quantity -= Math.abs(value);
    }
}
