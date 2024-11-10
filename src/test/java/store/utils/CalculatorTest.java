package store.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;
import store.domain.PromotionProduct;

public class CalculatorTest {

    @Test
    @DisplayName("남은 수량 계산 검증")
    void 남은_수량_계산_검증() {
        //given
        PromotionProduct promotionProduct = new PromotionProduct("콜라", "1000", 10,
                new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30"));
        int itemQuantity = 3;

        //when
        int remainStock = Calculator.calculateRemainStock(promotionProduct, itemQuantity);

        //then
        assertThat(remainStock).isEqualTo(7);
    }

    @Test
    @DisplayName("사용자가 구매해야하는 남은 개수 검증")
    void 사용자_구매_남은_개수_검증() {
        //given
        PromotionProduct promotionProduct = new PromotionProduct("콜라", "1000", 10,
                new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30"));
        int itemQuantity = 10;

        //when
        int remainUserPurchase = Calculator.calculateUserRemain(promotionProduct, itemQuantity);

        //then
        assertThat(remainUserPurchase).isEqualTo(1);
    }

    @Test
    @DisplayName("프로모션 적용 개수 검증")
    void 프로모션_적용_개수_검증() {
        //given
        PromotionProduct promotionProduct = new PromotionProduct("콜라", "1000", 10,
                new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30"));

        //when
        int promotionCount = Calculator.calculateNoPromotion(promotionProduct);

        //then
        assertThat(promotionCount).isEqualTo(9);
    }

    @Test
    @DisplayName("뺄셈 검증")
    void 뺄셈_검증() {
        //given
        int numberOne = 2;
        int numberTwo = 1;

        //when
        int subtractionValue = Calculator.minus(numberOne, numberTwo);

        //then
        assertThat(subtractionValue).isEqualTo(1);
    }

    @Test
    @DisplayName("나눗셈 검증")
    void 나눗셈_검증() {
        //given
        int numberOne = 2;
        int numberTwo = 1;

        //when
        int divideValue = Calculator.divide(numberOne, numberTwo);

        //then
        assertThat(divideValue).isEqualTo(2);
    }

    @Test
    @DisplayName("프로모션인데 일반 재고 사용해야 하는 경우 - 구매 개수, 증정 개수 묶음 계산 검증")
    void 프로모션_일반_재고_사용_구매_증정_묶음_계산() {
        //given
        PromotionProduct promotionProduct = new PromotionProduct("콜라", "1000", 10,
                new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30"));
        int count = 11;
        int leftBuy = 2;

        //when
        List<Integer> buyGetSet = Calculator.calculateGetAndBuy(count, leftBuy, promotionProduct);

        //then
        assertThat(buyGetSet.get(0)).isEqualTo(6);
        assertThat(buyGetSet.get(1)).isEqualTo(3);
    }

    @Test
    @DisplayName("프로모션 상품인데 1개 무료 증정 받을 수 있는 경우 계산 검증")
    void 프로모션_무료_증정_계산_검증() {
        //given
        PromotionProduct promotionProduct = new PromotionProduct("콜라", "1000", 10,
                new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30"));
        int count = 8;
        int leftBuy = 2;

        //when
        List<Integer> buyGetSet = Calculator.calculateOneMore(count, leftBuy, promotionProduct);

        //then
        assertThat(buyGetSet.get(0)).isEqualTo(6);
        assertThat(buyGetSet.get(1)).isEqualTo(2);
    }

}
