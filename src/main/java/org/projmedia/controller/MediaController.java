package org.projmedia.controller;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.projmedia.domain.Dimensions;
import org.projmedia.domain.MediaTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaController {
    private static MediaController instance = null;

    // index
    private int indexSize = 0;
    private List<String> indexTextList;
    private List<MediaTime> indexTimeList;

    // video
    JFXPanel jfxPanel;
    Media media;
    MediaPlayer mediaPlayer;

    private MediaController() {
        indexTextList = new ArrayList<>();
        indexTimeList = new ArrayList<>();
    }

    public static void init() {
        if (instance == null) {
            instance = new MediaController();
        }
        instance.initIndexList();
        instance.initVideoPlayer();
    }

    public static MediaController getInstance() {
        if (instance == null) {
            instance = new MediaController();
        }
        return instance;
    }

    public void setToTime(int index) {
        System.out.println("Set to time: " + indexTimeList.get(index).getMinute() + ":" + indexTimeList.get(index).getSecond() + " at index " + index);
        Duration time = Duration.seconds(indexTimeList.get(index).getMinute() * 60 + indexTimeList.get(index).getSecond());
        mediaPlayer.seek(time);
    }

    public void setToTime(MediaTime videoTime) {
        System.out.println("Set to time: " + videoTime.getMinute() + ":" + videoTime.getSecond());
        Duration time = Duration.seconds(videoTime.getMinute() * 60 + videoTime.getSecond());
        mediaPlayer.seek(time);
    }

    public void play() {
        mediaPlayer.play();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void initIndexList() {
        try {
            File file = new File(Dimensions.VIDEO_INDEX_PATH);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] parts = st.split("[.:]");
                int indexLevel = Integer.parseInt(parts[0]);
                StringBuilder indexText = new StringBuilder();
                for (int i = 1; i < indexLevel; i++) {
                    indexText.append("    ");
                }
                indexText.append(parts[1]);
                indexTextList.add(indexText.toString());
                indexSize++;
                indexTimeList.add(new MediaTime(Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initVideoPlayer() {
        File mediaFile = new File(Dimensions.VIDEO_FILE_PATH);
        media = new Media(mediaFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    public List<String> getIndexTextList() {
        return indexTextList;
    }

    public Media getMedia() {
        return media;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
