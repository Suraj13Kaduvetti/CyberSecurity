#include <iostream>
#include <string>
#include <chrono>
#include <cmath>
#include <vector>

class BruteForce {
public:
    BruteForce(const std::string& targetPassword) : targetPassword(targetPassword), found(false) {
        characters.resize(127);
        for (int i = 0; i < 127; ++i) {
            characters[i] = static_cast<char>(i);
        }
    }

    void crackPassword() {
        auto startTime = std::chrono::high_resolution_clock::now();
        int length = 1;

        while (!found) {
            std::vector<int> indices(length, 0);
            while (true) {
                std::string guessPassword = createPassword(indices);
                if (guessPassword == targetPassword) {
                    found = true;
                    std::cout << "Password found: " << guessPassword << std::endl;
                    return;
                }
                if (!incrementIndices(indices)) {
                    break;
                }
            }
            length++;
            auto elapsedTime = std::chrono::high_resolution_clock::now() - startTime;
            double elapsedSeconds = std::chrono::duration<double>(elapsedTime).count();
            double estimatedTime = std::pow(characters.size(), length) / 1e6; // Rough estimate
            std::cout << "Elapsed time: " << elapsedSeconds << "s | Estimated time for next length: " << estimatedTime << "s" << std::endl;
        }

        std::cout << "Password '" << targetPassword << "' cracked successfully." << std::endl;
    }

private:
    std::string targetPassword;
    std::vector<char> characters;
    bool found;

    std::string createPassword(const std::vector<int>& indices) {
        std::string password;
        for (int index : indices) {
            password += characters[index];
        }
        return password;
    }

    bool incrementIndices(std::vector<int>& indices) {
        for (int i = indices.size() - 1; i >= 0; --i) {
            if (indices[i] < characters.size() - 1) {
                indices[i]++;
                return true;
            }
            indices[i] = 0;
        }
        return false;
    }
};

int main() {
    std::string target;
    std::cout << "Enter the target password: ";
    std::cin >> target;
    BruteForce bruteForce(target);
    bruteForce.crackPassword();
    return 0;
}
