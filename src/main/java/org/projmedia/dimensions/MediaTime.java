package org.projmedia.dimensions;

// TODO: maybe add miliseconds?
public class MediaTime {
    private final int minutes;
    private final int seconds;

    public MediaTime(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public int getMinute() {
        return minutes;
    }

    public int getSecond() {
        return seconds;
    }

}