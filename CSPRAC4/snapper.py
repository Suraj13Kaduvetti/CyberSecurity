import os
import time
from PIL import ImageGrab
import keyboard

screenshot_folder = "ss"

if not os.path.exists(screenshot_folder):
    os.makedirs(screenshot_folder)

print("Press Enter to take a screenshot. Press ESC to exit.")

while True:
    if keyboard.is_pressed('enter'):
        timestamp = time.strftime("%Y%m%d_%H%M%S")
        screenshot_filename = f"{screenshot_folder}/screenshot_{timestamp}.png"

        screenshot = ImageGrab.grab()
        screenshot.save(screenshot_filename)

        print(f"Screenshot saved as {screenshot_filename}")

        time.sleep(1)

    if keyboard.is_pressed('esc'):
        print("Exiting...")
        break

