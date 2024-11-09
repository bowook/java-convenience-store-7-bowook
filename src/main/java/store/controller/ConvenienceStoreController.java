package store.controller;

import java.util.List;
import store.domain.GeneralProduct;
import store.domain.PromotionProduct;
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
    private final StorageService storageService;
    private Storage storage;

    public ConvenienceStoreController(InputView inputView, OutputView outputView, StorageService storageService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storageService = storageService;
    }

    public void operate() {
        outputView.writeWelcomeMessage();
        this.storage = storageService.initializeStorage();
        outputView.writeInitStorageStatus(storage);
        List<String> userProduct = userPurchaseProduct();
        processPurchase(userProduct);
        String userMembershipAnswer = userMembership();
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

    private void processPurchase(List<String> purchaseProduct) {
        for (String product : purchaseProduct) {
            List<String> item = List.of(product.split("-"));
            checkIsPromotionProduct(item.get(0), Integer.parseInt(item.get(1)));
            purchaseGeneralProduct(item.get(0), Integer.parseInt(item.get(1)));
        }
    }

    private void purchaseGeneralProduct(String itemName, int itemQuantity) {
        GeneralProduct generalProduct = storage.findGeneralProduct(itemName);
        storage.subtractGeneralProduct(generalProduct, itemQuantity);
    }

    private void checkIsPromotionProduct(String itemName, int itemQuantity) {
        PromotionProduct promotionProduct = storage.findPromotionProduct(itemName);
        if (promotionProduct != null) {
            int remainPromotionStock = Calculator.calculateRemainStock(promotionProduct, itemQuantity);
            int remainPurchase = Calculator.calculateUserRemain(promotionProduct, itemQuantity);
            boolean freeTag = userOneMoreFree(remainPurchase, remainPromotionStock, promotionProduct, itemQuantity);
            boolean supplementTag = supplementStock(itemQuantity, remainPromotionStock, promotionProduct);
            availablePromoStock(freeTag, supplementTag, promotionProduct, itemQuantity);
            System.out.println(promotionProduct.getQuantity());
            System.out.println(storage.findGeneralProduct(itemName).getQuantity());
        }
    }

    private boolean supplementStock(int itemQuantity, int remainStock, PromotionProduct promotionProduct) {
        if (Compare.checkSupplementStock(remainStock)) {
            int noPromotion = Calculator.calculateNoPromotion(promotionProduct);
            storage.subtractPromotionProduct(promotionProduct, noPromotion);
            String userAnswer = checkUserNoPromotion(promotionProduct, itemQuantity - noPromotion);
            suppleGeneralProduct(promotionProduct, userAnswer, itemQuantity - noPromotion);
            return true;
        }
        return false;
    }

    private void suppleGeneralProduct(PromotionProduct promotionProduct, String answer, int itemQuantity) {
        GeneralProduct generalProduct = storage.findGeneralProduct(promotionProduct.getName());
        int beforeQuantity = promotionProduct.getQuantity();
        if (answer.equals("Y")) {
            storage.subtractGeneralProduct(generalProduct, itemQuantity - beforeQuantity);
            storage.subtractPromotionProduct(promotionProduct, beforeQuantity);
        }
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

    private void availablePromoStock(boolean free, boolean suppleTag, PromotionProduct promotionProduct, int quantity) {
        if (!free && !suppleTag) {
            storage.subtractPromotionProduct(promotionProduct, quantity);
        }
    }

    private boolean userOneMoreFree(int remainBuy, int remainStock, PromotionProduct product, int quantity) {
        if (Compare.checkFreeOneMore(remainBuy, remainStock, product)) {
            storage.subtractPromotionProduct(product, quantity);
            if (checkUserAnswer(product).equals("Y")) {
                storage.subtractPromotionProduct(product, product.getPromotion().getGet());
            }
            return true;
        }
        return false;
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
