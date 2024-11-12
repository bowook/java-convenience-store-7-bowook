package store.constants;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.constant.FileMessage;

public class FileMessageTest {

    @Test
    @DisplayName("상품 파일명 반환 검증")
    void 상품_파일명_반환_검증() {
        //when
        String productFileName = FileMessage.PRODUCTS_FILE_NAME.getFileMessage();

        //then
        assertThat(productFileName).isEqualTo("products.md");
    }

    @Test
    @DisplayName("프로모션 파일명 반환 검증")
    void 프로모션_파일명_반환_검증() {
        //when
        String promotionFileName = FileMessage.PROMOTION_FILE_NAME.getFileMessage();

        //then
        assertThat(promotionFileName).isEqualTo("promotions.md");
    }

    @Test
    @DisplayName("널 문자열 반환 검증")
    void 널_문자열_반환_검증() {
        //when
        String nullString = FileMessage.NULL.getFileMessage();

        //then
        assertThat(nullString).isEqualTo("null");
    }

    @Test
    @DisplayName("탄산 프로모션 문자열 반환 검증")
    void 탄산_프로모션_문자열_반환_검증() {
        //when
        String softDrink = FileMessage.SOFT_DRINK.getFileMessage();

        //then
        assertThat(softDrink).isEqualTo("탄산2+1");
    }

    @Test
    @DisplayName("MD추천상품 프로모션 문자열 반환 검증")
    void MD_추천상품_프로모션_문자열_반환_검증() {
        //when
        String mdRecommendProduct = FileMessage.MD_RECOMMEND_PRODUCT.getFileMessage();

        //then
        assertThat(mdRecommendProduct).isEqualTo("MD추천상품");
    }

    @Test
    @DisplayName("반짝할인 프로모션 문자열 반환 검증")
    void 반짝할인_프로모션_문자열_반환_검증() {
        //when
        String flashDiscount = FileMessage.FLASH_DISCOUNT.getFileMessage();

        //then
        assertThat(flashDiscount).isEqualTo("반짝할인");
    }

    @Test
    @DisplayName("파일 시작 문자열 반환 검증")
    void 파일_시작_문자열_반환_검증() {
        //when
        String fileStartWord = FileMessage.FILE_START_WORD.getFileMessage();

        //then
        assertThat(fileStartWord).isEqualTo("name");
    }

}
