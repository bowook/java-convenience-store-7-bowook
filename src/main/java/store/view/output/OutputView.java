package store.view.output;

import java.util.List;
import store.domain.GeneralProduct;
import store.domain.PromotionProduct;
import store.domain.Receipt;
import store.domain.ReceiptItem;
import store.domain.Storage;
import store.exception.ConvenienceStoreException;

public class OutputView {

    public void writeStorageStatus(Storage storage) {
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        System.out.println(OutputMessage.WELCOME_MESSAGE.getOutputMessage());
        System.out.println(OutputMessage.SHOW_CURRENT_ITEMS.getOutputMessage());
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        writeInitPromotionProducts(storage);
        writeInitOnlyGeneralProducts(storage);
    }

    public void writeReceipt(Receipt receipt, String userAnswer) {
        writeReceiptMenuHeader();
        writeReceiptMenuName(receipt);
        writePresentation(receipt);
        System.out.println(OutputMessage.PERFORATION_LINE.getOutputMessage());
        writeUserTotalReceipt(receipt, userAnswer);
    }

    private void writeUserTotalReceipt(Receipt receipt, String userAnswer) {
        writeShowTotalPurchaseAmount(receipt);
        writeShowTotalPromotionDiscountAmount(receipt);
        writeShowTotalMembershipDiscountAmount(receipt, userAnswer);
        writeShowTotalPaymentAmount(receipt, userAnswer);
    }

    private void writeShowTotalPaymentAmount(Receipt receipt, String userAnswer) {
        System.out.printf(OutputMessage.SHOW_TOTAL_PAYMENT_AMOUNT.getOutputMessage(),
                receipt.totalPurchaseAmount() - receipt.totalPromotionDiscount() - receipt.validateMembership(
                        userAnswer));
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
    }

    private void writeShowTotalMembershipDiscountAmount(Receipt receipt, String userAnswer) {
        System.out.printf(OutputMessage.SHOW_TOTAL_MEMBERSHIP_DISCOUNT_AMOUNT.getOutputMessage(),
                receipt.validateMembership(userAnswer));
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
    }

    private void writeShowTotalPromotionDiscountAmount(Receipt receipt) {
        System.out.printf(OutputMessage.SHOW_TOTAL_PROMOTION_DISCOUNT_AMOUNT.getOutputMessage(),
                receipt.totalPromotionDiscount());
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
    }

    private void writeShowTotalPurchaseAmount(Receipt receipt) {
        System.out.printf(OutputMessage.SHOW_TOTAL_PURCHASE_AMOUNT.getOutputMessage(), receipt.getTotalPurchaseCount(),
                receipt.totalPurchaseAmount());
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
    }

    private void writePresentation(Receipt receipt) {
        System.out.println(OutputMessage.SHOW_RECEIPT_PRESENTATION_HEADER.getOutputMessage());
        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            if (receiptItem.getGetQuantity() != 0) {
                System.out.printf(OutputMessage.SHOW_RECEIPT_PRESENTATION_AMOUNT.getOutputMessage(),
                        receiptItem.getItemName(), receiptItem.getGetQuantity());
                System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
            }
        }
    }

    private void writeReceiptMenuName(Receipt receipt) {
        System.out.println(OutputMessage.SHOW_RECEIPT_MENU_NAME.getOutputMessage());
        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            System.out.printf((OutputMessage.SHOW_RECEIPT_USER_PAYMENT_PRODUCT.getOutputMessage()),
                    receiptItem.getItemName(), receiptItem.getTotalBuyQuantity(),
                    receiptItem.getPrice() * receiptItem.getTotalBuyQuantity());
            System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        }
    }

    private void writeReceiptMenuHeader() {
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        System.out.println(OutputMessage.SHOW_RECEIPT_HEADER.getOutputMessage());
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
                System.out.println(generalProduct);
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
