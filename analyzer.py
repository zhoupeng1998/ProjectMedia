import os
import cv2
import librosa
import numpy as np
import matplotlib.pyplot as plt

def analyze():
    video_path = "files/InputVideo.rgb"
    audio_path = "files/InputAudio.wav"
    mp4_path = "files/output.mp4"

    size = (480, 270)
    fps = 30

    # analyze video

    with open("files/env.txt", "r") as f:
        video_path = os.path.normpath(f.readline().strip()).replace("\\", "/")
        audio_path = os.path.normpath(f.readline().strip()).replace("\\", "/")
        f.close()

    video_cap = cv2.VideoCapture(mp4_path)
    video_cap.set(cv2.CAP_PROP_FRAME_WIDTH, size[0])
    video_cap.set(cv2.CAP_PROP_FRAME_HEIGHT, size[1])
    video_cap.set(cv2.CAP_PROP_FPS, fps)

    abs_diffs = []
    histograms = []

    ret, prev_frame = video_cap.read()

    while video_cap.isOpened():
        ret, frame = video_cap.read()
        if not ret:
            break

        hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
        hist, _ = np.histogram(hsv[:,:,0], bins=180, range=[0, 180])
        pixel_diff = cv2.absdiff(frame, prev_frame)

        histograms.append(hist)
        abs_diffs.append(pixel_diff.mean())

        prev_frame = frame

    histograms = np.array(histograms)
    hist_diffs = np.abs(np.diff(histograms, axis=0))

    fig, axs = plt.subplots(3)

    # analyze audio

    samples, sample_rate = librosa.load(audio_path, sr=None, mono=True)
    print("Samples: ", len(samples))
    print("Sample rate: ", sample_rate)
    print("Duration: ", librosa.get_duration(y=samples, sr=sample_rate))

    time_array = np.arange(0, len(samples)) / sample_rate
    print("Time array: ", time_array.shape)

    axs[0].plot(abs_diffs)
    axs[0].set_title("Pixel differences")

    axs[1].plot(hist_diffs)
    axs[1].set_title("Color Histogram differences")

    axs[2].plot(time_array, samples)
    axs[2].set_title("Audio")
    axs[2].set_xlabel('Time (s)')
    axs[2].set_ylabel('Amplitude')

    plt.show()

if __name__ == "__main__":
    analyze()