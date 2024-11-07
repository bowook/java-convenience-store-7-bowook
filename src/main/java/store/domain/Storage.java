package store.domain;

import java.util.Collections;
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
            validateQuantity(item.get(0), item.get(1));
        }
    }

    public List<GeneralProduct> getGeneralProducts() {
        return Collections.unmodifiableList(generalProducts);
    }

    public List<PromotionProduct> getPromotionProducts() {
        return Collections.unmodifiableList(promotionProducts);
    }

    private void validateQuantity(String name, String quantity) {
        int count = 0;
        if (isPromotionProductInStorage(name)) {
            count += getPromotionProductQuantity(name);
        }
        count += getGeneralProductQuantity(name);
        if (count < Integer.parseInt(quantity)) {
            throw ConvenienceStoreException.from(ErrorMessage.STORAGE_OVER);
        }
    }

    private int getGeneralProductQuantity(String name) {
        for (GeneralProduct generalProduct : generalProducts) {
            if (generalProduct.getName().equals(name)) {
                return generalProduct.getQuantity();
            }
        }
        return 0;
    }

    private int getPromotionProductQuantity(String name) {
        for (PromotionProduct promotionProduct : promotionProducts) {
            if (promotionProduct.getName().equals(name)) {
                return promotionProduct.getQuantity();
            }
        }
        return 0;
    }

    private void validateItemName(String name) {
        if (!isProductInStorage(name)) {
            throw ConvenienceStoreException.from(ErrorMessage.NON_EXISTENT_PRODUCT);
        }
    }

    private boolean isPromotionProductInStorage(String productName) {
        return promotionProducts.stream().anyMatch(product -> product.getName().equals(productName));
    }

    private boolean isProductInStorage(String productName) {
        return generalProducts.stream().anyMatch(product -> product.getName().equals(productName));
    }
}
