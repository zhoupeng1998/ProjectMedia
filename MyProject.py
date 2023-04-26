import sys
import subprocess
import multiprocessing
import time

import analyzer

start_time = time.time()

RAW_VIDEO_PATH = sys.argv[1]
RAW_AUDIO_PATH = sys.argv[2]

with open("files/env.txt", "w") as env_file:
    env_file.write(RAW_VIDEO_PATH + "\n")
    env_file.write(RAW_AUDIO_PATH + "\n")
    env_file.close()

processes = []

compress_cmd = [
    "ffmpeg",
    "-f", "rawvideo",
    "-pix_fmt", "rgb24",
    "-s", "480x270",
    "-r", "30",
    "-i", RAW_VIDEO_PATH,
    "-i", RAW_AUDIO_PATH,
    "-c:v", "libx264",
    "-profile:v", "high",
    "-pix_fmt", "yuv420p",
    "-crf", "20",
    "-c:a", "aac",
    "-strict", "experimental",
    "-b:a", "192k",
    "-y", ".\\files\\output.mp4"
]

build_cmd = ["mvn.cmd", "clean", "package"]


def compress():
    compress_process = subprocess.Popen(
        compress_cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    compress_process.communicate()
    print("Compressing done")


def build():
    build_process = subprocess.Popen(
        build_cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True)
    build_process.communicate()
    print("Building done")


# main
if __name__ == "__main__":

    # compress_process = multiprocessing.Process(target=compress)
    # build_process = multiprocessing.Process(target=build)

    # compress_process.start()
    # build_process.start()

    # compress_process.join()
    # analyzer.analyze()

    # analyzer.detect_boundary()
    analyzer.detect_boundary_color_histogram()

    # build_process.join()
    print("Elapsed time: " + str(time.time() - start_time))

    run_cmd = ["java", "-jar", "./target/projmedia-1.0-SNAPSHOT.jar"]
    run_process = subprocess.Popen(run_cmd, shell=True)
    run_process.communicate()
