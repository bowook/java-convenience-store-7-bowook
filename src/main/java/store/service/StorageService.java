package store.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.domain.GeneralProduct;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.Storage;
import store.utils.FileLoader;

public class StorageService {
    public Storage initializeStorage() {
        List<String> getProductFile = loadProducts();
        List<String> getPromotionFile = loadPromotions();
        List<Promotion> promotions = generatePromotionData(getPromotionFile);
        List<GeneralProduct> onlyGeneralProducts = findGeneralProduct(getProductFile);
        List<PromotionProduct> onlyPromotionProducts = findPromotionProduct(getProductFile, promotions);
        List<GeneralProduct> insertedGeneralProducts = insertOutOfStock(onlyPromotionProducts, onlyGeneralProducts);
        return new Storage(insertedGeneralProducts, onlyPromotionProducts);
    }

    private List<String> loadPromotions() {
        try {
            return FileLoader.fileReadLine("promotions.md");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Promotion> generatePromotionData(List<String> promotionFile) {
        List<Promotion> promotions = new ArrayList<>();
        for (String line : promotionFile) {
            List<String> items = List.of(line.split(","));
            promotions.add(new Promotion(items.get(0), Integer.parseInt(items.get(1)), Integer.parseInt(items.get(2)),
                    items.get(3), items.get(4)));
        }
        return promotions;
    }

    private List<String> loadProducts() {
        try {
            return FileLoader.fileReadLine("products.md");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<GeneralProduct> insertOutOfStock(List<PromotionProduct> prItem, List<GeneralProduct> generalProducts) {
        int idx = 0;
        for (PromotionProduct product : prItem) {
            if (findEqualGeneralProductName(generalProducts, product.getName())) {
                idx += 1;
                continue;
            }
            generalProducts.add(idx, new GeneralProduct(product.getName(), product.getPrice(), "0"));
        }
        return generalProducts;
    }


    private boolean findEqualGeneralProductName(List<GeneralProduct> generalProducts, String name) {
        for (GeneralProduct generalProduct : generalProducts) {
            if (generalProduct.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private List<GeneralProduct> findGeneralProduct(List<String> getFile) {
        List<GeneralProduct> onlyGeneralProducts = new ArrayList<>();
        for (String itemDetails : getFile) {
            if (itemDetails.contains("null")) {
                List<String> item = List.of(itemDetails.split(","));
                onlyGeneralProducts.add(new GeneralProduct(item.get(0), item.get(1), item.get(2)));
            }
        }
        return onlyGeneralProducts;
    }

    private List<PromotionProduct> findPromotionProduct(List<String> getFile, List<Promotion> promotions) {
        List<PromotionProduct> onlyPromotionProducts = new ArrayList<>();
        for (String itemDetails : getFile) {
            if (itemDetails.contains("탄산2+1") || itemDetails.contains("MD추천상품") || itemDetails.contains("반짝할인")) {
                List<String> item = List.of(itemDetails.split(","));
                onlyPromotionProducts.add(new PromotionProduct(item.get(0), item.get(1), item.get(2),
                        matchingPromotion(item.get(3), promotions)));
            }
        }
        return onlyPromotionProducts;
    }

    private Promotion matchingPromotion(String promotionName, List<Promotion> promotions) {
        return promotions.stream().filter(promotion -> promotion.getName().equals(promotionName)).findFirst()
                .orElse(null);
    }


}
