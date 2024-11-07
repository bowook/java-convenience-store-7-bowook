package store.domain;

public class Product {
    private final String name;
    private final int price;
    private String quantity;

    protected Product(final String name, final int price, final String quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

}
