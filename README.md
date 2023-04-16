# The Multimedia Project

## Prerequisites

This Project requires the following to run:
`ffmpeg`
`maven`

## Installation

### Windows

Installation on Windows can be done using `chocolatey` with the following steps:
1. Install `chocolatey` by following the instructions at https://chocolatey.org/install
2. Open a powershell window as administrator and run the following command:
3. `choco install ffmpeg`
4. `choco install maven`

### Mac

Installation on Mac can be done using `homebrew` with the following steps:
1. Install `homebrew` by following the instructions at https://brew.sh/
2. Open a terminal window and run the following command:
3. `brew install ffmpeg maven`

### Linux

Installation on Linux can be done using `apt-get` by running `sudo apt-get install ffmpeg maven`

## Getting Started

- This project develops with Java 8 and Maven, using IntelliJ IDEA.
- To get started, please include your media files in the `files` directory in the project root directory.

## Running the Project

Please run the scripts in the project root directory:

### Windows
```
./MyProject.bat <input_video_file_path> <input_audio_file_path>
```

### Mac/Linux
```bash
chmod +x MyProject.sh 
./MyProject.sh <input_video_file_path> <input_audio_file_path>`
```

