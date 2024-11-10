package store.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Receipt;
import store.domain.ReceiptItem;
import store.view.output.OutputMessage;
import store.view.output.OutputView;

public class OutputViewTest {
    @Test
    @DisplayName("영수증 출력 검증")
    void 영수증_출력_검증() {
        //given
        Receipt receipt = new Receipt();
        receipt.addItem(new ReceiptItem("사이다", 2, 1, 1000));
        OutputView outputView = new OutputView();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        String expectedOutput =
                System.lineSeparator() + OutputMessage.SHOW_RECEIPT_HEADER.getOutputMessage() + System.lineSeparator() +
                        OutputMessage.SHOW_RECEIPT_MENU_NAME.getOutputMessage() + System.lineSeparator() +
                        String.format(OutputMessage.SHOW_RECEIPT_USER_PAYMENT_PRODUCT.getOutputMessage(), "사이다", 3,
                                3000)
                        + System.lineSeparator() +
                        OutputMessage.SHOW_RECEIPT_PRESENTATION_HEADER.getOutputMessage() + System.lineSeparator() +
                        String.format(OutputMessage.SHOW_RECEIPT_PRESENTATION_AMOUNT.getOutputMessage(), "사이다", 1)
                        + System.lineSeparator() +
                        OutputMessage.PERFORATION_LINE.getOutputMessage() + System.lineSeparator() +
                        String.format(OutputMessage.SHOW_TOTAL_PURCHASE_AMOUNT.getOutputMessage(), 3, 3000)
                        + System.lineSeparator() +
                        String.format(OutputMessage.SHOW_TOTAL_PROMOTION_DISCOUNT_AMOUNT.getOutputMessage(), 1000)
                        + System.lineSeparator() +
                        String.format(OutputMessage.SHOW_TOTAL_MEMBERSHIP_DISCOUNT_AMOUNT.getOutputMessage(), 0)
                        + System.lineSeparator() +
                        String.format(OutputMessage.SHOW_TOTAL_PAYMENT_AMOUNT.getOutputMessage(), 2000)
                        + System.lineSeparator();
        //when
        outputView.writeReceipt(receipt, "N");

        //then
        assertThat(outputStream.toString()).isEqualTo(expectedOutput);
        System.setOut(originalOut);
    }
}
