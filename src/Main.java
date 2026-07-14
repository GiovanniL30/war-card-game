import enums.Rank;
import enums.Suit;
import model.Card;
import model.Deck;
import model.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final String INPUT_PATH = "src/input.txt";
    private static final int MAX_SHUFFLE = 10000;
    private static final int MIN_SHUFFLE = 1;
    private static final int MAX_PLAYERS = 8;
    private static final int MIN_PLAYERS = 2;
    private static final int DECK_SIZE = 52;

    public static void main(String[] args) {

        if (MAX_PLAYERS > 52) {
            System.out.println("Maximum allowed players is 52 only, please edit configuration");
            System.exit(1);
        }

        Deck playingDeck = readInitialDeck();

        printHeader("WAR CARD GAME");
        System.out.println(playingDeck);

        int shuffleCount = getNumberInputInfiniteUntilCorrect("Enter Desired Shuffle Count", MIN_SHUFFLE, MAX_SHUFFLE );
        shuffleDeck(shuffleCount, playingDeck);

        int playerCount = getNumberInputInfiniteUntilCorrect("Enter Number of Players", MIN_PLAYERS, MAX_PLAYERS);

        Game game = new Game(playingDeck, playerCount);
        game.distributeCards();

        while (game.getActivePlayersSize() > 1) {
            game.startRound();
        }

        Player gameWinner = game.getGameWinner();

        printHeader("GAME OVER");
        if (gameWinner != null) {
            System.out.printf("Winner        : %s%n", gameWinner.getPlayerName());
            System.out.printf("Total Rounds  : %d%n", game.getRound());
            System.out.printf("Cards Owned   : %d%n%n", gameWinner.getDeck().size());
            System.out.printf("Final Deck%n");
            System.out.printf("----------%n");
            System.out.println(gameWinner.getDeck());
        } else {
            System.out.printf("No winner was determined.%n");
            System.out.printf("The game ended without a player collecting all 52 cards.%n");
            System.out.printf("Total Rounds : %d%n", game.getRound());
        }
    }

    private static Deck readInitialDeck() {

        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_PATH))) {

            String line = reader.readLine();

            if (line == null || line.trim().isEmpty()) {
                throw new RuntimeException("Input file is empty.");
            }

            StringTokenizer inputTokenizer = new StringTokenizer(line, ",");

            Deck playingDeck = new Deck(new ArrayList<>());
            Set<String> seenCards = new HashSet<>();

            while (inputTokenizer.hasMoreTokens()) {

                String cardInput = inputTokenizer.nextToken().trim();

                StringTokenizer cardTokenizer = new StringTokenizer(cardInput, "-");

                if (cardTokenizer.countTokens() != 2) {
                    throw new RuntimeException("Invalid Card Detected: " + cardTokenizer);
                }

                String suitStr = cardTokenizer.nextToken().trim();
                String rankStr = cardTokenizer.nextToken().trim();

                Suit suit = Suit.fromString(suitStr);
                Rank rank = Rank.fromString(rankStr);

                String key = suit + "-" + rank;

                if (!seenCards.add(key)) {
                    throw new RuntimeException("Duplicate Card Detected: " + key);
                }

                playingDeck.addCard(new Card(suit, rank));
            }

            if (playingDeck.size() != DECK_SIZE) {
                throw new RuntimeException("Invalid Cards Length: Expected " + DECK_SIZE + ", Given " + playingDeck.size());
            }

            return playingDeck;

        } catch (RuntimeException | IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Program Terminated.");
            System.exit(1);
            return null;
        }
    }

    private static void shuffleDeck(int shuffleCount, Deck playingDeck) {
        for (int i = 0; i < shuffleCount; i++) {
            playingDeck.shuffle();
        }
        printHeader("PLAYING DECK AFTER SHUFFLE");
        System.out.println(playingDeck);
    }



    private static int getNumberInputInfiniteUntilCorrect(String message, int minValue, int maxValue) {

        int input = 0;

        while(input < minValue || input > maxValue) {
            try {
                System.out.print(message + " (" + minValue + "-" + maxValue + "): ");
                input = Integer.parseInt(sc.nextLine().trim());

                if(input < minValue || input > maxValue) {
                    System.out.printf("Please enter a value between %d and %d%n", minValue, maxValue);
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid Input, please enter a number only.");
            }
        }

        return  input;
    }

    private static void printHeader(String title) {
        System.out.printf("%n============================================================%n");
        System.out.printf("%s%n", title);
        System.out.printf("============================================================%n");
    }


}