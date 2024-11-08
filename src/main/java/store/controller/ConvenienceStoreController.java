package store.controller;

import java.util.List;
import store.domain.GeneralProduct;
import store.domain.PromotionProduct;
import store.domain.Storage;
import store.exception.ConvenienceStoreException;
import store.service.StorageService;
import store.view.input.InputView;
import store.view.output.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final StorageService storageService;

    public ConvenienceStoreController(InputView inputView, OutputView outputView, StorageService storageService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storageService = storageService;
    }

    public void operate() {
        outputView.writeWelcomeMessage();
        Storage storage = storageService.initializeStorage();
        outputView.writeInitStorageStatus(storage);
        List<String> userProduct = userPurchaseProduct(storage);
        processPurchase(storage, userProduct);
    }

    private List<String> userPurchaseProduct(Storage storage) {
        while (true) {
            try {
                return storage.validateStorageStatus(inputView.readItems());
            } catch (ConvenienceStoreException convenienceStoreException) {
                outputView.displayErrorMessage(convenienceStoreException);
            }
        }
    }

    private void processPurchase(Storage storage, List<String> purchaseProduct) {
        //프로모션 상품인지 확인을 하고
        //프로모션 상품이면 프로모션 상품 재고 안에서 구매할 수 있는지 확인을 하고
        //프로모션 재고 안에서 구매할 수 있으면 그 만큼 재고 차감
        //프로며선 재고 안에서 구매할 수 있는데 2+1인데 2개만 가져온 경우 1개 무료 증정 추가할거냐고 물어보고
        //사용자가 Y라고 답변하면 1개 추가로 증정해서 총 재고 3개 차감
        //사용자가 N라고 답변하면 추가 증정 없이 총 재고 2개 차감
        //프로모션 재고 안에서 구매할 수 없으면 일반 재고로 가서 남은 만큼 개수를 가져오고 구매할거냐고 물어보고
        //구매 안한다고 하면 남은 개수는 판매 안하고 프로모션 재고만 판매
        for (String product : purchaseProduct) {
            List<String> item = List.of(product.split("-"));
            String itemName = item.get(0);
            int itemQuantity = Integer.parseInt(item.get(1));
            checkIsPromotionProduct(storage, itemName, itemQuantity);
        }
    }

    private void checkIsPromotionProduct(Storage storage, String itemName, int itemQuantity) {
        PromotionProduct promotionProduct = storage.findPromotionProduct(itemName);
        if (promotionProduct != null) {
            int sum = promotionProduct.getPromotion().getBuy() + promotionProduct.getPromotion().getGet();
            int remainStock = promotionProduct.getQuantity() - (sum * (itemQuantity / sum));
            int remainProduct = itemQuantity % sum;
            //1. 한개 더 가져올거냐고 물어봐야됨
            if ((remainProduct == promotionProduct.getPromotion().getBuy() && remainStock >= sum)
                    && itemQuantity % sum == promotionProduct.getPromotion().getBuy()) {
                String userAnswer = checkUserAnswer(promotionProduct);
                if (userAnswer.equals("Y")) {
                    remainStock -= sum;
                } else if (userAnswer.equals("N")) {
                    remainStock -= remainProduct;
                }
            }
            //2. 그냥 프로모션에서 다 구매 가능한 경우
            if (remainProduct == remainStock) {
                remainStock -= remainProduct;
            }
            //프로모션 재고 안에서 다 구매를 못하는 상황
            //프로모션 재고 없음 만들고, 나머지가 있어서 일반재고도 건들어야 하는 상황
            GeneralProduct generalProduct = storage.findGeneralProduct(itemName);
            if (remainStock < 0) {
                String userAnswer = checkUserNoPromotion(promotionProduct.getName(),
                        promotionProduct.getQuantity() % sum);
                if (userAnswer.equals("Y")) {
                    int generalProductQuantity =
                            generalProduct.getQuantity() - (itemQuantity - (promotionProduct.getQuantity() / sum) * sum)
                                    + (promotionProduct.getQuantity() % sum);
                    System.out.println(generalProductQuantity);
                }
                remainStock = 0;
            }
        }
    }

    private String checkUserNoPromotion(String name, int quantity) {
        while (true) {
            try {
                return inputView.readNoDiscountAnswer(name, quantity);
            } catch (ConvenienceStoreException convenienceStoreException) {
                outputView.displayErrorMessage(convenienceStoreException);
            }
        }
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
