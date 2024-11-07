package store.controller;

import java.util.List;
import store.domain.Storage;
import store.exception.ConvenienceStoreException;
import store.service.StorageService;
import store.view.input.InputView;
import store.view.output.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final StorageService storageService;

    public ConvenienceStoreController(InputView inputView, OutputView outputView, StorageService storageService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storageService = storageService;
    }

    public void operate() {
        outputView.writeWelcomeMessage();

        Storage storage = storageService.initializeStorage();
        outputView.writeInitStorageStatus(storage);

        userPurchaseProduct(storage);
    }

    private void userPurchaseProduct(Storage storage) {
        while (true) {
            try {
                List<String> purchaseProduct = inputView.readItems();
                storage.validateStorageStatus(purchaseProduct);
                break;
            } catch (ConvenienceStoreException convenienceStoreException) {
                outputView.displayErrorMessage(convenienceStoreException);
            }
        }
    }


}
