package store.service;

import java.util.List;
import store.constant.CommonValue;
import store.domain.GeneralProduct;
import store.domain.PromotionProduct;
import store.domain.Receipt;
import store.domain.ReceiptItem;
import store.domain.Storage;
import store.utils.Calculator;
import store.utils.Parser;

public class ReceiptService {

    public static boolean oneMoreReceipt(Receipt receipt, int count, int leftBuy, PromotionProduct product) {
        List<Integer> getBuySet = Calculator.calculateOneMore(count, leftBuy, product);
        String productName = product.getName();
        int buy = getBuySet.get(CommonValue.ZERO.getValue());
        int get = getBuySet.get(CommonValue.ONE.getValue()) + CommonValue.ONE.getValue();
        receipt.addItem(new ReceiptItem(productName, buy, get, Parser.parse(product.getPrice())));
        return true;
    }

    public static boolean noOneMoreReceipt(Receipt receipt, int count, int leftBuy, PromotionProduct product) {
        List<Integer> getBuySet = Calculator.calculateOneMore(count, leftBuy, product);
        String productName = product.getName();
        int buy = getBuySet.get(CommonValue.ZERO.getValue());
        int get = getBuySet.get(CommonValue.ONE.getValue());
        receipt.addItem(new ReceiptItem(productName, buy, get, Parser.parse(product.getPrice())));
        return true;
    }

    public static boolean noDiscountPurchase(Receipt receipt, int quantity, int count, int leftBuy,
                                             PromotionProduct item) {
        List<Integer> getBuySet = Calculator.calculateGetAndBuy(count, leftBuy, item);
        String productName = item.getName();
        int buy = getBuySet.get(CommonValue.ZERO.getValue()) + quantity;
        int get = getBuySet.get(CommonValue.ONE.getValue());
        receipt.addItem(new ReceiptItem(productName, buy, get, Integer.parseInt(item.getPrice())));
        return true;
    }

    public static void purchaseGeneralProduct(Storage storage, String itemName, int itemQuantity, Receipt receipt) {
        GeneralProduct generalProduct = storage.findGeneralProduct(itemName);
        storage.subtractGeneralProduct(generalProduct, itemQuantity);
        receipt.addItem(new ReceiptItem(itemName, itemQuantity, CommonValue.ZERO.getValue(),
                Integer.parseInt(generalProduct.getPrice())));
    }

    public static void useStock(PromotionProduct product, int quantity, Receipt receipt) {
        int free = Calculator.divide(quantity, product.getPromotionSum());
        int buyQuantity = Calculator.minus(quantity, free);
        receipt.addItem(new ReceiptItem(product.getName(), buyQuantity, free, Parser.parse(product.getPrice())));
    }
}
