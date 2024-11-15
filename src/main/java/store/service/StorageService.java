package store.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.constant.FileMessage;
import store.constant.SignMessage;
import store.domain.GeneralProduct;
import store.domain.Promotion;
import store.domain.PromotionProduct;
import store.domain.Storage;
import store.utils.FileLoader;

public class StorageService {
    private static final String FILE_NOT_FOUND_ERROR = "[ERROR] 파일을 찾을 수 없습니다.";
    private final List<String> productFile;
    private final List<String> promotionFile;

    public StorageService(final String productFileName, final String promotionFileName) {
        this.productFile = loadFile(productFileName);
        this.promotionFile = loadFile(promotionFileName);
    }

    public Storage initializeStorage() {
        List<Promotion> promotions = generatePromotionData(promotionFile);
        List<GeneralProduct> onlyGeneralProducts = findGeneralProduct(productFile);
        List<PromotionProduct> onlyPromotionProducts = findPromotionProduct(productFile, promotions);
        List<GeneralProduct> insertedGeneralProducts = insertOutOfStock(onlyPromotionProducts, onlyGeneralProducts);
        return new Storage(insertedGeneralProducts, onlyPromotionProducts);
    }

    private List<String> loadFile(String fileName) {
        try {
            return FileLoader.fileReadLine(fileName);
        } catch (IOException e) {
            throw new IllegalArgumentException(FILE_NOT_FOUND_ERROR);
        }
    }

    private List<Promotion> generatePromotionData(List<String> promotionFile) {
        List<Promotion> promotions = new ArrayList<>();
        for (String line : promotionFile) {
            List<String> items = List.of(line.split(SignMessage.COMMA.getSign()));
            promotions.add(new Promotion(items.get(0), Integer.parseInt(items.get(1)), Integer.parseInt(items.get(2)),
                    items.get(3), items.get(4)));
        }
        return promotions;
    }

    private List<GeneralProduct> insertOutOfStock(List<PromotionProduct> prItem, List<GeneralProduct> generalProducts) {
        int idx = 0;
        for (PromotionProduct product : prItem) {
            if (findEqualGeneralProductName(generalProducts, product.getName())) {
                idx += 1;
                continue;
            }
            generalProducts.add(idx, new GeneralProduct(product.getName(), product.getPrice(), 0));
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
            if (itemDetails.contains(FileMessage.NULL.getFileMessage())) {
                List<String> item = List.of(itemDetails.split(SignMessage.COMMA.getSign()));
                onlyGeneralProducts.add(new GeneralProduct(item.get(0), item.get(1), Integer.parseInt(item.get(2))));
            }
        }
        return onlyGeneralProducts;
    }

    private List<PromotionProduct> findPromotionProduct(List<String> getFile, List<Promotion> promotions) {
        List<PromotionProduct> onlyPromotionProducts = new ArrayList<>();
        for (String itemDetails : getFile) {
            if (isPromotionProduct(itemDetails)) {
                addPromotionProduct(onlyPromotionProducts, itemDetails, promotions);
            }
        }
        return onlyPromotionProducts;
    }

    private boolean isPromotionProduct(String itemDetails) {
        return itemDetails.contains(FileMessage.SOFT_DRINK.getFileMessage())
                || itemDetails.contains(FileMessage.MD_RECOMMEND_PRODUCT.getFileMessage())
                || itemDetails.contains(FileMessage.FLASH_DISCOUNT.getFileMessage());
    }

    private void addPromotionProduct(List<PromotionProduct> products, String itemDetails, List<Promotion> promotions) {
        List<String> item = List.of(itemDetails.split(SignMessage.COMMA.getSign()));
        String productName = item.get(0);
        String productCategory = item.get(1);
        int price = Integer.parseInt(item.get(2));
        Promotion promotion = matchingPromotion(item.get(3), promotions);

        products.add(new PromotionProduct(productName, productCategory, price, promotion));
    }

    private Promotion matchingPromotion(String promotionName, List<Promotion> promotions) {
        return promotions.stream().filter(promotion -> promotion.getName().equals(promotionName)).findFirst()
                .orElse(null);
    }

}
