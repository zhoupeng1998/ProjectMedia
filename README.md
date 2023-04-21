# The Multimedia Project

## Prerequisites

This Project requires the following to run:
`python`
`ffmpeg`
`maven`

## Installation

### Python Dependencies

The following python packages are required to run the project, use the following command:
```
python3 -m pip install opencv-python librosa
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

## Getting Started

- This project develops with Java 8 and Maven, using IntelliJ IDEA.
- To get started, please include your media files in the `files` directory in the project root directory.

## Running the Project

Please run the scripts in the project root directory:

### Using Python
```
python3 .\MyProject.py <input_video_file_path> <input_audio_file_path>
```

### Notes

If you want to adjust the video size, please change the `SCALE_FACTOR` variable in `org.projmedia.domain.Dimensions`.
