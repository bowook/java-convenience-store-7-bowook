package store.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;
import store.domain.PromotionProduct;

public class CompareTest {
    @Test
    @DisplayName("프로모션에 따라 1개 무료 증정 검증")
    void 프로모션_1개_무료_증정_검증() {
        //given
        PromotionProduct promotionProduct = new PromotionProduct("콜라", "1000", 10,
                new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30"));
        int remainingPurchase = 2;
        int remainPromotionStock = 5;

        //when
        boolean result = Compare.checkFreeOneMore(remainingPurchase, remainPromotionStock, promotionProduct);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("프로모션에 따라 1개 무료 증정이 안 되는 경우 검증")
    void 프로모션_1개_무료_증정_불가_검증() {
        //given
        PromotionProduct promotionProduct = new PromotionProduct("콜라", "1000", 10,
                new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30"));
        int remainingPurchase = 1;
        int remainPromotionStock = 5;

        //when
        boolean result = Compare.checkFreeOneMore(remainingPurchase, remainPromotionStock, promotionProduct);

        //then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("재고가 부족한 경우 검증")
    void 재고_부족_검증() {
        //given
        int remainStock = -1;

        //when
        boolean result = Compare.checkSupplementStock(remainStock);

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("재고가 충분한 경우 검증")
    void 재고_충분_검증() {
        //given
        int remainStock = 5;

        //when
        boolean result = Compare.checkSupplementStock(remainStock);

        //then
        assertThat(result).isFalse();
    }

}
