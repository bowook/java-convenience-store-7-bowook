package store.view.output;

import java.util.List;
import store.domain.GeneralProduct;
import store.domain.PromotionProduct;
import store.domain.Storage;

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
            boolean flag = false;
            flag = findEqualGeneralProductName(storage.getGeneralProducts(), promotionProduct.getName(), flag);
            writeOutOfStock(flag, promotionProduct.getName(), promotionProduct.getPrice());
        }
    }

    private boolean findEqualGeneralProductName(List<GeneralProduct> generalProducts, String name, boolean flag) {
        for (GeneralProduct generalProduct : generalProducts) {
            if (generalProduct.getName().equals(name)) {
                System.out.println(generalProduct.toString());
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void writeOutOfStock(boolean flag, String name, String price) {
        if (!flag) {
            System.out.printf("- %s %,d원 재고 없음", name, Integer.parseInt(price));
            System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        }
    }

    private void writeInitOnlyGeneralProducts(Storage storage) {
        for (GeneralProduct generalProduct : storage.getGeneralProducts()) {
            String generalProductName = generalProduct.getName();
            boolean flag = false;
            flag = findEqualPromotionProductName(storage.getPromotionProducts(), generalProductName, flag);
            writeOnlyGeneralProduct(flag, generalProduct);
        }
    }

    private boolean findEqualPromotionProductName(List<PromotionProduct> promotionProducts, String name, boolean flag) {
        for (PromotionProduct promotionProduct : promotionProducts) {
            if (promotionProduct.getName().equals(name)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void writeOnlyGeneralProduct(boolean flag, GeneralProduct generalProduct) {
        if (!flag) {
            System.out.println(generalProduct.toString());
        }
    }
}
