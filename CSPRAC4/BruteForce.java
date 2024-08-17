import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BruteForce {
    private final String targetPassword;
    private final char[] characters = new char[127];
    private boolean found = false;

    public BruteForce(String targetPassword) {
        this.targetPassword = targetPassword;
        for (int i = 0; i < 127; i++) {
            characters[i] = (char) i;
        }
    }

    public void crackPassword() {
        long startTime = System.currentTimeMillis();
        int length = 1;
        while (!found) {
            Iterator<String> guesses = new CombinationIterator(length);
            while (guesses.hasNext()) {
                String guessPassword = guesses.next();
                if (guessPassword.equals(targetPassword)) {
                    found = true;
                    System.out.println("Password found: " + guessPassword);
                    return;
                }
            }
            length++;
            long elapsedTime = System.currentTimeMillis() - startTime;
            double estimatedTime = Math.pow(characters.length, length) / 1_000_000.0; // Rough estimate
            System.out.printf("Elapsed time: %.2fs | Estimated time for next length: %.2fs%n", elapsedTime / 1000.0, estimatedTime);
        }
        System.out.println("Password '" + targetPassword + "' cracked successfully.");
    }

    private class CombinationIterator implements Iterator<String> {
        private final int length;
        private final char[] combination;
        private boolean hasMore;

        CombinationIterator(int length) {
            this.length = length;
            this.combination = new char[length];
            Arrays.fill(combination, characters[0]);
            this.hasMore = true;
        }

        @Override
        public boolean hasNext() {
            return hasMore;
        }

        @Override
        public String next() {
            if (!hasMore) {
                throw new NoSuchElementException();
            }
            String result = new String(combination);
            increment();
            return result;
        }

        private void increment() {
            int pos = length - 1;
            while (pos >= 0) {
                if (combination[pos] < characters[characters.length - 1]) {
                    combination[pos]++;
                    return;
                }
                combination[pos] = characters[0];
                pos--;
            }
            hasMore = false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the target password: ");
        String target = scanner.nextLine();
        new BruteForce(target).crackPassword();
    }
}
