package store.domain;

import java.util.Collections;
import java.util.List;
import store.constant.CommonValue;
import store.constant.SignMessage;
import store.exception.ConvenienceStoreException;
import store.exception.ErrorMessage;

public class Storage {
    private final List<GeneralProduct> generalProducts;
    private final List<PromotionProduct> promotionProducts;

    public Storage(final List<GeneralProduct> generalProducts, final List<PromotionProduct> promotionProducts) {
        this.generalProducts = generalProducts;
        this.promotionProducts = promotionProducts;
    }

    public List<String> validateStorageStatus(List<String> purchaseProduct) {
        for (String purchaseItem : purchaseProduct) {
            List<String> item = List.of(purchaseItem.split(SignMessage.HYPHEN.getSign()));
            validateItemName(item.get(CommonValue.ZERO.getValue()));
            validateQuantity(item.get(CommonValue.ZERO.getValue()), item.get(CommonValue.ONE.getValue()));
        }
        return purchaseProduct;
    }

    public List<GeneralProduct> getGeneralProducts() {
        return Collections.unmodifiableList(generalProducts);
    }

    public List<PromotionProduct> getPromotionProducts() {
        return Collections.unmodifiableList(promotionProducts);
    }

    public GeneralProduct findGeneralProduct(String productName) {
        return generalProducts.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    public PromotionProduct findPromotionProduct(String productName) {
        return promotionProducts.stream()
                .filter(product -> product.getName().equals(productName))
                .findFirst()
                .orElse(null);
    }

    public void subtractPromotionProduct(PromotionProduct promotionProduct, int itemQuantity) {
        promotionProduct.subtraction(itemQuantity);
    }

    public void subtractGeneralProduct(GeneralProduct generalProduct, int itemQuantity) {
        generalProduct.subtraction(itemQuantity);
    }

    private void validateQuantity(String name, String quantity) {
        if (getProductQuantity(name) < Integer.parseInt(quantity)) {
            throw ConvenienceStoreException.from(ErrorMessage.STORAGE_OVER);
        }
    }

    private int getProductQuantity(String name) {
        PromotionProduct promotionProduct = findPromotionProduct(name);
        int count = CommonValue.ZERO.getValue();
        if (promotionProduct != null) {
            count += promotionProduct.getQuantity();
        }
        return count + findGeneralProduct(name).getQuantity();
    }

    private void validateItemName(String name) {
        if (!isProductInStorage(name)) {
            throw ConvenienceStoreException.from(ErrorMessage.NON_EXISTENT_PRODUCT);
        }
    }

    private boolean isProductInStorage(String productName) {
        return generalProducts.stream().anyMatch(product -> product.getName().equals(productName));
    }

}
