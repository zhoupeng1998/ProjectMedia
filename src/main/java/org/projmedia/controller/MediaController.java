package org.projmedia.controller;

import com.sun.media.jfxmedia.events.PlayerTimeListener;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.projmedia.domain.Dimensions;
import org.projmedia.domain.MediaTime;

import javax.swing.*;
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
    private List<Integer> indexLevelList;
    private List<MediaTime> indexTimeList;

    // video
    private JFXPanel jfxPanel;
    private Media media;
    private MediaPlayer mediaPlayer;

    // select list
    private JList<String> selectList;
    private boolean userSelecting = false;
    private int userSelectIndex = 0;

    private MediaController() {
        indexTextList = new ArrayList<>();
        indexLevelList = new ArrayList<>();
        indexTimeList = new ArrayList<>();
    }

    public static void init() {
        if (instance == null) {
            instance = new MediaController();
        }
        instance.initIndexList();
        instance.initVideoPlayer();
        instance.selectList = new JList<>(instance.getIndexTextList().toArray(new String[0]));
    }

    public static MediaController getInstance() {
        if (instance == null) {
            instance = new MediaController();
        }
        return instance;
    }

    public void setToTime(int index) {
        //System.out.println("Set to time: " + indexTimeList.get(index).getMinute() + ":" + indexTimeList.get(index).getSecond() + ":" + indexTimeList.get(index).getMillisecond() + " at index " + index);
        Duration time = Duration.millis(indexTimeList.get(index).getMinute() * 60 * 1000 + indexTimeList.get(index).getSecond() * 1000 + indexTimeList.get(index).getMillisecond());
        mediaPlayer.seek(time);
    }

    public void setToTime(MediaTime videoTime) {
        //System.out.println("Set to time: " + videoTime.getMinute() + ":" + videoTime.getSecond());
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
        mediaPlayer.pause();
        int index = findCurrentSelectListIndex();
        while (index > 0 && indexLevelList.get(index) > 2) {
            index--;
        }
        setToTime(index);
        selectList.setSelectedIndex(index);
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
                indexLevelList.add(indexLevel);
                indexSize++;
                indexTimeList.add(new MediaTime(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initVideoPlayer() {
        File mediaFile = new File(Dimensions.VIDEO_FILE_PATH);
        media = new Media(mediaFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnReady(() -> {
            mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                if (selectList.getValueIsAdjusting()) {
                    return;
                }
                int index = findCurrentSelectListIndex();
                if (userSelecting) {
                    userSelecting = false;
                    index = userSelectIndex;
                    setToTime(index);
                }
                selectList.setSelectedIndex(index);
            });
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.pause();
        });
    }

    public int findCurrentSelectListIndex() {
        MediaTime currentTime = new MediaTime(mediaPlayer.getCurrentTime().toMillis());
        for (int i = 0; i < indexSize; i++) {
            if (currentTime.compareTo(indexTimeList.get(i)) < 0) {
                return i - 1;
            }
        }
        return indexSize - 1;
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

    public JList<String> getSelectList() {
        return selectList;
    }

    public void setUserSelect(int index) {
        this.userSelecting = true;
        this.userSelectIndex = index;
    }
}
