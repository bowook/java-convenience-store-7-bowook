package store.utils;

import store.domain.PromotionProduct;

public class Compare {
    public static boolean checkFreeOneMore(int remainingPurchase, int remainPromotionStock,
                                           PromotionProduct promotionProduct) {
        return (remainingPurchase == promotionProduct.getPromotionGetBuy() && remainPromotionStock >= 1);

    }

    public static boolean checkSupplementStock(int remainStock) {
        return remainStock < 0;
    }
}
