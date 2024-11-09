package store.domain;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private final List<ReceiptItem> receiptItems = new ArrayList<>();

    public void addItem(ReceiptItem receiptItem) {
        receiptItems.add(receiptItem);
    }

    public List<ReceiptItem> getReceiptItems() {
        return receiptItems;
    }

    public int totalPurchaseAmount() {
        int sum = 0;
        for (ReceiptItem receiptItem : receiptItems) {
            sum += receiptItem.getTotalBuyQuantity() * receiptItem.getPrice();
        }
        return sum;
    }

    public int totalPromotionDiscount() {
        int sum = 0;
        for (ReceiptItem receiptItem : receiptItems) {
            if (receiptItem.getGetQuantity() != 0) {
                sum += receiptItem.getGetQuantity() * receiptItem.getPrice();
            }
        }
        return sum;
    }

    private int noTotalPromotionAmount() {
        int sum = 0;
        for (ReceiptItem receiptItem : receiptItems) {
            if (receiptItem.getGetQuantity() == 0) {
                sum += receiptItem.getBuyQuantity() * receiptItem.getPrice();
            }
        }
        return sum;
    }

    public int getTotalPurchaseCount() {
        int sum = 0;
        for (ReceiptItem receiptItem : receiptItems) {
            sum += receiptItem.getTotalBuyQuantity();
        }
        return sum;
    }

    public int validateMembership(String userAnswer) {
        if (userAnswer.equals("Y")) {
            return getMembershipDiscount();
        }
        return 0;
    }

    private int getMembershipDiscount() {
        int membershipDiscount = (int) (noTotalPromotionAmount() * 0.3);
        if (membershipDiscount > 8000) {
            membershipDiscount = 8000;
        }
        return membershipDiscount;
    }


}
