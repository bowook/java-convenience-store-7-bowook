package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.exception.ConvenienceStoreException;
import store.exception.ErrorMessage;

public class StorageTest {
    private Storage storage;
    private GeneralProduct generalProduct;
    private PromotionProduct promotionProduct;

    @BeforeEach
    void setUp() {
        //given
        List<GeneralProduct> generalProducts = new ArrayList<>();
        this.generalProduct = new GeneralProduct("에너지바", "2000", 5);
        GeneralProduct generalProductTwo = new GeneralProduct("콜라", "1000", 10);
        generalProducts.add(generalProduct);
        generalProducts.add(generalProductTwo);

        this.promotionProduct = new PromotionProduct("콜라", "1000", 10,
                new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30"));

        this.storage = new Storage(generalProducts, List.of(promotionProduct));
    }

    @Test
    @DisplayName("재고에 있는 상품 입력")
    void 재고_있는_상품_입력() {
        //given
        List<String> purchaseProduct = List.of("에너지바-2", "콜라-2");

        //when
        List<String> result = storage.validateStorageStatus(purchaseProduct);

        //then
        assertThat(result).isEqualTo(purchaseProduct);
    }

    @Test
    @DisplayName("재고에 없는 상품 입력")
    void 재고_없는_상품_입력() {
        //given
        List<String> purchaseProduct = List.of("사이다-1");

        //when
        try {
            List<String> result = storage.validateStorageStatus(purchaseProduct);
        } catch (ConvenienceStoreException convenienceStoreException) {
            assertThat("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.").isEqualTo(convenienceStoreException.getMessage());
        }

        //then
        assertThatThrownBy(() -> storage.validateStorageStatus(purchaseProduct))
                .isInstanceOf(ConvenienceStoreException.class)
                .hasMessage(ErrorMessage.NON_EXISTENT_PRODUCT.getErrorMessage());
    }

    @Test
    @DisplayName("재고에 있는데 수량 부족")
    void 재고_있는_상품_수량_부족() {
        //given
        List<String> purchaseProduct = List.of("에너지바-20");

        //when
        try {
            List<String> result = storage.validateStorageStatus(purchaseProduct);
        } catch (ConvenienceStoreException convenienceStoreException) {
            assertThat("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.").isEqualTo(convenienceStoreException.getMessage());
        }

        //then
        assertThatThrownBy(() -> storage.validateStorageStatus(purchaseProduct))
                .isInstanceOf(ConvenienceStoreException.class)
                .hasMessage(ErrorMessage.STORAGE_OVER.getErrorMessage());
    }

    @Test
    @DisplayName("일반 상품 찾기")
    void 일반_상품_찾기() {
        //when
        GeneralProduct generalProduct = storage.findGeneralProduct("에너지바");

        //then
        assertThat(generalProduct.getName()).isEqualTo("에너지바");
    }

    @Test
    @DisplayName("일반 상품 찾기 실패")
    void 일반_상품_찾기_실패() {
        //when
        GeneralProduct generalProduct = storage.findGeneralProduct("감자칩");

        //then
        assertThat(generalProduct).isNull();
    }

    @Test
    @DisplayName("프로모션 상품 찾기")
    void 프로모션_상품_찾기() {
        //when
        PromotionProduct promotionProduct = storage.findPromotionProduct("콜라");

        //then
        assertThat(promotionProduct.getName()).isEqualTo("콜라");
    }

    @Test
    @DisplayName("일반 상품 수량 차감")
    void 일반_상품_수량_차감() {
        //given
        int quantity = generalProduct.getQuantity();
        int userPurchaseQuantity = 3;

        //when
        storage.subtractGeneralProduct(generalProduct, userPurchaseQuantity);

        //then
        assertThat(generalProduct.getQuantity()).isEqualTo(quantity - userPurchaseQuantity);
    }

    @Test
    @DisplayName("프로모션 상품 수량 차감")
    void 프로모션_상품_수량_차감() {
        //given
        int quantity = promotionProduct.getQuantity();
        int userPurchaseQuantity = 3;

        //when
        storage.subtractPromotionProduct(promotionProduct, userPurchaseQuantity);

        //then
        assertThat(promotionProduct.getQuantity()).isEqualTo(quantity - userPurchaseQuantity);
    }

}
