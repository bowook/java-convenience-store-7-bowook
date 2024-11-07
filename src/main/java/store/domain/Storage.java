package store.domain;

import java.util.List;

public class Storage {
    private final List<GeneralProduct> generalProducts;
    private final List<PromotionProduct> promotionProducts;

    public Storage(final List<GeneralProduct> generalProducts, final List<PromotionProduct> promotionProducts) {
        this.generalProducts = generalProducts;
        this.promotionProducts = promotionProducts;
    }

    public List<GeneralProduct> getGeneralProducts() {
        return generalProducts;
    }

    public List<PromotionProduct> getPromotionProducts() {
        return promotionProducts;
    }
}
