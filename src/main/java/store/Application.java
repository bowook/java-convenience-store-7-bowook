package store;

import store.config.AppConfig;
import store.controller.ConvenienceStoreController;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        ConvenienceStoreController convenienceStoreController = appConfig.convenienceStoreController();
        convenienceStoreController.operate();
    }

}
