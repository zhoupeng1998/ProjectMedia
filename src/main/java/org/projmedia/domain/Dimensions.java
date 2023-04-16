package org.projmedia.domain;

public class Dimensions {
    /*
    // Dimensions based on frame size
    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;

    public static final int LEFT_PANEL_WIDTH = 300;
    public static final int RIGHT_PANEL_WIDTH = FRAME_WIDTH - LEFT_PANEL_WIDTH;

    public static final int TOP_RIGHT_PANEL_HEIGHT = 400;
    public static final int BOTTOM_RIGHT_PANEL_HEIGHT = FRAME_HEIGHT - TOP_RIGHT_PANEL_HEIGHT;
     */

    public static final int SCALE_FACTOR = 4;

    public static final int VIDEO_WIDTH = 480 * SCALE_FACTOR, TOP_RIGHT_PANEL_WIDTH = VIDEO_WIDTH, RIGHT_PANEL_WIDTH = VIDEO_WIDTH;
    public static final int VIDEO_HEIGHT = 270 * SCALE_FACTOR, TOP_RIGHT_PANEL_HEIGHT = VIDEO_HEIGHT;

    public static final int LEFT_PANEL_WIDTH = 300;
    public static final int BOTTOM_RIGHT_PANEL_HEIGHT = 200;

    public static final int FRAME_WIDTH = VIDEO_WIDTH + LEFT_PANEL_WIDTH;
    public static final int FRAME_HEIGHT = VIDEO_HEIGHT + BOTTOM_RIGHT_PANEL_HEIGHT;

    public static final String ENV_PATH = "files/env.txt";

    public static final String VIDEO_INDEX_PATH = "files/indexfile.txt";
    public static final String VIDEO_FILE_PATH = "files/output.mp4";
}
