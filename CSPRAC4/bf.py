import itertools
import time
import string

class BruteForce:
    def __init__(self, target_password):
        self.target_password = target_password
        self.characters = string.printable[:127]  # All ASCII characters
        self.found = False

    def crack_password(self):
        start_time = time.time()
        length = 1
        while not self.found:
            for guess in itertools.product(self.characters, repeat=length):
                guess_password = ''.join(guess)
                if guess_password == self.target_password:
                    self.found = True
                    print(f"Password found: {guess_password}")
                    return
            length += 1
            elapsed_time = time.time() - start_time
            estimated_time = (len(self.characters) ** length) / 1000000  # Rough estimate
            print(f"Elapsed time: {elapsed_time:.2f}s | Estimated time for next length: {estimated_time:.2f}s")
        
        print(f"Password '{self.target_password}' cracked successfully.")

if __name__ == "__main__":
    target = input("Enter the target password: ")
    brute_force = BruteForce(target)
    brute_force.crack_password()
