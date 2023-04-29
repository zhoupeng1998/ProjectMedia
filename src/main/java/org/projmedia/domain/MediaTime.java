package org.projmedia.domain;

public class MediaTime implements Comparable<MediaTime> {
    private final int minutes;
    private final int seconds;
    private final int milliseconds;

    public MediaTime(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = 0;
    }

    public MediaTime(int minutes, int seconds, int miliseconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = miliseconds;
    }

    public MediaTime(double milliseconds) {
        this.minutes = (int) (milliseconds / 1000 / 60);
        this.seconds = (int) (milliseconds / 1000) % 60;
        this.milliseconds = (int) (milliseconds % 1000);
    }

    public int getMinute() {
        return minutes;
    }

    public int getSecond() {
        return seconds;
    }

    public int getMillisecond() {
        return milliseconds;
    }

    @Override
    public int compareTo(MediaTime other) {
        if (this.minutes != other.minutes) {
            return Integer.compare(this.minutes, other.minutes);
        } else if (this.seconds != other.seconds) {
            return Integer.compare(this.seconds, other.seconds);
        } else {
            return Integer.compare(this.milliseconds, other.milliseconds);
        }
    }

    public String toString() {
        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
    }
}