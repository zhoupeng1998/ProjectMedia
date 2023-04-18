import sys
import subprocess
import threading
import time

start_time = time.time()

RAW_VIDEO_PATH = sys.argv[1]
RAW_AUDIO_PATH = sys.argv[2]

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

def compress():
    compress_process = subprocess.Popen(compress_cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    compress_process.communicate()
    print("Compressing done")

def build():
    build_cmd = ["mvn.cmd", "clean", "package"]
    build_process = subprocess.Popen(build_cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    build_process.communicate()
    print("Building done")

compress()
print("Elapsed time: " + str(time.time() - start_time))
build()
print("Elapsed time: " + str(time.time() - start_time))

run_cmd = ["java", "-jar", "./target/projmedia-1.0-SNAPSHOT.jar"]
run_process = subprocess.Popen(run_cmd,shell=True)
run_process.communicate()