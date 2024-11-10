package store.config;

import store.constant.FileMessage;
import store.controller.ConvenienceStoreController;
import store.service.StorageService;
import store.view.input.InputView;
import store.view.output.OutputView;

public class AppConfig {
    public ConvenienceStoreController convenienceStoreController() {
        return new ConvenienceStoreController(inputView(), outputView(), storageService());
    }

    private InputView inputView() {
        return new InputView();
    }

    private OutputView outputView() {
        return new OutputView();
    }

    private StorageService storageService() {
        return new StorageService(
                FileMessage.PRODUCTS_FILE_NAME.getFileMessage(),
                FileMessage.PROMOTION_FILE_NAME.getFileMessage()
        );
    }

}
