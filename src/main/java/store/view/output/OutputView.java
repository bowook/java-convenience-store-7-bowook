package store.view.output;

import java.util.List;
import store.domain.GeneralProduct;
import store.domain.PromotionProduct;
import store.domain.Receipt;
import store.domain.ReceiptItem;
import store.domain.Storage;
import store.exception.ConvenienceStoreException;

public class OutputView {

    public void writeWelcomeMessage() {
        System.out.println(OutputMessage.WELCOME_MESSAGE.getOutputMessage());
    }

    public void writeStorageStatus(Storage storage) {
        System.out.println(OutputMessage.SHOW_CURRENT_ITEMS.getOutputMessage());
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        writeInitPromotionProducts(storage);
        writeInitOnlyGeneralProducts(storage);
    }

    public void writeReceipt(Receipt receipt, String userAnswer) {
        System.out.println();
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            System.out.printf("%s\t\t%d\t%d\n", receiptItem.getItemName(), receiptItem.getTotalBuyQuantity(),
                    receiptItem.getPrice() * receiptItem.getTotalBuyQuantity());
        }
        System.out.println("=============증\t정===============");
        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            if (receiptItem.getGetQuantity() != 0) {
                System.out.printf("%s\t\t%d\n", receiptItem.getItemName(), receiptItem.getGetQuantity());
            }
        }
        System.out.println("====================================");
        System.out.printf("총구매액\t\t%d\t%d\n", receipt.getTotalPurchaseCount(), receipt.totalPurchaseAmount());
        System.out.printf("행사할인\t\t\t-%,d원\n", receipt.totalPromotionDiscount());
        System.out.printf("멤버십할인\t\t\t-%,d원\n", receipt.validateMembership(userAnswer));
        System.out.printf("내실돈\t\t\t %,d원\n",
                receipt.totalPurchaseAmount() - receipt.totalPromotionDiscount() - receipt.validateMembership(
                        userAnswer));
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
