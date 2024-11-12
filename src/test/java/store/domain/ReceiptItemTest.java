package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReceiptItemTest {
    @Test
    @DisplayName("이름 검증")
    void 이름_검증() {
        //given
        ReceiptItem receiptItem = new ReceiptItem("감자칩", 2, 1, 1000);

        //when
        String itemName = receiptItem.getItemName();

        //then
        assertThat(itemName).isEqualTo("감자칩");
    }

    @Test
    @DisplayName("구매 개수 검증")
    void 구매_개수_검증() {
        //given
        ReceiptItem receiptItem = new ReceiptItem("감자칩", 2, 2, 1000);

        //when
        int buyQuantity = receiptItem.getBuyQuantity();

        //then
        assertThat(buyQuantity).isEqualTo(2);
    }

    @Test
    @DisplayName("구매와 증정 전체 개수 검증")
    void 구매_증정_전체_개수_검증() {
        //given
        ReceiptItem receiptItem = new ReceiptItem("감자칩", 2, 2, 1000);

        //when
        int totalBuyQuantity = receiptItem.getTotalBuyQuantity();

        //then
        assertThat(totalBuyQuantity).isEqualTo(4);
    }

    @Test
    @DisplayName("증정 개수 검증")
    void 증정_개수_검증() {
        //given
        ReceiptItem receiptItem = new ReceiptItem("감자칩", 3, 1, 1000);

        //when
        int getQuantity = receiptItem.getGetQuantity();

        //then
        assertThat(getQuantity).isEqualTo(1);
    }

    @Test
    @DisplayName("가격 검증")
    void 가격_검증() {
        //given
        ReceiptItem receiptItem = new ReceiptItem("감자칩", 2, 1, 1000);

        //when
        int price = receiptItem.getPrice();

        //then
        assertThat(price).isEqualTo(1000);
    }

}
