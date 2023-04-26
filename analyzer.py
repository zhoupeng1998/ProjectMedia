import os
import cv2
import librosa
import numpy as np
import matplotlib.pyplot as plt
import tools


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
        hist, _ = np.histogram(hsv[:, :, 0], bins=180, range=[0, 180])
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


def detect_boundary():
    video_path = "files/InputVideo0.rgb"
    # Create a VideoCapture object and read from input file
    # If the input is the camera, pass 0 instead of the video file name

    size = (480, 270)
    fps = 30

    # Create a named window for displaying the video
    cv2.namedWindow('Video Display', cv2.WINDOW_NORMAL)

    previous_frame = None
    previous_kp = None
    previous_des = None
    current_frame = None
    current_kp = None
    current_des = None
    sift = cv2.SIFT_create()

    boundaries = []

    with open(video_path, 'rb') as f:
        frame = 0
        while True:
            # Read raw RGB data for a single frame
            raw_data = f.read(size[0] * size[1] * 3)

            if not raw_data:
                break

            # Convert the raw data to a NumPy array and reshape it to match the frame dimensions
            # so that it can be used in cv2
            frame_data = np.frombuffer(
                raw_data, dtype=np.uint8).reshape((size[1], size[0], 3))

            # do not need this convertion for analyzing video
            frame_data_BGR = cv2.cvtColor(frame_data, cv2.COLOR_RGB2GRAY)

            # Display the frame
            # cv2.imshow('Video Display', frame_data_BGR)

            # Wait for the specified delay and exit the loop if the user presses 'q'
            '''
            key = cv2.waitKey(int(1000 / fps))
            if key == ord('q'):
                break
            '''
            # SIFT
            if previous_frame is None:
                previous_frame = frame_data_BGR
                previous_kp, previous_des = sift.detectAndCompute(
                    previous_frame, None)
            else:
                current_frame = frame_data_BGR

                current_kp, current_des = sift.detectAndCompute(
                    current_frame, None)
                FLANN_INDEX_KDTREE = 1
                index_params = dict(algorithm=FLANN_INDEX_KDTREE, trees=5)
                search_params = dict(checks=50)
                flann = cv2.FlannBasedMatcher(index_params, search_params)
                matches = flann.knnMatch(previous_des, current_des, k=2)

                good_matches = 0
                for m, n in matches:
                    if m.distance < 0.7 * n.distance:
                        good_matches += 1

                if good_matches < 3:
                    boundaries.append([1, "Scene", frame])
                    print(frame)
                elif good_matches < 7:
                    boundaries.append([2, "Shot", frame])

                previous_frame = frame_data_BGR
                previous_kp = current_kp
                previous_des = current_des

                frame = frame + 1

    scene_idx = 1
    shot_idx = 1
    subshot_idx = 1
    with open("files/indexfile.txt", "w") as f:
        for entry in boundaries:
            if entry[0] == 1:
                # scene
                # reset shot idx, subshot idx
                shot_idx = 1
                subshot_idx = 1
                f.write(str(entry[0]) + "." + entry[1] + " " + str(scene_idx) +
                        ":" + (tools.frame_to_time(entry[2])).to_str_min_sec() + "\n")
                scene_idx += 1
            elif entry[0] == 2:
                # shot
                # reset subshot idx
                subshot_idx = 1
                f.write(str(entry[0]) + "." + entry[1] + " " + str(shot_idx) +
                        ":" + (tools.frame_to_time(entry[2])).to_str_min_sec() + "\n")
                shot_idx += 1
            else:
                # subshot
                f.write(str(entry[0]) + "." + entry[1] + " " + str(subshot_idx) +
                        ":" + (tools.frame_to_time(entry[2])).to_str_min_sec() + "\n")
                subshot_idx += 1


def detect_boundary_color_histogram():
    video_path = "files/InputVideo0.rgb"
    mp4_path = "files/output.mp4"
    # Create a VideoCapture object and read from input file
    # If the input is the camera, pass 0 instead of the video file name

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
    print(distances)

    # $hist_diffs = []

    cv2.normalize(distances, distances)
    max_value = np.max(distances)
    indices = np.where(distances > max_value*0.8)

    boundaries = [["1", "Scene", i+1] for i in indices[0]]

    scene_idx = 1
    shot_idx = 1
    subshot_idx = 1
    with open("files/indexfile.txt", "w") as f:
        for entry in boundaries:
            if entry[0] == 1:
                # scene
                # reset shot idx, subshot idx
                shot_idx = 1
                subshot_idx = 1
                f.write(str(entry[0]) + "." + entry[1] + " " + str(scene_idx) +
                        ":" + (tools.frame_to_time(entry[2])).to_str_min_sec() + "\n")
                scene_idx += 1
            elif entry[0] == 2:
                # shot
                # reset subshot idx
                subshot_idx = 1
                f.write(str(entry[0]) + "." + entry[1] + " " + str(shot_idx) +
                        ":" + (tools.frame_to_time(entry[2])).to_str_min_sec() + "\n")
                shot_idx += 1
            else:
                # subshot
                f.write(str(entry[0]) + "." + entry[1] + " " + str(subshot_idx) +
                        ":" + (tools.frame_to_time(entry[2])).to_str_min_sec() + "\n")
                subshot_idx += 1


if __name__ == "__main__":
    # analyze()
    detect_boundary()
