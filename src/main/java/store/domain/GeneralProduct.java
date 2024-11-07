package store.domain;

public class GeneralProduct {
    private final String name;
    private final String price;
    private String quantity;

    public GeneralProduct(final String name, final String price, final String quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        if ("재고 없음".equals(quantity)) {
            return String.format("- %s %,d원 %s", name, Integer.parseInt(price), quantity);
        }
        return String.format("- %s %,d원 %s개", name, Integer.parseInt(price), quantity);
    }
    
    public String getName() {
        return name;
    }
}
