package store.view.input;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import store.constant.CommonMessage;
import store.constant.CommonValue;
import store.constant.SignMessage;
import store.exception.ConvenienceStoreException;
import store.exception.ErrorMessage;
import store.view.output.OutputMessage;

public class InputView {

    public List<String> readItems() {
        System.out.println(InputMessage.READ_ITEMS_MESSAGE.getInputMessage());
        List<String> splitByCommaPurchaseItem = List.of(readLine().split(SignMessage.COMMA.getSign()));
        return validatePurchaseItemProcess(splitByCommaPurchaseItem);
    }

    public String readNoDiscountAnswer(String itemName, int itemQuantity) {
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        System.out.printf((InputMessage.NO_PROMOTION_DISCOUNT_MESSAGE.getInputMessage()), itemName,
                itemQuantity);
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        return validateUserAnswer(readLine());
    }

    public String readOneMoreFree(String itemName) {
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        System.out.printf((InputMessage.ONE_MORE_FREE_MESSAGE.getInputMessage()), itemName);
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        return validateUserAnswer(readLine());
    }

    public String readMembership() {
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        System.out.println(InputMessage.MEMBERSHIP_MESSAGE.getInputMessage());
        return validateUserAnswer(readLine());
    }

    public String readRetry() {
        System.out.print(OutputMessage.NEW_LINE.getOutputMessage());
        System.out.println(InputMessage.RETRY_MESSAGE.getInputMessage());
        return validateUserAnswer(readLine());
    }

    public void closeConsole() {
        Console.close();
    }

    private String validateUserAnswer(String userAnswer) {
        if (!userAnswer.equals(CommonMessage.YES.getCommonMessage()) && !userAnswer.equals(
                CommonMessage.NO.getCommonMessage())) {
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
            List<String> splitByHyphenPurchaseItem = List.of(item.split(SignMessage.HYPHEN.getSign()));
            if (splitByHyphenPurchaseItem.size() != CommonValue.TWO.getValue()) {
                throw ConvenienceStoreException.from(ErrorMessage.INCORRECT_FORMAT);
            }
            validateItemName(splitByHyphenPurchaseItem.get(CommonValue.ZERO.getValue()));
            validateItemQuantity(splitByHyphenPurchaseItem.get(CommonValue.ONE.getValue()));
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
        List<String> delBracketItem = new ArrayList<>();
        for (String item : splitByCommaPurchaseItem) {
            if (!bracketsFormat(item)) {
                throw ConvenienceStoreException.from(ErrorMessage.INCORRECT_FORMAT);
            }
            delBracketItem.add(item.substring(CommonValue.ONE.getValue(), item.length() - CommonValue.ONE.getValue()));
        }
        return delBracketItem;
    }

    private boolean bracketsFormat(String item) {
        return item.charAt(CommonValue.ZERO.getValue()) == SignMessage.LEFT_SQUARE_BRACKET.getSign()
                .charAt(CommonValue.ZERO.getValue())
                && item.charAt(item.length() - CommonValue.ONE.getValue()) == SignMessage.RIGHT_SQUARE_BRACKET.getSign()
                .charAt(CommonValue.ZERO.getValue());
    }

    private void validateBracketsCount(List<String> splitByCommaPurchaseItem) {
        for (String item : splitByCommaPurchaseItem) {
            int count = bracketsCount(item);
            if (count != CommonValue.TWO.getValue()) {
                throw ConvenienceStoreException.from(ErrorMessage.INCORRECT_FORMAT);
            }
        }
    }

    private int bracketsCount(String item) {
        int count = CommonValue.ZERO.getValue();
        for (char spell : item.toCharArray()) {
            if (spell == SignMessage.LEFT_SQUARE_BRACKET.getSign().charAt(CommonValue.ZERO.getValue())
                    || spell == SignMessage.RIGHT_SQUARE_BRACKET.getSign().charAt(CommonValue.ZERO.getValue())) {
                count += CommonValue.ONE.getValue();
            }
        }
        return count;
    }

    private String readLine() {
        return Console.readLine();
    }

}
