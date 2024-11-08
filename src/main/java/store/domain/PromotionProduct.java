package store.domain;

public class PromotionProduct {
    private final String name;
    private final String price;
    private String quantity;
    private Promotion promotion;

    public PromotionProduct(final String name, final String price, String quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return String.format("- %s %,d원 %s개 %s", name, Integer.parseInt(price), quantity, promotion.getName());
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

    public Promotion getPromotion() {
        return promotion;
    }
}
