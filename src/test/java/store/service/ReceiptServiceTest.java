package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.GeneralProduct;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.Receipt;
import store.domain.ReceiptItem;
import store.domain.Storage;

public class ReceiptServiceTest {
    @Test
    @DisplayName("1개 무료 증정 검증")
    void 무료_증정_검증() {
        //given
        Receipt receipt = new Receipt();
        int count = 2;
        int leftBuy = 2;
        String productName = "사이다";
        String productPrice = "1000";
        int productQuantity = 10;
        Promotion promotion = new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30");
        PromotionProduct product = new PromotionProduct(productName, productPrice, productQuantity, promotion);

        //when
        boolean result = ReceiptService.oneMoreReceipt(receipt, count, leftBuy, product);
        List<ReceiptItem> items = receipt.getReceiptItems();

        //then
        assertThat(items.get(0).getItemName()).isEqualTo("사이다");
        assertThat(items.get(0).getBuyQuantity()).isEqualTo(2);
        assertThat(items.get(0).getGetQuantity()).isEqualTo(1);
        assertThat(items.get(0).getPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("1개 무료 증정 안함 검증")
    void 무료_증정X_검증() {
        //given
        Receipt receipt = new Receipt();
        int count = 2;
        int leftBuy = 2;
        String productName = "사이다";
        String productPrice = "1000";
        int productQuantity = 10;
        Promotion promotion = new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30");
        PromotionProduct product = new PromotionProduct(productName, productPrice, productQuantity, promotion);

        //when
        boolean result = ReceiptService.noOneMoreReceipt(receipt, count, leftBuy, product);
        List<ReceiptItem> items = receipt.getReceiptItems();

        //then
        assertThat(items.get(0).getItemName()).isEqualTo("사이다");
        assertThat(items.get(0).getBuyQuantity()).isEqualTo(2);
        assertThat(items.get(0).getGetQuantity()).isEqualTo(0);
        assertThat(items.get(0).getPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("정가로 추가 구매 검증")
    void 정가_추가_구매_검증() {
        //given
        Receipt receipt = new Receipt();
        String productName = "사이다";
        String productPrice = "1000";
        int productQuantity = 7;
        Promotion promotion = new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30");
        PromotionProduct product = new PromotionProduct(productName, productPrice, productQuantity, promotion);
        int quantity = 4;
        int count = 7;
        int leftBuy = 1;

        //when
        boolean result = ReceiptService.noDiscountPurchase(receipt, quantity, count, leftBuy, product);
        List<ReceiptItem> items = receipt.getReceiptItems();

        //then
        assertThat(items.get(0).getItemName()).isEqualTo("사이다");
        assertThat(items.get(0).getBuyQuantity()).isEqualTo(8);
        assertThat(items.get(0).getGetQuantity()).isEqualTo(2);
        assertThat(items.get(0).getPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("프로모션 아닌 상품 구매 검증")
    void 프로모션_X_상품_구매_검증() {
        //given
        Receipt receipt = new Receipt();
        List<GeneralProduct> generalProducts = new ArrayList<>();
        GeneralProduct generalProduct = new GeneralProduct("에너지바", "2000", 5);
        GeneralProduct generalProductTwo = new GeneralProduct("콜라", "1000", 10);
        generalProducts.add(generalProduct);
        generalProducts.add(generalProductTwo);

        PromotionProduct promotionProduct = new PromotionProduct("콜라", "1000", 10,
                new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30"));

        Storage storage = new Storage(generalProducts, List.of(promotionProduct));
        String itemName = "에너지바";
        int purchaseCount = 3;

        //when
        ReceiptService.purchaseGeneralProduct(storage, itemName, purchaseCount, receipt);
        List<ReceiptItem> items = receipt.getReceiptItems();

        //then
        assertThat(items.get(0).getItemName()).isEqualTo("에너지바");
        assertThat(items.get(0).getBuyQuantity()).isEqualTo(purchaseCount);
        assertThat(items.get(0).getGetQuantity()).isEqualTo(0);
        assertThat(items.get(0).getPrice()).isEqualTo(2000);
    }

    @Test
    @DisplayName("프로모션 상품인데 사용자가 개수 딱 맞춰서 가져온 경우 검증")
    void 프로모션_상품_개수_정확_검증() {
        //given
        int purchaseCount = 3;
        Receipt receipt = new Receipt();
        PromotionProduct promotionProduct = new PromotionProduct("콜라", "1000", 10,
                new Promotion("탄산2+1", 2, 1, "2024-11-01", "2024-11-30"));

        //when
        ReceiptService.useStock(promotionProduct, purchaseCount, receipt);
        List<ReceiptItem> items = receipt.getReceiptItems();

        //then
        assertThat(items.get(0).getItemName()).isEqualTo("콜라");
        assertThat(items.get(0).getBuyQuantity()).isEqualTo(2);
        assertThat(items.get(0).getGetQuantity()).isEqualTo(1);
        assertThat(items.get(0).getPrice()).isEqualTo(1000);
    }
}
