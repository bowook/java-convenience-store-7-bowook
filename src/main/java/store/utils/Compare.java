package store.utils;

import store.constant.CommonValue;
import store.domain.PromotionProduct;

public class Compare {
    public static boolean checkFreeOneMore(int remainingPurchase, int remainPromotionStock,
                                           PromotionProduct promotionProduct) {
        return (remainingPurchase == promotionProduct.getPromotionGetBuy()
                && remainPromotionStock >= CommonValue.ONE.getValue());

    }

    public static boolean checkSupplementStock(int remainStock) {
        return remainStock < CommonValue.ZERO.getValue();
    }

}
