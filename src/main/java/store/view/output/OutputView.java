package store.view.output;

import java.util.List;
import store.domain.GeneralProduct;
import store.domain.PromotionProduct;
import store.domain.Storage;
import store.exception.ConvenienceStoreException;

public class OutputView {

    public void writeWelcomeMessage() {
        System.out.println(OutputMessage.WELCOME_MESSAGE.getOutputMessage());
    }

    public void writeInitStorageStatus(Storage storage) {
        System.out.println(OutputMessage.SHOW_CURRENT_ITEMS.getOutputMessage());
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        writeInitPromotionProducts(storage);
        writeInitOnlyGeneralProducts(storage);
    }

    private void writeInitPromotionProducts(Storage storage) {
        for (PromotionProduct promotionProduct : storage.getPromotionProducts()) {
            System.out.println(promotionProduct);
            findEqualGeneralProductName(storage.getGeneralProducts(), promotionProduct.getName());
        }
    }

    private void findEqualGeneralProductName(List<GeneralProduct> generalProducts, String name) {
        for (GeneralProduct generalProduct : generalProducts) {
            if (generalProduct.getName().equals(name)) {
                System.out.println(generalProduct.toString());
                break;
            }
        }
    }


    private void writeInitOnlyGeneralProducts(Storage storage) {
        for (GeneralProduct generalProduct : storage.getGeneralProducts()) {
            String generalProductName = generalProduct.getName();
            boolean flag = findEqualPromotionProductName(storage.getPromotionProducts(), generalProductName);
            writeOnlyGeneralProduct(flag, generalProduct);
        }
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
    }

    private boolean findEqualPromotionProductName(List<PromotionProduct> promotionProducts, String name) {
        for (PromotionProduct promotionProduct : promotionProducts) {
            if (promotionProduct.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void writeOnlyGeneralProduct(boolean flag, GeneralProduct generalProduct) {
        if (!flag) {
            System.out.println(generalProduct.toString());
        }
    }

    public void displayErrorMessage(ConvenienceStoreException convenienceStoreException) {
        System.out.println(convenienceStoreException.getMessage());
    }

}
