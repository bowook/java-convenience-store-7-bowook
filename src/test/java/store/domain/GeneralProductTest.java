package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GeneralProductTest {

    @Test
    @DisplayName("일반상품 객체 생성 및 필드값 검증")
    void 일반_상품_생성_및_필드_값_검증() {
        //given
        String productName = "감자칩";
        String productPrice = "1000";
        int productQuantity = 50;

        //when
        GeneralProduct product = new GeneralProduct(productName, productPrice, productQuantity);

        //then
        assertThat(product.getName()).isEqualTo(productName);
        assertThat(product.getPrice()).isEqualTo(productPrice);
        assertThat(product.getQuantity()).isEqualTo(productQuantity);
    }

    @Test
    @DisplayName("수량 감소 검증")
    void 수량_감소_검증() {
        //given
        String productName = "감자칩";
        String productPrice = "1000";
        int productQuantity = 50;
        GeneralProduct product = new GeneralProduct(productName, productPrice, productQuantity);

        //when
        product.subtraction(10);

        //then
        assertThat(product.getQuantity()).isEqualTo(40);
    }

    @Test
    @DisplayName("수량 감소 시 음수 입력 검증")
    void 수량_감소_음수_입력_검증() {
        //given
        String productName = "감자칩";
        String productPrice = "1000";
        int productQuantity = 50;
        GeneralProduct product = new GeneralProduct(productName, productPrice, productQuantity);

        //when
        product.subtraction(-10);

        //then
        assertThat(product.getQuantity()).isEqualTo(40);
    }

    @Test
    @DisplayName("재고 있음 검증")
    void 재고_있음_검증() {
        //given
        String productName = "감자칩";
        String productPrice = "1000";
        int productQuantity = 50;
        GeneralProduct product = new GeneralProduct(productName, productPrice, productQuantity);

        //when
        String result = product.toString();

        //then
        assertThat(result).isEqualTo("- 감자칩 1,000원 50개");
    }

    @Test
    @DisplayName("재고 없음 검증")
    void 재고_없음_검증() {
        //given
        String productName = "감자칩";
        String productPrice = "1000";
        int productQuantity = 0;
        GeneralProduct product = new GeneralProduct(productName, productPrice, productQuantity);

        //when
        String result = product.toString();

        //then
        assertThat(result).isEqualTo("- 감자칩 1,000원 재고 없음");
    }

}
