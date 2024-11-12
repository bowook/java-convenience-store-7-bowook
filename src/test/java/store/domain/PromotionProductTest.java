package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PromotionProductTest {
    @Test
    @DisplayName("프로모션 상품 객체 생성 및 필드값 검증")
    void 프로모션_상품_생성_및_필드_값_검증() {
        //given
        String productName = "감자칩";
        String productPrice = "1000";
        int productQuantity = 50;
        Promotion promotion = new Promotion("반짝할인", 1, 1, "2024-11-01", "2024-11-30");

        //when
        PromotionProduct product = new PromotionProduct(productName, productPrice, productQuantity, promotion);

        //then
        assertThat(product.getName()).isEqualTo(productName);
        assertThat(product.getPrice()).isEqualTo(productPrice);
        assertThat(product.getQuantity()).isEqualTo(productQuantity);
        assertThat(product.getPromotion()).isEqualTo(promotion);
    }

    @Test
    @DisplayName("프로모션 합계 메소드 검증")
    void 프로모션_합계_검증() {
        //given
        String productName = "감자칩";
        String productPrice = "1000";
        int productQuantity = 50;
        Promotion promotion = new Promotion("반짝할인", 1, 1, "2024-11-01", "2024-11-30");

        PromotionProduct product = new PromotionProduct(productName, productPrice, productQuantity, promotion);

        //when
        int promotionSum = product.getPromotionSum();

        //then
        assertThat(promotionSum).isEqualTo(2);
    }

    @Test
    @DisplayName("프로모션 구매 개수 검증")
    void 프로모션_구매_개수_검증() {
        //given
        String productName = "감자칩";
        String productPrice = "1000";
        int productQuantity = 50;
        Promotion promotion = new Promotion("반짝할인", 1, 1, "2024-11-01", "2024-11-30");

        PromotionProduct product = new PromotionProduct(productName, productPrice, productQuantity, promotion);

        //when
        int buyQuantity = product.getPromotionGetBuy();

        //then
        assertThat(buyQuantity).isEqualTo(1);
    }

    @Test
    @DisplayName("프로모션 증정 개수 검증")
    void 프로모션_증정_개수_검증() {
        //given
        String productName = "감자칩";
        String productPrice = "1000";
        int productQuantity = 50;
        Promotion promotion = new Promotion("반짝할인", 1, 1, "2024-11-01", "2024-11-30");

        PromotionProduct product = new PromotionProduct(productName, productPrice, productQuantity, promotion);

        //when
        int getQuantity = product.getPromotionGetGet();

        //then
        assertThat(getQuantity).isEqualTo(1);
    }

    @Test
    @DisplayName("상품 개수 차감 검증")
    void subtraction_검증() {
        //given
        String productName = "감자칩";
        String productPrice = "1000";
        int productQuantity = 50;
        Promotion promotion = new Promotion("반짝할인", 1, 1, "2024-11-01", "2024-11-30");

        PromotionProduct product = new PromotionProduct(productName, productPrice, productQuantity, promotion);

        //when
        product.subtraction(10);

        //then
        assertThat(product.getQuantity()).isEqualTo(40);
    }

    @Test
    @DisplayName("재고 없음 검증")
    void 재고_없음_검증() {
        //given
        String productName = "감자칩";
        String productPrice = "1000";
        int productQuantity = 0;
        Promotion promotion = new Promotion("반짝할인", 1, 1, "2024-11-01", "2024-11-30");

        PromotionProduct product = new PromotionProduct(productName, productPrice, productQuantity, promotion);

        //when
        String result = product.toString();

        //then
        assertThat(result).isEqualTo("- 감자칩 1,000원 재고 없음 반짝할인");
    }

    @Test
    @DisplayName("재고 있음 검증")
    void 재고_있음_검증() {
        //given
        String productName = "감자칩";
        String productPrice = "1000";
        int productQuantity = 50;
        Promotion promotion = new Promotion("반짝할인", 1, 1, "2024-11-01", "2024-11-30");

        PromotionProduct product = new PromotionProduct(productName, productPrice, productQuantity, promotion);

        //when
        String result = product.toString();

        //then
        assertThat(result).isEqualTo("- 감자칩 1,000원 50개 반짝할인");
    }

}
