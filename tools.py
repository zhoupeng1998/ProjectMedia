size = (480, 270)
fps = 30
audio_sample_rate = 44100

class VideoTime:
    def __init__(self, minutes, seconds, milliseconds=0):
        self.minutes = minutes
        self.seconds = seconds
        self.milliseconds = milliseconds

    def __str__(self):
        return f"{self.minutes}:{self.seconds}.{self.milliseconds}"

def time_to_frame(time: VideoTime, fps=fps):
    return time.minutes * 60 * fps + time.seconds * fps + time.milliseconds * fps // 1000

def frame_to_time(frame, fps=fps):
    minutes = frame // (60 * fps)
    seconds = (frame - minutes * 60 * fps) // fps
    milliseconds = (frame - minutes * 60 * fps - seconds * fps) * 1000 // fps
    return VideoTime(minutes, seconds, milliseconds)

def time_to_sample(time: VideoTime, sample_rate=audio_sample_rate):
    return time.minutes * 60 * sample_rate + time.seconds * sample_rate + time.milliseconds * sample_rate // 1000

def sample_to_time(sample, sample_rate=audio_sample_rate):
    minutes = sample // (60 * sample_rate)
    seconds = (sample - minutes * 60 * sample_rate) // sample_rate
    milliseconds = (sample - minutes * 60 * sample_rate - seconds * sample_rate) * 1000 // sample_rate
    return VideoTime(minutes, seconds, milliseconds)
