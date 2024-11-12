package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReceiptTest {
    @Test
    @DisplayName("영수증 물품 추가 검증")
    void 영수증_물픔_추가_검증() {
        //given
        Receipt receipt = new Receipt();
        ReceiptItem item1 = new ReceiptItem("감자칩", 1, 1, 1000);
        ReceiptItem item2 = new ReceiptItem("콜라", 2, 1, 1000);

        //when
        receipt.addItem(item1);
        receipt.addItem(item2);

        //then
        assertThat(receipt.getReceiptItems()).containsExactly(item1, item2);
    }

    @Test
    @DisplayName("전체 구매 금액 검증")
    void 전체_구매_금액_검증() {
        //given
        Receipt receipt = new Receipt();
        receipt.addItem(new ReceiptItem("감자칩", 1, 1, 1000));
        receipt.addItem(new ReceiptItem("콜라", 2, 1, 1000));

        //when
        int totalPurchaseAmount = receipt.totalPurchaseAmount();

        //then
        assertThat(totalPurchaseAmount).isEqualTo(5000);
    }

    @Test
    @DisplayName("전체 행사 할인 금액 검증")
    void 전체_행사_할인_금액_검증() {
        //given
        Receipt receipt = new Receipt();
        receipt.addItem(new ReceiptItem("감자칩", 1, 1, 1000));
        receipt.addItem(new ReceiptItem("콜라", 2, 1, 1000));

        //when
        int totalDiscount = receipt.totalPromotionDiscount();

        //then
        assertThat(totalDiscount).isEqualTo(2000);
    }

    @Test
    @DisplayName("전체 구매 개수 검증")
    void 전체_구매_개수_검증() {
        //given
        Receipt receipt = new Receipt();
        receipt.addItem(new ReceiptItem("감자칩", 1, 1, 1000));
        receipt.addItem(new ReceiptItem("콜라", 2, 1, 1000));

        //when
        int totalCount = receipt.getTotalPurchaseCount();

        //then
        assertThat(totalCount).isEqualTo(5);
    }

    @Test
    @DisplayName("멤버쉽 Y 검증 - 프로모션 상품일 때 적용 X")
    void 프로모션_상품_멤버쉽_YES_검증() {
        //given
        Receipt receipt = new Receipt();
        receipt.addItem(new ReceiptItem("감자칩", 1, 1, 1000));
        receipt.addItem(new ReceiptItem("콜라", 2, 1, 1000));
        receipt.addItem(new ReceiptItem("사이다", 10, 0, 1000));

        //when
        int discount = receipt.validateMembership("Y");

        //then
        assertThat(discount).isEqualTo(3000);
    }

    @Test
    @DisplayName("멤버쉽 Y 검증 - 프로모션 상품아닐 때 적용 O")
    void 프로모션_상품_아닐_때_YES_검증() {
        //given
        Receipt receipt = new Receipt();
        receipt.addItem(new ReceiptItem("감자칩", 1, 0, 1000));
        receipt.addItem(new ReceiptItem("콜라", 2, 0, 1000));

        //when
        int discount = receipt.validateMembership("Y");

        //then
        assertThat(discount).isEqualTo(900);
    }

    @Test
    @DisplayName("멤버쉽 N 검증")
    void 멤버쉽_N_검증() {
        //given
        Receipt receipt = new Receipt();
        receipt.addItem(new ReceiptItem("감자칩", 1, 0, 1000));
        receipt.addItem(new ReceiptItem("콜라", 2, 0, 1000));

        //when
        int discount = receipt.validateMembership("N");

        //then
        assertThat(discount).isEqualTo(0);
    }

    @Test
    @DisplayName("멤버쉽 할인 금액 최대 8000원 검증")
    void 멤버쉽_할인_금액_최대_검증() {
        //given
        Receipt receipt = new Receipt();
        receipt.addItem(new ReceiptItem("감자칩", 10, 0, 1000));
        receipt.addItem(new ReceiptItem("콜라", 10, 0, 1000));
        receipt.addItem(new ReceiptItem("사이다", 10, 0, 1000));

        //when
        int discount = receipt.validateMembership("Y");

        //then
        assertThat(discount).isEqualTo(8000);
    }

}
