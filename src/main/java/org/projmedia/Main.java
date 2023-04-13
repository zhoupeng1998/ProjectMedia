package org.projmedia;

import javafx.application.Application;
import javafx.stage.Stage;
import org.projmedia.analyzer.MediaAnalyzer;
import org.projmedia.media.MediaController;
import org.projmedia.ui.AppFrame;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MediaAnalyzer mediaAnalyzer = new MediaAnalyzer();
        mediaAnalyzer.analyze();

        MediaController.init();
        AppFrame appFrame = new AppFrame();
    }
}