import javax.swing.JOptionPane;
import java.util.Random;

public class GuessTheNumberGame {

    public static void main(String[] args) {

        int maxNumber = 100;
        int maxAttempts = 5;
        int rounds = 3;
        int totalScore = 0;

        Random rand = new Random();

        JOptionPane.showMessageDialog(null,
                "WELCOME TO GUESS THE NUMBER GAME\n" +
                "Guess a number between 1 and 100\n" +
                "Attempts per round: 5\n" +
                "Total rounds: 3");

        for (int round = 1; round <= rounds; round++) {

            int number = rand.nextInt(maxNumber) + 1;
            int attempts = 0;
            boolean guessed = false;

            while (attempts < maxAttempts && !guessed) {

                String input = JOptionPane.showInputDialog(
                        "Round " + round +
                        "\nAttempt " + (attempts + 1) +
                        " of " + maxAttempts +
                        "\nEnter your guess:");

                if (input == null) {
                    JOptionPane.showMessageDialog(null, "Game exited.");
                    return;
                }

                int guess;
                try {
                    guess = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number!");
                    continue;
                }

                attempts++;

                if (guess == number) {
                    guessed = true;
                    int score = 0;

                    // CLASSIC SWITCH (Java 8 compatible)
                    switch (attempts) {
                        case 1:
                            score = 100;
                            break;
                        case 2:
                            score = 80;
                            break;
                        case 3:
                            score = 60;
                            break;
                        case 4:
                            score = 40;
                            break;
                        case 5:
                            score = 20;
                            break;
                    }

                    totalScore += score;

                    JOptionPane.showMessageDialog(null,
                            "Congratulations!\n" +
                            "You guessed the number: " + number +
                            "\nAttempts used: " + attempts +
                            "\nScore this round: " + score);

                } else if (guess < number) {
                    JOptionPane.showMessageDialog(null,
                            "The number is GREATER than " + guess +
                            "\nAttempts left: " + (maxAttempts - attempts));
                } else {
                    JOptionPane.showMessageDialog(null,
                            "The number is LESS than " + guess +
                            "\nAttempts left: " + (maxAttempts - attempts));
                }
            }

            if (!guessed) {
                JOptionPane.showMessageDialog(null,
                        "You used all attempts!\nThe number was: " + number);
            }
        }

        JOptionPane.showMessageDialog(null,
                "GAME OVER\nYour total score: " + totalScore);
    }
}
