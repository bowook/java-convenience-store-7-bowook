package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromotionTest {
    @Test
    @DisplayName("프로모션 객체 생성 및 필드값 검증")
    void 프로모션_생성_및_필드_값_검증() {
        //given
        String promotionName = "탄산2+1";
        int buyQuantity = 2;
        int getQuantity = 1;
        String startDate = "2024-11-01";
        String endDate = "2024-11-30";

        //when
        Promotion promotion = new Promotion(promotionName, buyQuantity, getQuantity, startDate, endDate);

        //then
        assertThat(promotion.getName()).isEqualTo(promotionName);
        assertThat(promotion.getBuy()).isEqualTo(buyQuantity);
        assertThat(promotion.getGet()).isEqualTo(getQuantity);
    }

    @Test
    @DisplayName("프로모션 기간 내 날짜")
    void 프로모션_기간_내_날짜_검증() {
        //given
        String promotionName = "탄산2+1";
        int buyQuantity = 2;
        int getQuantity = 1;
        String startDate = "2024-11-01";
        String endDate = "2024-11-30";
        LocalDateTime testDate = LocalDateTime.of(2024, 11, 15, 0, 0);

        Promotion promotion = new Promotion(promotionName, buyQuantity, getQuantity, startDate, endDate);

        //when
        boolean isActive = promotion.isActive(testDate);

        //then
        assertThat(isActive).isTrue();
    }

    @Test
    @DisplayName("프로모션 시작 전 날짜 검증")
    void 프로모션_시작_전_날짜_검증() {
        //given
        String promotionName = "탄산2+1";
        int buyQuantity = 2;
        int getQuantity = 1;
        String startDate = "2024-11-01";
        String endDate = "2024-11-30";
        LocalDateTime testDate = LocalDateTime.of(2024, 10, 30, 0, 0);

        Promotion promotion = new Promotion(promotionName, buyQuantity, getQuantity, startDate, endDate);

        //when
        boolean isActive = promotion.isActive(testDate);

        //then
        assertThat(isActive).isFalse();
    }

    @Test
    @DisplayName("프로모션 종료 후 날짜 검증")
    void 프로모션_종료_후_날짜_검증() {
        //given
        String promotionName = "탄산2+1";
        int buyQuantity = 2;
        int getQuantity = 1;
        String startDate = "2024-11-01";
        String endDate = "2024-11-30";
        LocalDateTime testDate = LocalDateTime.of(2024, 12, 1, 0, 0);

        Promotion promotion = new Promotion(promotionName, buyQuantity, getQuantity, startDate, endDate);

        //when
        boolean isActive = promotion.isActive(testDate);

        //then
        assertThat(isActive).isFalse();
    }

    @Test
    @DisplayName("프로모션 시작 및 종료 날짜 동일")
    void isActive_프로모션_시작_및_종료_날짜_동일_검증() {
        //given
        String promotionName = "탄산2+1";
        int buyQuantity = 2;
        int getQuantity = 1;
        String startDate = "2024-11-01";
        String endDate = "2024-11-01";
        LocalDateTime testDate = LocalDateTime.of(2024, 11, 1, 0, 0);

        Promotion promotion = new Promotion(promotionName, buyQuantity, getQuantity, startDate, endDate);

        //when
        boolean isActive = promotion.isActive(testDate);

        //then
        assertThat(isActive).isTrue();
    }

}
