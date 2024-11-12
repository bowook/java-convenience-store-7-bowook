package store.domain;

import java.util.ArrayList;
import java.util.List;
import store.constant.CommonMessage;
import store.constant.CommonValue;

public class Receipt {
    private final double THIRTY_PERCENT = 0.3;
    private final List<ReceiptItem> receiptItems = new ArrayList<>();

    public void addItem(ReceiptItem receiptItem) {
        receiptItems.add(receiptItem);
    }

    public List<ReceiptItem> getReceiptItems() {
        return receiptItems;
    }

    public int totalPurchaseAmount() {
        int sum = CommonValue.ZERO.getValue();
        for (ReceiptItem receiptItem : receiptItems) {
            sum += receiptItem.getTotalBuyQuantity() * receiptItem.getPrice();
        }
        return sum;
    }

    public int totalPromotionDiscount() {
        int sum = CommonValue.ZERO.getValue();
        for (ReceiptItem receiptItem : receiptItems) {
            if (receiptItem.getGetQuantity() != CommonValue.ZERO.getValue()) {
                sum += receiptItem.getGetQuantity() * receiptItem.getPrice();
            }
        }
        return sum;
    }

    public int getTotalPurchaseCount() {
        int sum = CommonValue.ZERO.getValue();
        for (ReceiptItem receiptItem : receiptItems) {
            sum += receiptItem.getTotalBuyQuantity();
        }
        return sum;
    }

    public int validateMembership(String userAnswer) {
        if (userAnswer.equals(CommonMessage.YES.getCommonMessage())) {
            return getMembershipDiscount();
        }
        return CommonValue.ZERO.getValue();
    }

    private int noTotalPromotionAmount() {
        int sum = CommonValue.ZERO.getValue();
        for (ReceiptItem receiptItem : receiptItems) {
            if (receiptItem.getGetQuantity() == CommonValue.ZERO.getValue()) {
                sum += receiptItem.getBuyQuantity() * receiptItem.getPrice();
            }
        }
        return sum;
    }

    private int getMembershipDiscount() {
        int membershipDiscount = (int) (noTotalPromotionAmount() * THIRTY_PERCENT);
        if (membershipDiscount > CommonValue.EIGHT_THOUSAND.getValue()) {
            membershipDiscount = CommonValue.EIGHT_THOUSAND.getValue();
        }
        return membershipDiscount;
    }

}
