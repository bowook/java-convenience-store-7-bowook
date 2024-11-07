package store.domain;

import java.util.List;
import store.exception.ConvenienceStoreException;
import store.exception.ErrorMessage;

public class Storage {
    private final List<GeneralProduct> generalProducts;
    private final List<PromotionProduct> promotionProducts;

    public Storage(final List<GeneralProduct> generalProducts, final List<PromotionProduct> promotionProducts) {
        this.generalProducts = generalProducts;
        this.promotionProducts = promotionProducts;
    }

    public void validateStorageStatus(List<String> purchaseProduct) {
        for (String purchaseItem : purchaseProduct) {
            List<String> item = List.of(purchaseItem.split("-"));
            validateItemName(item.get(0));
        }
    }

    private void validateItemName(String name) {
        if (!isProductInStorage(name)) {
            throw ConvenienceStoreException.from(ErrorMessage.NON_EXISTENT_PRODUCT);
        }
    }

    private boolean isProductInStorage(String productName) {
        return generalProducts.stream().anyMatch(product -> product.getName().equals(productName));
    }


    public List<GeneralProduct> getGeneralProducts() {
        return generalProducts;
    }

    public List<PromotionProduct> getPromotionProducts() {
        return promotionProducts;
    }
}
