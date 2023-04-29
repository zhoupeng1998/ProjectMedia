package org.projmedia;

import javafx.application.Application;
import javafx.stage.Stage;
import org.projmedia.controller.EnvController;
import org.projmedia.controller.MediaController;
import org.projmedia.ui.AppFrame;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Starting Media Player");
        EnvController.init();
        MediaController.init();
        AppFrame appFrame = new AppFrame();
    }
}