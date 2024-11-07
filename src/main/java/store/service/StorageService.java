package store.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.domain.GeneralProduct;
import store.domain.PromotionProduct;
import store.domain.Storage;
import store.utils.FileLoader;

public class StorageService {
    public Storage initializeStorage() {
        List<String> getFile = readFile();
        List<GeneralProduct> onlyGeneralProducts = findGeneralProduct(getFile);
        List<PromotionProduct> onlyPromotionProducts = findPromotionProduct(getFile);
        return new Storage(onlyGeneralProducts, onlyPromotionProducts);
    }

    private List<String> readFile() {
        try {
            return FileLoader.fileReadLine("products.md");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private List<PromotionProduct> findPromotionProduct(List<String> getFile) {
        List<PromotionProduct> onlyPromotionProducts = new ArrayList<>();
        for (String itemDetails : getFile) {
            if (itemDetails.contains("탄산2+1") || itemDetails.contains("MD추천상품") || itemDetails.contains("반짝할인")) {
                List<String> item = List.of(itemDetails.split(","));
                onlyPromotionProducts.add(new PromotionProduct(item.get(0), item.get(1), item.get(2), item.get(3)));
            }
        }
        return onlyPromotionProducts;
    }


}
