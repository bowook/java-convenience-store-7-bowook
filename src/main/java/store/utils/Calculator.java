package store.utils;

import store.domain.PromotionProduct;

public class Calculator {

    public static int calculateRemainStock(PromotionProduct promotionProduct, int itemQuantity) {
        int sum = promotionProduct.getPromotionSum();
        return promotionProduct.getQuantity() - ((sum * (itemQuantity / sum)) + (itemQuantity % sum));
    }

    public static int calculateUserRemain(PromotionProduct promotionProduct, int itemQuantity) {
        return itemQuantity % promotionProduct.getPromotionSum();
    }

    public static int calculateNoPromotion(PromotionProduct promotionProduct) {
        return promotionProduct.getPromotionSum() * (promotionProduct.getQuantity()
                / promotionProduct.getPromotionSum());
    }

}
