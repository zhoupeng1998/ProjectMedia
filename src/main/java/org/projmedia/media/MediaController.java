package org.projmedia.media;

import org.projmedia.dimensions.Dimensions;
import org.projmedia.dimensions.MediaTime;

import javax.media.Manager;
import javax.media.Player;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MediaController {
    private static MediaController instance = null;

    private int indexSize = 0;
    private List<String> indexTextList;
    private List<MediaTime> indexTimeList;

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
    }

    public void setToTime(MediaTime videoTime) {
        System.out.println("Set to time: " + videoTime.getMinute() + ":" + videoTime.getSecond());
    }

    public void play() {
        System.out.println("Play");
    }

    public void pause() {
        System.out.println("Pause");
    }

    public void stop() {
        System.out.println("Stop");
    }

    public void initIndexList() {
        // read video index file using BufferedReader
        try {
            File file = new File(Dimensions.VIDEO_INDEX_PATH);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] parts = st.split("[.:]");
                //System.out.println(st);
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
        // TODO
    }

    public List<String> getIndexTextList() {
        return indexTextList;
    }

}
