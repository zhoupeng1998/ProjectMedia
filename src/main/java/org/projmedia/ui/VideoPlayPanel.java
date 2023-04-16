package org.projmedia.ui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import org.projmedia.domain.Dimensions;
import org.projmedia.controller.MediaController;

import javax.swing.*;
import java.awt.*;

public class VideoPlayPanel extends JPanel {
    JFXPanel jfxPanel;
    MediaView mediaView;
    Pane root;
    Scene scene;

    public VideoPlayPanel() {
        jfxPanel = new JFXPanel();
        mediaView = new MediaView(MediaController.getInstance().getMediaPlayer());

        mediaView.setFitWidth(Dimensions.VIDEO_WIDTH);
        mediaView.setFitHeight(Dimensions.VIDEO_HEIGHT);

        root = new Pane();
        root.getChildren().add(mediaView);
        scene = new Scene(root, Dimensions.VIDEO_WIDTH, Dimensions.VIDEO_HEIGHT);
        jfxPanel.setScene(scene);

        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);
    }
}
