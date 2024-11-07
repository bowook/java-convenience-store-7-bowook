package store.view.input;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public String readItems() {
        System.out.println(InputMessage.READ_ITEMS_MESSAGE.getInputMessage());
        return readLine();
    }

    private String readLine() {
        return Console.readLine();
    }
}
