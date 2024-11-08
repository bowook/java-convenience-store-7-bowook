package store.view.input;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import store.exception.ConvenienceStoreException;
import store.exception.ErrorMessage;

public class InputView {

    public List<String> readItems() {
        System.out.println(InputMessage.READ_ITEMS_MESSAGE.getInputMessage());
        List<String> splitByCommaPurchaseItem = List.of(readLine().split(","));
        return validatePurchaseItemProcess(splitByCommaPurchaseItem);
    }

    public String readNoDiscountAnswer(String itemName, int itemQuantity) {
        System.out.printf((InputMessage.NO_PROMOTION_DISCOUNT_MESSAGE.getInputMessage()) + "\n", itemName,
                itemQuantity);
        return validateUserAnswer(readLine());
    }

    public String readOneMoreFree(String itemName) {
        System.out.printf((InputMessage.ONE_MORE_FREE_MESSAGE.getInputMessage()) + "\n", itemName);
        return validateUserAnswer(readLine());
    }

    private String validateUserAnswer(String userAnswer) {
        if (!userAnswer.equals("Y") && !userAnswer.equals("N")) {
            throw ConvenienceStoreException.from(ErrorMessage.WRONG_ANSWER);
        }
        return userAnswer;
    }


    private List<String> validatePurchaseItemProcess(List<String> splitByCommaPurchaseItem) {
        validateBracketsCount(splitByCommaPurchaseItem);
        List<String> deleteBracketItem = validateBracketsFormat(splitByCommaPurchaseItem);
        validateHyphen(deleteBracketItem);
        return deleteBracketItem;
    }

    private void validateHyphen(List<String> deleteBracketItem) {
        for (String item : deleteBracketItem) {
            List<String> splitByHyphenPurchaseItem = List.of(item.split("-"));
            if (splitByHyphenPurchaseItem.size() != 2) {
                throw ConvenienceStoreException.from(ErrorMessage.INCORRECT_FORMAT);
            }
            validateItemName(splitByHyphenPurchaseItem.get(0));
            validateItemQuantity(splitByHyphenPurchaseItem.get(1));
        }
    }

    private void validateItemQuantity(String quantity) {
        try {
            Integer.parseInt(quantity);
        } catch (NumberFormatException numberFormatException) {
            throw ConvenienceStoreException.from(ErrorMessage.INCORRECT_FORMAT);
        }
    }

    private void validateItemName(String name) {
        if (name.isEmpty()) {
            throw ConvenienceStoreException.from(ErrorMessage.INCORRECT_FORMAT);
        }
        if (name.isBlank()) {
            throw ConvenienceStoreException.from(ErrorMessage.INCORRECT_FORMAT);
        }
    }


    private List<String> validateBracketsFormat(List<String> splitByCommaPurchaseItem) {
        List<String> deleteBracketItem = new ArrayList<>();
        for (String item : splitByCommaPurchaseItem) {
            if (!bracketsFormat(item)) {
                throw ConvenienceStoreException.from(ErrorMessage.INCORRECT_FORMAT);
            }
            deleteBracketItem.add(item.substring(1, item.length() - 1));
        }
        return deleteBracketItem;
    }

    private boolean bracketsFormat(String item) {
        return item.charAt(0) == '[' && item.charAt(item.length() - 1) == ']';
    }

    private void validateBracketsCount(List<String> splitByCommaPurchaseItem) {
        for (String item : splitByCommaPurchaseItem) {
            int count = bracketsCount(item);
            if (count > 2) {
                throw ConvenienceStoreException.from(ErrorMessage.INCORRECT_FORMAT);
            }
            if (count < 2) {
                throw ConvenienceStoreException.from(ErrorMessage.INCORRECT_FORMAT);
            }
        }
    }

    private int bracketsCount(String item) {
        int count = 0;
        for (char spell : item.toCharArray()) {
            if (spell == '[' || spell == ']') {
                count += 1;
            }
        }
        return count;
    }


    private String readLine() {
        return Console.readLine();
    }
}
