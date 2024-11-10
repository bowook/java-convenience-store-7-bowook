package store.controller;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;
import java.util.function.Supplier;
import store.constant.CommonMessage;
import store.constant.CommonValue;
import store.constant.SignMessage;
import store.domain.GeneralProduct;
import store.domain.PromotionProduct;
import store.domain.Receipt;
import store.domain.ReceiptItem;
import store.domain.Storage;
import store.exception.ConvenienceStoreException;
import store.service.StorageService;
import store.utils.Calculator;
import store.utils.Compare;
import store.utils.Parser;
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
        } while (retryFlag.equalsIgnoreCase(CommonMessage.YES.getCommonMessage()));
    }

    private void processPurchase(List<String> purchaseProduct, Receipt receipt) {
        for (String detail : purchaseProduct) {
            List<String> item = List.of(detail.split(SignMessage.HYPHEN.getSign()));
            PromotionProduct product = storage.findPromotionProduct(item.get(CommonValue.ZERO.getValue()));
            if (product != null && product.getPromotion().isActive(DateTimes.now())) {
                processPromotionProduct(product, Parser.parse(item.get(CommonValue.ONE.getValue())), receipt);
                continue;
            }
            processGeneralPurchase(item, receipt);
        }
    }

    private void processGeneralPurchase(List<String> item, Receipt receipt) {
        purchaseGeneralProduct(item.get(CommonValue.ZERO.getValue()),
                Integer.parseInt(item.get(CommonValue.ONE.getValue())), receipt);
    }

    private void purchaseGeneralProduct(String itemName, int itemQuantity, Receipt receipt) {
        GeneralProduct generalProduct = storage.findGeneralProduct(itemName);
        storage.subtractGeneralProduct(generalProduct, itemQuantity);
        receipt.addItem(new ReceiptItem(itemName, itemQuantity, CommonValue.ZERO.getValue(),
                Integer.parseInt(generalProduct.getPrice())));
    }

    private void processPromotionProduct(PromotionProduct promotionProduct, int itemQuantity, Receipt receipt) {
        int stock = Calculator.calculateRemainStock(promotionProduct, itemQuantity);
        int purchase = Calculator.calculateUserRemain(promotionProduct, itemQuantity);
        boolean freeTag = userOneMoreFree(purchase, stock, promotionProduct, itemQuantity, receipt);
        boolean supplementTag = supplementStock(itemQuantity, stock, promotionProduct, receipt);
        useStock(freeTag, supplementTag, promotionProduct, itemQuantity, receipt);
    }

    private boolean userOneMoreFree(int leftBuy, int leftStock, PromotionProduct product, int count, Receipt receipt) {
        if (Compare.checkFreeOneMore(leftBuy, leftStock, product)) {
            storage.subtractPromotionProduct(product, count);
            if (checkUserAnswer(product).equals(CommonMessage.YES.getCommonMessage())) {
                storage.subtractPromotionProduct(product, product.getPromotionGetGet());
                return oneMoreReceipt(receipt, count, leftBuy, product);
            }
            return noOneMoreReceipt(receipt, count, leftBuy, product);
        }
        return false;
    }

    private boolean oneMoreReceipt(Receipt receipt, int count, int leftBuy, PromotionProduct product) {
        List<Integer> getBuySet = Calculator.calculateOneMore(count, leftBuy, product);
        String productName = product.getName();
        int buy = getBuySet.get(CommonValue.ZERO.getValue());
        int get = getBuySet.get(CommonValue.ONE.getValue()) + CommonValue.ONE.getValue();
        receipt.addItem(new ReceiptItem(productName, buy, get, Parser.parse(product.getPrice())));
        return true;
    }

    private boolean noOneMoreReceipt(Receipt receipt, int count, int leftBuy, PromotionProduct product) {
        List<Integer> getBuySet = Calculator.calculateOneMore(count, leftBuy, product);
        String productName = product.getName();
        int buy = getBuySet.get(CommonValue.ZERO.getValue());
        int get = getBuySet.get(CommonValue.ONE.getValue());
        receipt.addItem(new ReceiptItem(productName, buy, get, Parser.parse(product.getPrice())));
        return true;
    }

    private boolean supplementStock(int quantity, int stock, PromotionProduct product, Receipt receipt) {
        if (Compare.checkSupplementStock(stock)) {
            int noPromotion = Calculator.calculateNoPromotion(product);
            int beforePromotionQuantity = product.getQuantity();
            storage.subtractPromotionProduct(product, noPromotion);
            String userAnswer = checkUserNoPromotion(product, Calculator.minus(quantity, noPromotion));
            return suppleGeneralProduct(product, userAnswer, Calculator.minus(quantity, noPromotion), receipt,
                    beforePromotionQuantity);
        }
        return false;
    }

    private boolean suppleGeneralProduct(PromotionProduct product, String answer, int quantity, Receipt receipt,
                                         int prevQuantity) {
        GeneralProduct generalProduct = storage.findGeneralProduct(product.getName());
        int currentQuantity = product.getQuantity();
        if (answer.equals(CommonMessage.YES.getCommonMessage())) {
            storage.subtractGeneralProduct(generalProduct, Calculator.minus(quantity, currentQuantity));
            storage.subtractPromotionProduct(product, currentQuantity);
            return noDiscountPurchase(receipt, quantity, prevQuantity, currentQuantity, product);
        }
        return noOneMoreReceipt(receipt, prevQuantity, currentQuantity, product);
    }

    private boolean noDiscountPurchase(Receipt receipt, int quantity, int count, int leftBuy, PromotionProduct item) {
        List<Integer> getBuySet = Calculator.calculateGetAndBuy(count, leftBuy, item);
        String productName = item.getName();
        int buy = getBuySet.get(CommonValue.ZERO.getValue()) + quantity;
        int get = getBuySet.get(CommonValue.ONE.getValue());
        receipt.addItem(new ReceiptItem(productName, buy, get, Integer.parseInt(item.getPrice())));
        return true;
    }

    private void useStock(boolean freeTag, boolean suppleTag, PromotionProduct product, int quantity, Receipt receipt) {
        if (!freeTag && !suppleTag) {
            storage.subtractPromotionProduct(product, quantity);
            int free = Calculator.divide(quantity, product.getPromotionSum());
            int buyQuantity = Calculator.minus(quantity, free);
            receipt.addItem(new ReceiptItem(product.getName(), buyQuantity, free, Parser.parse(product.getPrice())));
        }
    }

    private String userRetry() {
        return handleUserInput(inputView::readRetry);
    }

    private String userMembership() {
        return handleUserInput(inputView::readMembership);
    }

    private List<String> userPurchaseProduct() {
        return handleUserInput(() -> storage.validateStorageStatus(inputView.readItems()));
    }

    private String checkUserAnswer(PromotionProduct promotionProduct) {
        return handleUserInput(() -> inputView.readOneMoreFree(promotionProduct.getName()));
    }

    private String checkUserNoPromotion(PromotionProduct promotionProduct, int quantity) {
        return handleUserInput(() -> inputView.readNoDiscountAnswer(promotionProduct.getName(), quantity));
    }

    private <T> T handleUserInput(Supplier<T> inputSupplier) {
        while (true) {
            try {
                return inputSupplier.get();
            } catch (ConvenienceStoreException convenienceStoreException) {
                outputView.displayErrorMessage(convenienceStoreException);
            }
        }
    }

}
