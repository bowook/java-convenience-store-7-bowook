package store.domain;

public class PromotionProduct {
    private final String name;
    private final String price;
    private String quantity;
    private String promotion;

    public PromotionProduct(final String name, final String price, String quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return String.format("- %s %,d원 %s개 %s", name, Integer.parseInt(price), quantity, promotion);
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return Integer.parseInt(quantity);
    }
}
