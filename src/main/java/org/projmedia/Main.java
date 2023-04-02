package org.projmedia;

import org.projmedia.media.MediaController;
import org.projmedia.ui.AppFrame;

public class Main {
    public static void main(String[] args) {
        MediaController.init();
        AppFrame appFrame = new AppFrame();
    }
}