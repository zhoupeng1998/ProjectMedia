package org.projmedia.domain;

public class MediaTime {
    private final int minutes;
    private final int seconds;
    private final int miliseconds;

    public MediaTime(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.miliseconds = 0;
    }

    public MediaTime(int minutes, int seconds, int miliseconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.miliseconds = miliseconds;
    }

    public int getMinute() {
        return minutes;
    }

    public int getSecond() {
        return seconds;
    }

    public int getMilisecond() {
        return miliseconds;
    }
}