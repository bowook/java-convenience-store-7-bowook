package store.utils;

import java.util.List;
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

    public static int minus(int numberOne, int numberTwo) {
        return numberOne - numberTwo;
    }

    public static int divide(int numberOne, int numberTwo) {
        return numberOne / numberTwo;
    }

    public static List<Integer> calculateGetAndBuy(int count, int leftBuy, PromotionProduct product) {
        int get = (count - leftBuy) / product.getPromotionSum();
        int buy = (count - leftBuy) - get;
        return List.of(buy, get);
    }

    public static List<Integer> calculateOneMore(int count, int leftBuy, PromotionProduct product) {
        int get = (count - leftBuy) / product.getPromotionSum();
        int buy = (count - leftBuy) - get + product.getPromotionGetBuy();
        return List.of(buy, get);
    }


}
