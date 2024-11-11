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
        String name = String.format("%-14s", "내실돈").replace(" ", "\u3000");
        String price = String.format("%,d", receipt.totalPurchaseAmount() - receipt.totalPromotionDiscount() -
                receipt.validateMembership(userAnswer));
        System.out.printf(OutputMessage.SHOW_PAYMENT_FORMAT.getOutputMessage(), name, price);
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
    }

    private void writeShowTotalMembershipDiscountAmount(Receipt receipt, String userAnswer) {
        String name = String.format("%-14s", "멤버십할인").replace(" ", "\u3000");
        String price = String.format("-%,d", receipt.validateMembership(userAnswer));
        System.out.printf(OutputMessage.SHOW_DISCOUNT_FORMAT.getOutputMessage(), name, price);
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
    }

    private void writeShowTotalPromotionDiscountAmount(Receipt receipt) {
        String name = String.format("%-14s", "행사할인").replace(" ", "\u3000");
        String price = String.format("-%,d", receipt.totalPromotionDiscount());
        System.out.printf(OutputMessage.SHOW_DISCOUNT_FORMAT.getOutputMessage(), name, price);
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
    }

    private void writeShowTotalPurchaseAmount(Receipt receipt) {
        String name = String.format("%-14s", "총구매액").replace(" ", "\u3000");
        String quantity = String.format("%-10d", receipt.getTotalPurchaseCount());
        String price = String.format("%,-10d", receipt.totalPurchaseAmount());
        System.out.printf(OutputMessage.SHOW_RECEIPT_INIT_FORMAT.getOutputMessage(), name, quantity, price);
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
    }

    private void writePresentation(Receipt receipt) {
        System.out.println(OutputMessage.SHOW_RECEIPT_PRESENTATION_HEADER.getOutputMessage());
        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            if (receiptItem.getGetQuantity() != 0) {
                String name = String.format("%-14s", receiptItem.getItemName()).replace(" ", "\u3000");
                String quantity = String.format("%,-10d", receiptItem.getGetQuantity());
                System.out.printf(OutputMessage.SHOW_PRESENTATION_FORMAT.getOutputMessage(), name, quantity);
                System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
            }
        }
    }

    private void writeReceiptMenuName(Receipt receipt) {
        System.out.println(OutputMessage.SHOW_RECEIPT_MENU_NAME.getOutputMessage());
        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            String name = String.format("%-14s", receiptItem.getItemName()).replace(" ", "\u3000");
            String quantity = String.format("%-10d", receiptItem.getTotalBuyQuantity());
            String price = String.format("%,-10d", receiptItem.getPrice() * receiptItem.getTotalBuyQuantity());
            System.out.printf(OutputMessage.SHOW_RECEIPT_INIT_FORMAT.getOutputMessage(), name, quantity, price);
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
