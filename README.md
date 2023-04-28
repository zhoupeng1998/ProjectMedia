# The Multimedia Project

This project designed to extract structure from video media elements, to construct scene and shot indices, and to build an interactive media player to explore the content in a more intuitive way.

The project runs with the following steps:
- building: build the interactive media player implemented in Java
- compressing: compress raw media into playable format
- analyzing: extract structure and construct indices for the media

## Prerequisites

This Project requires the following to run:
`python`
`ffmpeg`
`maven`

## Installation

### Python Dependencies

The following python packages are required to run the project, use the following command:
```
python3 -m pip install opencv-python librosa scenedetect
```

### Windows

Installation on Windows can be done using `chocolatey` with the following steps:
1. Install `chocolatey` by following the instructions at https://chocolatey.org/install
2. Open a powershell window as administrator and run the following command:
3. `choco install python3`
4. `choco install ffmpeg`
5. `choco install maven`

### Mac

Installation on Mac can be done using `homebrew` with the following steps:
1. Install `homebrew` by following the instructions at https://brew.sh/
2. Open a terminal window and run the following command:
3. `brew install python3 ffmpeg maven`

### Linux

Installation on Linux can be done using `apt-get` by running `sudo apt-get install python3 ffmpeg maven`

## Running the Project

Please run `MyProject.py` in the project root directory:

```
python3 .\MyProject.py <input_video_file_path> <input_audio_file_path> -flags
```
The optional flags accepts any combination of the following, when you are running the project multiple times:
- `-b` skip build
- `-c` skip media compress
- `-a` skip analyze

Example:
- `-abc` skip build, compress, and analyze


### Notes

If you want to adjust the video display size, please change the `SCALE_FACTOR` variable in `org.projmedia.domain.Dimensions`.
