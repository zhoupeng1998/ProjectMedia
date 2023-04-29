package org.projmedia.controller;

import org.projmedia.domain.Dimensions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class EnvController {
    private static EnvController instance = null;

    // env
    private String rawVideoFilePath;
    private String rawAudioFilePath;

    private EnvController() {
    }

    public static EnvController getInstance() {
        if (instance == null) {
            instance = new EnvController();
        }
        return instance;
    }

    public static void init() {
        if (instance == null) {
            instance = new EnvController();
        }
        instance.initEnv();
        /*
        System.out.println("EnvController initialized");
        System.out.println("Raw video file path: " + instance.getRawVideoFilePath());
        System.out.println("Raw audio file path: " + instance.getRawAudioFilePath());
        */
    }

    public void initEnv() {
        try {
            File file = new File(Dimensions.ENV_PATH);
            BufferedReader br = new BufferedReader(new FileReader(file));
            rawVideoFilePath = br.readLine();
            rawAudioFilePath = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRawVideoFilePath() {
        return rawVideoFilePath;
    }

    public String getRawAudioFilePath() {
        return rawAudioFilePath;
    }
}
