package store.domain;

public class PromotionProduct {
    private final String name;
    private final String price;
    private int quantity;
    private Promotion promotion;

    public PromotionProduct(final String name, final String price, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getPromotionSum() {
        return promotion.getBuy() + promotion.getGet();
    }

    public int getPromotionGetBuy() {
        return promotion.getBuy();
    }

    public int getPromotionGetGet() {
        return promotion.getGet();
    }

    public void subtraction(int value) {
        this.quantity -= Math.abs(value);
    }

    @Override
    public String toString() {
        if (quantity == 0) {
            return String.format("- %s %,d원 재고 없음 %s", name, Integer.parseInt(price), promotion.getName());
        }
        return String.format("- %s %,d원 %s개 %s", name, Integer.parseInt(price), quantity, promotion.getName());
    }

}
