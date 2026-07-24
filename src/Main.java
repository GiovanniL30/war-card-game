import components.MainFrame;
import components.panels.GameSetupPanel;
import components.ui.CardUI;
import enums.Rank;
import enums.Suit;
import game.Game;
import game.GameFileManager;
import model.Card;
import model.Deck;
import model.Player;

import javax.swing.*;

private final Scanner sc = new Scanner(System.in);

void main() {

    SwingUtilities.invokeLater(() -> {
        MainFrame mainFrame = new MainFrame("War Card Game");

        CardUI cardUI = new CardUI(new Card(Suit.CLUBS, Rank.ACE));
        cardUI.setBounds(20, 20, 100, 145);

        mainFrame.getContentPane().add(new GameSetupPanel());
    });

    System.out.println();
    System.out.println("""
            ██╗    ██╗ █████╗ ██████╗      ██████╗ █████╗ ██████╗ ██████╗      ██████╗  █████╗ ███╗   ███╗███████╗
            ██║    ██║██╔══██╗██╔══██╗    ██╔════╝██╔══██╗██╔══██╗██╔══██╗    ██╔════╝ ██╔══██╗████╗ ████║██╔════╝
            ██║ █╗ ██║███████║██████╔╝    ██║     ███████║██████╔╝██║  ██║    ██║  ███╗███████║██╔████╔██║█████╗
            ██║███╗██║██╔══██║██╔══██╗    ██║     ██╔══██║██╔══██╗██║  ██║    ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝
            ╚███╔███╔╝██║  ██║██║  ██║    ╚██████╗██║  ██║██║  ██║██████╔╝    ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗
             ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝  ╚═╝     ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝      ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝
            """);

    while (true) {
        GameFileManager fileManager = new GameFileManager();
        Deck playingDeck;

        do {
            playingDeck = fileManager.readAndInitializeDeck();
        } while (playingDeck == null);

        printHeader("WAR CARD GAME");
        System.out.println(playingDeck);

        int shuffleCount = getNumberInputInfiniteUntilCorrect("Enter Desired Shuffle Count", 1, 1000);
        shuffleDeck(shuffleCount, playingDeck);

        int playerCount = getNumberInputInfiniteUntilCorrect("Enter Number of Players", 2, 8);

        Game game = new Game(playingDeck, playerCount);
        Player gameWinner = game.startGame();

        printHeader("GAME OVER");
        System.out.printf("Winner        : %s%n", gameWinner.getPlayerName());
        System.out.printf("Total Rounds  : %d%n", game.getRound());
        System.out.printf("Cards Owned   : %d%n%n", gameWinner.getDeck().cardsCount());
        System.out.printf("Final Deck%n");
        System.out.printf("----------%n");
        System.out.println(gameWinner.getDeck());

        System.out.println("The winning deck will be saved to a text file.");
        fileManager.saveDeckToFile(gameWinner.getDeck());

        System.out.print("\nWould you like to run the program again? (y/n): ");
        String input = sc.nextLine().trim().toLowerCase();

        if (input.equals("n") || input.equals("no")) {
            break; // end the loop
        }
        System.out.println();
    }

    printHeader("Thank you for playing War Card Game.");
}

private void shuffleDeck(int shuffleCount, Deck playingDeck) {
    for (int i = 0; i < shuffleCount; i++) {
        playingDeck.shuffle();
    }
    printHeader("PLAYING DECK AFTER SHUFFLE");
    System.out.println(playingDeck);
}

private int getNumberInputInfiniteUntilCorrect(String message, int minValue, int maxValue) {

    int input = 0;

    while (input < minValue || input > maxValue) {
        try {
            System.out.printf("%s (%d-%d): ", message, minValue, maxValue);
            input = Integer.parseInt(sc.nextLine().trim());

            if (input < minValue || input > maxValue) {
                System.out.printf("Please enter a value between %d and %d%n", minValue, maxValue);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid Input, please enter a number only.");
        }
    }

    return input;
}

private void printHeader(String title) {
    System.out.printf("%n============================================================%n");
    System.out.printf("%s%n", title);
    System.out.printf("============================================================%n");
}