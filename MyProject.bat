@echo off

set RAW_VIDEO_PATH=%1
set RAW_AUDIO_PATH=%2

echo %RAW_VIDEO_PATH% > .\files\env.txt
echo %RAW_AUDIO_PATH% >> .\files\env.txt

ffmpeg -f rawvideo -pix_fmt rgb24 -s 480x270 -r 30 -i %RAW_VIDEO_PATH% -i %RAW_AUDIO_PATH% -c:v libx264 -profile:v high -pix_fmt yuv420p -crf 20 -c:a aac -strict experimental -b:a 192k -y .\files\output.mp4
call mvn clean package
java -jar .\target\projmedia-1.0-SNAPSHOT.jar