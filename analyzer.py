import os
import time
import cv2
import librosa
import numpy as np
import matplotlib.pyplot as plt
from scenedetect import SceneManager, open_video, ContentDetector, AdaptiveDetector
import tools

size = (480, 270)
fps = 30

def read_env():
    video_path = "files/InputVideo.rgb"
    audio_path = "files/InputAudio.wav"
    mp4_path = "files/output.mp4"

    with open("files/env.txt", "r") as f:
        video_path = os.path.normpath(f.readline().strip()).replace("\\", "/")
        audio_path = os.path.normpath(f.readline().strip()).replace("\\", "/")
        f.close()

    return video_path, audio_path, mp4_path

# analyze frame differences, color histograms, and audio
def frame_analyzer():
    video_path, audio_path, mp4_path = read_env()

    # analyze video
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
    print("Histogram differences: ", hist_diffs.shape)
    hist_mean_diffs = hist_diffs.mean(axis=1)
    print("Histogram mean differences: ", hist_mean_diffs.shape)

    # NOTE: disable plots when running in production
    fig, axs = plt.subplots(4)

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

    axs[2].plot(hist_mean_diffs)
    axs[2].set_title("Color Histogram mean differences")

    axs[3].plot(time_array, samples)
    axs[3].set_title("Audio")
    axs[3].set_xlabel('Time (s)')
    axs[3].set_ylabel('Amplitude')

    plt.show()

# detect shots and subshots using detector
def scene_analyzer():
    video_path, audio_path, mp4_path = read_env()

    video = open_video(mp4_path)
    video2 = open_video(mp4_path)

    subshot_scene_manager = SceneManager()
    subshot_scene_manager.add_detector(ContentDetector(threshold=15))

    shot_scene_manager = SceneManager()
    shot_scene_manager.add_detector(AdaptiveDetector())

    subshot_scene_manager.detect_scenes(frame_source=video)
    shot_scene_manager.detect_scenes(frame_source=video2)

    subshot_list = subshot_scene_manager.get_scene_list()
    shot_list = shot_scene_manager.get_scene_list()

    subshot_index_list = [int(i[0]) for i in subshot_list]
    shot_index_list = [int(i[0]) for i in shot_list]

    # for debugging
    '''
    for i in range(len(subshot_list)):
        print("Subshot %d: Start %d, End %d, at %s" % (i, subshot_list[i][0], subshot_list[i][1], str(tools.frame_to_time(int(subshot_list[i][0])))))
    
    for i in range(len(shot_list)):
        print("Shot %d: Start %d, End %d, at %s" % (i, shot_list[i][0], shot_list[i][1], str(tools.frame_to_time(int(shot_list[i][0])))))
    '''
    
    # NOTE: return two lists containing the start frame number of each subshot and shot in integer
    return shot_index_list, subshot_index_list

def histogram_analyzer():
    video_path, audio_path, mp4_path = read_env()

    video_cap = cv2.VideoCapture(mp4_path)
    video_cap.set(cv2.CAP_PROP_FRAME_WIDTH, size[0])
    video_cap.set(cv2.CAP_PROP_FRAME_HEIGHT, size[1])
    video_cap.set(cv2.CAP_PROP_FPS, fps)

    histograms = []

    while video_cap.isOpened():
        ret, frame = video_cap.read()
        if not ret:
            break

        hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
        hist_h = cv2.calcHist([hsv], [0], None, [64], [0, 180])
        hist_s = cv2.calcHist([hsv], [1], None, [64], [0, 255])
        hist_v = cv2.calcHist([hsv], [1], None, [64], [0, 255])

        cv2.normalize(hist_h, hist_h)
        cv2.normalize(hist_s, hist_s)
        cv2.normalize(hist_v, hist_v)

        hist = np.concatenate([hist_h, hist_s, hist_v]).flatten()

        histograms.append(hist)

    histograms = np.array(histograms)
    hist_diffs = np.diff(histograms, axis=0)
    distances = np.linalg.norm(hist_diffs, axis=1)

    cv2.normalize(distances, distances)
    max_value = np.max(distances)
    indices = np.where(distances > max_value*0.6)

    boundaries = indices[0]

    # set a minimum distance between boundaries
    index_list = [boundaries[0]]
    for i in range(1, len(boundaries)):
        if boundaries[i] - boundaries[i-1] > 10:
            index_list.append(boundaries[i])

    return index_list


if __name__ == "__main__":
    start_time = time.time()

    scene_analyzer()
    histogram_analyzer()
    #frame_analyzer()

    print("Time elapsed: ", time.time() - start_time)