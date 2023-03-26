import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * An over/under game that allows the user to play by themselves, play with
 * a friend, or play against the computer. In each game, a random number between
 * 1 and 13 is rolled, and a player must guess if the next number will be higher
 * or lower than that number. The same number is never rolled twice.
 *
 * @author Chris Wentz
 * @version 1.0.0  [Last Updated: 03/25/2023]
 * */
public class OverUnderGame {

    /**
     * The main method displays the game instructions enters the main menu loop.
     * The player can choose to start a one player game, start a two player game
     * with a friend, or start a two player game against the computer, or quit the
     * program. Invalid commands print an error and prompt the user for new input.
     * */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();  // Used to generate random numbers in the game modes
        String playerMenuChoice = "";  // Stores the player's menu choice

        showInstructions();

        while (!playerMenuChoice.equals("q")) {
            displayMainMenu();
            playerMenuChoice = scanner.nextLine().toLowerCase();
            switch (playerMenuChoice) {
                case "q":  // Quits the game
                    System.out.println("\n*** Thanks for playing, see you again soon! ***\n");
                    break;
                case "1":  // Starts a one player game
                    onePlayerGame(scanner, random);
                    break;
                case "2":  // Starts a two player game against a friend
                    againstFriendGame(scanner, random);
                    break;
                case "3": // Starts a two player game against the computer
                    againstComputerGame(scanner, random);
                    break;
                default:
                    System.out.println("\n< Invalid command. Please try again! >");
                    break;
            }
        }
    }

    /** Displays the game instructions to the player */
    public static void showInstructions() {
        System.out.println("\n*** Let's play the Over/Under Game! ***\n");
        System.out.println("The goal of this game is to take turns guessing if the");
        System.out.println("next random number will be higher or lower than the");
        System.out.println("current number. You can play by yourself, against ");
        System.out.println("a friend, or against the computer.");
    }

    /** Displays the main menu to the player */
    public static void displayMainMenu() {
        System.out.println("\n*** Main Menu: ***\n");
        System.out.println("[1]: Play by yourself and get a high score");
        System.out.println("[2]: Play a two player game with a friend");
        System.out.println("[3]: Play against the computer");
        System.out.println("[q]: Quit");
        System.out.print("\nEnter a command: ");
    }

    /** A brief delay used for suspense or to simulate the computer "thinking" */
    public static void waitASecond(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        }
        catch (InterruptedException ignored) {  // Ignores any interruption to the time delay
        }
    }

    /**
     * This method contains the one player game. In this game mode, a player
     * score mechanic allows the player to get points for each correct guess.
     * The numbers are randomly rolled between 1 and 13 and the same number
     * cannot be rolled twice. The player is prompted to guess whether the
     * next number will be higher or lower than the current number. They also
     * have the option to return to the main menu at any time.
     * */
    public static void onePlayerGame(Scanner scanner, Random random) {
        int playerScore = 0;      // Stores the player score
        int currentNumber;        // Stores the current random number
        int lastNumber;           // Stores the previous random number
        String playerGuess = "";  // Stores the player's guess or allows the player to quit

        // Game mode instructions
        System.out.println("\n*** One Player Mode ***\n");
        System.out.println("The numbers go from 1 to 13.");
        System.out.println("Guess if the second number will be higher or lower!");
        System.out.println("The next number will never be the same number.");
        currentNumber = random.nextInt(13) + 1;  // Generates a random number from 1-13
        System.out.println("The first number is " + currentNumber + ".");


        // Main game mode loop
        while (true) {
            System.out.println("\nCurrent score: " + playerScore);
            System.out.println("Current number: " + currentNumber);
            System.out.println("Enter \"h\" for higher, \"l\" for lower, or \"q\" to quit: ");
            playerGuess = scanner.nextLine().toLowerCase();

            // Input validation
            while (!playerGuess.equals("q") && (!playerGuess.equals("h")) && (!playerGuess.equals("l"))) {
                System.out.println("< Invalid choice, please try again! >");
                System.out.println("\nCurrent score: " + playerScore);
                System.out.println("Current number: " + currentNumber);
                System.out.println("Enter \"h\" for higher, \"l\" for lower, or \"q\" to quit: ");
                playerGuess = scanner.nextLine().toLowerCase();
            }

            lastNumber = currentNumber;            // Stores the previous number when rolling a new one
            while (currentNumber == lastNumber) {  // Ensures the next number is never the same number as before
                currentNumber = random.nextInt(13) + 1;
            }
            switch (playerGuess) {
                case "q":
                    System.out.println("Final score: " + playerScore + ". Returning to main menu.");
                    return; // Returns to main menu
                case "h":
                    waitASecond(1);
                    System.out.println("The next number is: " + currentNumber);
                    if (currentNumber > lastNumber) {  // If the player is correct
                        ++playerScore;
                        System.out.println("Good job! " + currentNumber + " is higher than " + lastNumber + ".");
                    } else {  // If the player was wrong
                        System.out.println("Oh no! " + currentNumber + " is lower than " + lastNumber + "!");
                        System.out.println("Game over. Your final score is " + playerScore + ".");
                        return;  // Returns to the main menu
                    }
                    break;  // Breaks out of the switch and goes back to the top of the main while loop
                case "l":
                    waitASecond(1);
                    System.out.println("The next number is: " + currentNumber);
                    if (currentNumber < lastNumber) {  // If the player is correct
                        ++playerScore;
                        System.out.println("Good job! " + currentNumber + " is lower than " + lastNumber + ".");
                    } else {  // If the player was wrong
                        System.out.println("Oh no! " + currentNumber + " is higher than " + lastNumber + "!");
                        System.out.println("Game over. Your final score is " + playerScore + ".");
                        return;   // Returns to the main menu
                    }
                    break;  // Breaks out of the switch and goes back to the top of the main while loop
            }
        }
    }

    /**
     * This method contains the "against a friend" game mode. In this game mode,
     * two players take turns guessing whether the next number will be higher
     * or lower. The numbers are again randomly rolled between 1 and 13 and the
     * same number cannot be rolled twice. If a player guesses wrongly, they lose
     * the game and the other player wins. Either player can choose to quit at any
     * time, which causes the other player to win the game.
     * */
    public static void againstFriendGame(Scanner scanner, Random random) {
        int currentNumber;                    // Contains the current random number
        int lastNumber;                       // Contains the previous random number
        String currentPlayer = "Player One";  // Keeps track of the current player
        String otherPlayer = "Player Two";    // Keeps track of the other player
        String playerGuess = "";              // Used to store both users' guesses

        System.out.println("\n*** Against a Friend Mode ***\n");
        System.out.println("The numbers go from 1 to 13.");
        System.out.println("Guess if the second number will be higher or lower!");
        System.out.println("The next number will never be the same number.");
        currentNumber = random.nextInt(13) + 1;  // Generates a random number from 1-13
        System.out.println("The first number is " + currentNumber + ".");

        // Main game mode loop
        while (true) {
            System.out.println("\nIt's your turn, " + currentPlayer + "!");
            System.out.println("Current number: " + currentNumber);
            System.out.println("Enter \"h\" for higher, \"l\" for lower, or \"q\" to quit: ");
            playerGuess = scanner.nextLine().toLowerCase();

            // Input validation
            while (!playerGuess.equals("q") && (!playerGuess.equals("h")) && (!playerGuess.equals("l"))) {
                System.out.println("< Invalid choice, please try again! >");
                System.out.println("\nIt's your turn, " + currentPlayer + "!");
                System.out.println("Current number: " + currentNumber);
                System.out.println("Enter \"h\" for higher, \"l\" for lower, or \"q\" to quit: ");
                playerGuess = scanner.nextLine().toLowerCase();
            }
            lastNumber = currentNumber;
            while (currentNumber == lastNumber) {  // Ensures the next number is never the same number as before
                currentNumber = random.nextInt(13) + 1;
            }
            switch (playerGuess) {
                case "q":  // Player chose to quit
                    System.out.println(currentPlayer + " quit the game. " + otherPlayer + " wins!");
                    return; // Returns to main menu
                case "h":  // "Higher"
                    waitASecond(1);
                    System.out.println("The next number is: " + currentNumber);
                    if (currentNumber > lastNumber) {  // The current player guessed correctly
                        System.out.println("Good job " + currentPlayer + "! " + currentNumber
                                + " is higher than " + lastNumber + ".");
                    } else {  // The current player was wrong
                        System.out.println("Oh no! " + currentNumber + " is lower than " + lastNumber + "!");
                        System.out.println("Game over. " + otherPlayer + " wins!");
                        return;  // Returns to the main menu
                    }
                    break;
                case "l":  // "Lower"
                    waitASecond(1);
                    System.out.println("The next number is: " + currentNumber);
                    if (currentNumber < lastNumber) {  // The current player guessed correctly
                        System.out.println("Good job " + currentPlayer + "! " + currentNumber
                                + " is lower than " + lastNumber + ".");
                    } else {  // The current player was wrong
                        System.out.println("Oh no! " + currentNumber + " is higher than " + lastNumber + "!");
                        System.out.println("Game over. " + otherPlayer + " wins!");
                        return;  // Returns to the main menu
                    }
                    break;
            }
            // Swaps the current and other player
            if (currentPlayer.equals("Player One")) {
                currentPlayer = "Player Two";
                otherPlayer = "Player One";
            }
            else {
                currentPlayer = "Player One";
                otherPlayer = "Player Two";
            }
        }
    }

    /**
     * This method contains the "against the computer" game mode. In this game mode,
     * the player competes against a computer with a relatively simplistic "AI".
     * The numbers are again randomly rolled between 1 and 13 and the
     * same number cannot be rolled twice. If a player guesses wrongly, they lose
     * the game and the other player wins. The player has the option to return to
     * the main menu at any time during their turn.
     * */
    public static void againstComputerGame(Scanner scanner, Random random) {
        int currentNumber;          // Stores the current random number
        int lastNumber;             // Stores the previous random number
        String playerGuess = "";    // Stores the player's guess
        String computerGuess = "";  // Stores the computer's (random) guess

        System.out.println("\n*** Against the Computer Mode ***\n");
        System.out.println("The numbers go from 1 to 13.");
        System.out.println("Guess if the second number will be higher or lower!");
        System.out.println("The next number will never be the same number.");
        System.out.println("You go first, the computer goes second.");
        currentNumber = random.nextInt(13) + 1;  // Generates a random number from 1-13
        System.out.println("The first number is " + currentNumber + ".");


        // Main game mode loop
        while (true) {
            System.out.println("\nIt's your turn!");
            System.out.println("Current number: " + currentNumber);
            System.out.println("Enter \"h\" for higher, \"l\" for lower, or \"q\" to quit: ");
            playerGuess = scanner.nextLine().toLowerCase();

            // Input validation
            while (!playerGuess.equals("q") && (!playerGuess.equals("h")) && (!playerGuess.equals("l"))) {
                System.out.println("< Invalid choice, please try again! >");
                System.out.println("\nIt's your turn!");
                System.out.println("Current number: " + currentNumber);
                System.out.println("Enter \"h\" for higher, \"l\" for lower, or \"q\" to quit: ");
                playerGuess = scanner.nextLine().toLowerCase();
            }
            lastNumber = currentNumber;
            while (currentNumber == lastNumber) {  // Ensures the next number is never the same number as before
                currentNumber = random.nextInt(13) + 1;
            }
            switch (playerGuess) {
                case "q":
                    System.out.println("Returning to main menu.");
                    return; // Returns to main menu
                case "h":
                    waitASecond(1);
                    System.out.println("The next number is: " + currentNumber);
                    if (currentNumber > lastNumber) {
                        System.out.println("Good job! " + currentNumber + " is higher than " + lastNumber + ".");
                    }
                    else {
                        System.out.println("Oh no! " + currentNumber + " is lower than " + lastNumber + "!");
                        System.out.println("Game over. The computer wins!");
                        return;  // Returns to the main menu
                    }
                    break;
                case "l":
                    waitASecond(1);
                    System.out.println("The next number is: " + currentNumber);
                    if (currentNumber < lastNumber) {
                        System.out.println("Good job! " + currentNumber + " is lower than " + lastNumber + ".");

                    }
                    else {
                        System.out.println("Oh no! " + currentNumber + " is higher than " + lastNumber + "!");
                        System.out.println("Game over. The computer wins!");
                        return;   // Returns to the main menu
                    }
                    break;
                default:
                    System.out.println("< Invalid choice, please try again! >");
                    break;
            }

            /*
            * If the player was correct, the computer takes a turn. The computer's "AI" consists of
            * it simply flipping a coin to decide "higher" or "lower". An idea for a future version
            * of this game mode could be to have the computer calculate which option is more
            * statistically likely and going with that.
            */
            System.out.println("\nIt's the computer's turn!");
            System.out.println("The current number is: " + currentNumber + ".");
            if (random.nextInt(2) == 0) {  // Simulates flipping a coin, will be either 0 or 1
                computerGuess = "higher";
            }
            else {
                computerGuess = "lower";
            }
            waitASecond(2);  // The computer waits two seconds to simulate "thinking"
            System.out.println("\n[Computer]: \"I think it will be " + computerGuess + "\".");
            lastNumber = currentNumber;
            while (currentNumber == lastNumber) {  // Ensures the next number is never the same number as before
                currentNumber = random.nextInt(13) + 1;  // Randomly rolls a number from 1 to 13
            }
            waitASecond(1);
            System.out.println("The next number is: " + currentNumber);
            if ((computerGuess.equals("higher") && (currentNumber > lastNumber))
             || (computerGuess.equals("lower") && (currentNumber < lastNumber))) {
                System.out.println("Good job, computer! It's the player's turn!");
            }
            else {
                System.out.println("The computer was wrong. The player wins!!!");
                return;  // Returns to the main menu
            }
        }
    }
}
