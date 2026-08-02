package org.svi;

import org.svi.game.Game;
import org.svi.game.GameFileManager;
import org.svi.model.Deck;
import org.svi.model.Player;

import java.util.*;

public class Main {

    private final Scanner sc = new Scanner(System.in);

    void main() {

        System.out.println();
        System.out.println("""
                ██╗    ██╗ █████╗ ██████╗      ██████╗ █████╗ ██████╗ ██████╗      ██████╗  █████╗ ███╗   ███╗███████╗
                ██║    ██║██╔══██╗██╔══██╗    ██╔════╝██╔══██╗██╔══██╗██╔══██╗    ██╔════╝ ██╔══██╗████╗ ████║██╔════╝
                ██║ █╗ ██║███████║██████╔╝    ██║     ███████║██████╔╝██║  ██║    ██║  ███╗███████║██╔████╔██║█████╗
                ██║███╗██║██╔══██║██╔══██╗    ██║     ██╔══██║██╔══██╗██║  ██║    ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝
                ╚███╔███╔╝██║  ██║██║  ██║    ╚██████╗██║  ██║██║  ██║██████╔╝    ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗
                 ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝  ╚═╝     ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝      ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝
                """);

        System.out.print("Press Enter to continue: ");
        sc.nextLine();

        int totalGamesPlayed = 0;
        int totalRounds = 0;
        Map<String, Integer> playerWins = new HashMap<>();
        List<String> gameByGameSummary = new ArrayList<>();

        while (true) {
            GameFileManager fileManager = new GameFileManager();
            Deck playingDeck;

            do {
                if (fileManager.getAvailableFilesLength() == 0) {
                    printHeader("No deck files found. Creating a standard 52-card deck...");
                    playingDeck = Deck.buildDeck();
                } else {
                    playingDeck = fileManager.readAndInitializeDeck();
                }
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

            totalGamesPlayed++;
            totalRounds += game.getRound();
            playerWins.merge(gameWinner.getPlayerName(), 1, Integer::sum);
            gameByGameSummary.add(String.format("Game %d - Winner: %s | Players: %d | Total Rounds: %d", totalGamesPlayed, gameWinner.getPlayerName(), playerCount, game.getRound()));

            System.out.print("\nWould you like to run the program again? (y/n): ");
            String input = sc.nextLine().trim().toLowerCase();

            if (input.equals("n") || input.equals("no")) {
                break; // end the loop
            }
            System.out.println();
        }

        printHeader("GAME SUMMARY");
        System.out.printf("Total Games Played : %d%n", totalGamesPlayed);

        if (totalGamesPlayed > 0) {
            System.out.printf("Average Rounds     : %.2f%n", (double) totalRounds / totalGamesPlayed);
        }

        System.out.println("\nWins Per Player");
        System.out.println("----------------");
        playerWins.forEach((player, wins) -> System.out.printf("%s : %d win%s%n", player, wins, wins == 1 ? "" : "s"));

        System.out.println("\nGame-by-Game Results");
        System.out.println("--------------------");
        gameByGameSummary.forEach(System.out::println);

        printHeader("Thank You for Playing War Card Game");
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
}
