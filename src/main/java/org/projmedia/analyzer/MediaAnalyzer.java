package org.projmedia.analyzer;

import org.projmedia.controller.EnvController;
import org.projmedia.domain.Dimensions;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MediaAnalyzer {
    private static final int fps = 30;
    private static final int numFrames = 8682;

    private byte[] buffer1;
    private byte[] buffer2;
    private boolean buffer1IsCurrent;

    public MediaAnalyzer() {
        buffer1 = new byte[Dimensions.VIDEO_WIDTH * Dimensions.VIDEO_HEIGHT * 3];
        buffer2 = new byte[Dimensions.VIDEO_WIDTH * Dimensions.VIDEO_HEIGHT * 3];
    }

    private void analyzeFrame() {
        byte[] curBuffer = buffer1IsCurrent ? buffer1 : buffer2;
        int count = 0;
        for (int i = 0; i < buffer1.length; i += 3) {
            int r = curBuffer[i] & 0xFF;
            int g = curBuffer[i + 1] & 0xFF;
            int b = curBuffer[i + 2] & 0xFF;
            count++;
        }
        System.out.println(count);
        // Do your things here
    }

    public void analyze() {
        File rawVideoFile = new File(EnvController.getInstance().getRawVideoFilePath());
        try {
            RandomAccessFile rafVideoFile = new RandomAccessFile(rawVideoFile, "r");
            FileChannel videoFileChannel = rafVideoFile.getChannel();
            ByteBuffer rawVideoBuffer = ByteBuffer.allocate(Dimensions.VIDEO_WIDTH * Dimensions.VIDEO_HEIGHT * 3);

            videoFileChannel.read(rawVideoBuffer);
            rawVideoBuffer.rewind();
            rawVideoBuffer.get(buffer2);

            for (int i = 0; i < numFrames; i++) {
                rawVideoBuffer.clear();
                videoFileChannel.read(rawVideoBuffer);
                rawVideoBuffer.rewind();
                if (buffer1IsCurrent) {
                    rawVideoBuffer.get(buffer1);
                } else {
                    rawVideoBuffer.get(buffer2);
                }
                buffer1IsCurrent = !buffer1IsCurrent;
                analyzeFrame();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
