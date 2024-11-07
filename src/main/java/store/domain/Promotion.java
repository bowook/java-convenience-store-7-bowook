package store.domain;

import java.time.LocalDate;

public class Promotions {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotions() {
        this.onePlusOne = false;
        this.twoPlusOne = false;
        this.flashSale = false;
    }
}
