package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import store.domain.GeneralProduct;
import store.domain.PromotionProduct;
import store.domain.Receipt;
import store.domain.ReceiptItem;
import store.domain.Storage;
import store.exception.ConvenienceStoreException;
import store.service.StorageService;
import store.utils.Calculator;
import store.utils.Compare;
import store.view.input.InputView;
import store.view.output.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final Storage storage;

    public ConvenienceStoreController(InputView inputView, OutputView outputView, StorageService storageService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storage = storageService.initializeStorage();
    }

    public void operate() {
        String retryFlag;
        do {
            outputView.writeWelcomeMessage();
            outputView.writeStorageStatus(storage);
            Receipt receipt = new Receipt();
            processPurchase(userPurchaseProduct(), receipt);
            outputView.writeReceipt(receipt, userMembership());
            retryFlag = userRetry();
        } while (retryFlag.equalsIgnoreCase("Y"));
    }

    private String userRetry() {
        while (true) {
            try {
                return inputView.readRetry();
            } catch (ConvenienceStoreException convenienceStoreException) {
                outputView.displayErrorMessage(convenienceStoreException);
            }
        }
    }

    private String userMembership() {
        while (true) {
            try {
                return inputView.readMembership();
            } catch (ConvenienceStoreException convenienceStoreException) {
                outputView.displayErrorMessage(convenienceStoreException);
            }
        }
    }

    private List<String> userPurchaseProduct() {
        while (true) {
            try {
                return storage.validateStorageStatus(inputView.readItems());
            } catch (ConvenienceStoreException convenienceStoreException) {
                outputView.displayErrorMessage(convenienceStoreException);
            }
        }
    }

    private void processPurchase(List<String> purchaseProduct, Receipt receipt) {
        for (String product : purchaseProduct) {
            List<String> item = List.of(product.split("-"));
            PromotionProduct promotionProduct = storage.findPromotionProduct(item.get(0));
            if (promotionProduct != null && promotionProduct.getPromotion().isActive(DateTimes.now())) {
                checkIsPromotionProduct(promotionProduct, Integer.parseInt(item.get(1)), receipt);
                continue;
            }
            purchaseGeneralProduct(item.get(0), Integer.parseInt(item.get(1)), receipt);
        }
    }

    private void purchaseGeneralProduct(String itemName, int itemQuantity, Receipt receipt) {
        GeneralProduct generalProduct = storage.findGeneralProduct(itemName);
        storage.subtractGeneralProduct(generalProduct, itemQuantity);
        receipt.addItem(new ReceiptItem(itemName, itemQuantity, 0, Integer.parseInt(generalProduct.getPrice())));
    }

    private void checkIsPromotionProduct(PromotionProduct promotionProduct, int itemQuantity, Receipt receipt) {
        int stock = Calculator.calculateRemainStock(promotionProduct, itemQuantity);
        int purchase = Calculator.calculateUserRemain(promotionProduct, itemQuantity);
        boolean freeTag = userOneMoreFree(purchase, stock, promotionProduct, itemQuantity, receipt);
        boolean supplementTag = supplementStock(itemQuantity, stock, promotionProduct, receipt);
        useStock(freeTag, supplementTag, promotionProduct, itemQuantity, receipt);
    }

    private boolean supplementStock(int quantity, int stock, PromotionProduct product, Receipt receipt) {
        if (Compare.checkSupplementStock(stock)) {
            int noPromotion = Calculator.calculateNoPromotion(product);
            int beforePromotionQuantity = product.getQuantity();
            storage.subtractPromotionProduct(product, noPromotion);
            String userAnswer = checkUserNoPromotion(product, quantity - noPromotion);
            return suppleGeneralProduct(product, userAnswer, quantity - noPromotion, receipt,
                    beforePromotionQuantity);
        }
        return false;
    }

    private boolean suppleGeneralProduct(PromotionProduct product, String answer, int quantity, Receipt receipt,
                                         int prevQuantity) {
        GeneralProduct generalProduct = storage.findGeneralProduct(product.getName());
        int currentQuantity = product.getQuantity();
        if (answer.equals("Y")) {
            storage.subtractGeneralProduct(generalProduct, quantity - currentQuantity);
            storage.subtractPromotionProduct(product, currentQuantity);
            return noDiscountPurchase(receipt, quantity, prevQuantity, currentQuantity, product);
        }
        return noOneMoreReceipt(receipt, prevQuantity, currentQuantity, product);
    }


    private String checkUserNoPromotion(PromotionProduct promotionProduct, int quantity) {
        while (true) {
            try {
                return inputView.readNoDiscountAnswer(promotionProduct.getName(), quantity);
            } catch (ConvenienceStoreException convenienceStoreException) {
                outputView.displayErrorMessage(convenienceStoreException);
            }
        }
    }

    private void useStock(boolean freeTag, boolean suppleTag, PromotionProduct product, int quantity, Receipt receipt) {
        if (!freeTag && !suppleTag) {
            storage.subtractPromotionProduct(product, quantity);
            int free = quantity / product.getPromotionSum();
            receipt.addItem(
                    new ReceiptItem(product.getName(), quantity - free, free, Integer.parseInt(product.getPrice())));
        }
    }

    private boolean userOneMoreFree(int leftBuy, int leftStock, PromotionProduct product, int count, Receipt receipt) {
        if (Compare.checkFreeOneMore(leftBuy, leftStock, product)) {
            storage.subtractPromotionProduct(product, count);
            if (checkUserAnswer(product).equals("Y")) {
                storage.subtractPromotionProduct(product, product.getPromotionGetGet());
                return oneMoreReceipt(receipt, count, leftBuy, product);
            }
            return noOneMoreReceipt(receipt, count, leftBuy, product);
        }
        return false;
    }

    private boolean noDiscountPurchase(Receipt receipt, int quantity, int count, int leftBuy, PromotionProduct item) {
        List<Integer> getBuySet = Calculator.calculateGetAndBuy(count, leftBuy, item);
        String productName = item.getName();
        int buy = getBuySet.get(0) + quantity;
        int get = getBuySet.get(1);
        receipt.addItem(new ReceiptItem(productName, buy, get, Integer.parseInt(item.getPrice())));
        return true;
    }

    private boolean noOneMoreReceipt(Receipt receipt, int count, int leftBuy, PromotionProduct product) {
        List<Integer> getBuySet = Calculator.calculateOneMore(count, leftBuy, product);
        String productName = product.getName();
        int buy = getBuySet.get(0);
        int get = getBuySet.get(1);
        receipt.addItem(new ReceiptItem(productName, buy, get, Integer.parseInt(product.getPrice())));
        return true;
    }

    private boolean oneMoreReceipt(Receipt receipt, int count, int leftBuy, PromotionProduct product) {
        List<Integer> getBuySet = Calculator.calculateOneMore(count, leftBuy, product);
        String productName = product.getName();
        int buy = getBuySet.get(0);
        int get = getBuySet.get(1) + 1;
        receipt.addItem(new ReceiptItem(productName, buy, get, Integer.parseInt(product.getPrice())));
        return true;
    }

    private String checkUserAnswer(PromotionProduct promotionProduct) {
        while (true) {
            try {
                return inputView.readOneMoreFree(promotionProduct.getName());
            } catch (ConvenienceStoreException convenienceStoreException) {
                outputView.displayErrorMessage(convenienceStoreException);
            }
        }
    }

}
