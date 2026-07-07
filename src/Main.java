import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final int MAX_SHUFFLE = 10000;
    private static final int MIN_SHUFFLE = 1;
    private static final int MAX_PLAYERS = 52;
    private static final int MIN_PLAYERS = 2;

    private static Deck readInitialDeck() {

        String filePath = "src/input.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine();
            String[] cards = line.split(",");

            if (cards.length != 52) {
                throw new RuntimeException("Invalid Cards Length: Expected 52, Given " + cards.length);
            }

            Deck playingDeck = new Deck(new ArrayList<>());
            Set<String> seenCards = new HashSet<>();


            for (String cardInput : cards) {
                String[] cardStr = cardInput.split("-");

                if (cardStr.length != 2) {
                    throw new RuntimeException("Invalid Card Detected: " + Arrays.toString(cardStr));
                }

                String suitStr = cardStr[0];
                String rankStr = cardStr[1];

                Suit suit = Suit.fromString(suitStr);
                Rank rank = Rank.fromString(rankStr);

                String key = suit + "-" + rank;

                if (!seenCards.add(key)) {
                    throw new RuntimeException("Duplicate Card Detected: " + key);
                }

                playingDeck.addCard(new Card(suit, rank));
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

        System.out.printf("%n============================================================%n");
        System.out.printf("PLAYING DECK AFTER SHUFFLE%n");
        System.out.printf("============================================================%n");
        System.out.println(playingDeck);
    }

    private static int getNumberFromUser(String message) {
        int input = 0;

        try {
            System.out.print(message + ": ");
            input = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input, please enter a number only.");
        }

        return input;
    }

    public static void main(String[] args) {

        if(MAX_PLAYERS > 52) {
            System.out.println("Maximum allowed players is 52 only, please edit configuration");
            System.exit(1);
        }

        Deck playingDeck = readInitialDeck();

        System.out.printf("%n============================================================%n");
        System.out.print("                     WAR CARD GAME");
        System.out.printf("%n============================================================%n");
        System.out.println(playingDeck);

        //Deck Shuffle
        int shuffleCount = 0;

        while (shuffleCount < MIN_SHUFFLE || shuffleCount > MAX_SHUFFLE) {
            shuffleCount = getNumberFromUser(String.format("Enter Desired Shuffle Count (%d- %d)", MIN_SHUFFLE, MAX_SHUFFLE));

            if (shuffleCount < MIN_SHUFFLE || shuffleCount > MAX_SHUFFLE) {
                System.out.printf("Please Enter values between %d-%d%n", MIN_SHUFFLE, MAX_SHUFFLE);
            }
        }

        shuffleDeck(shuffleCount, playingDeck);

        //Number of Players
        int numberOfPlayers = 0;

        while (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS) {
            numberOfPlayers = getNumberFromUser(String.format("Enter Number of Players (%d - %d)", MIN_PLAYERS, MAX_PLAYERS));

            if (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS) {
                System.out.printf("Please Enter values between %d-%d%n", MIN_PLAYERS, MAX_PLAYERS);
            }
        }

        Game game = new Game(playingDeck, numberOfPlayers);
        game.distributeCards();

        while (game.getActivePlayersSize() > 1) {
            game.startRound();
        }

        System.out.printf("%n============================================================%n");
        System.out.printf("GAME OVER%n");
        System.out.printf("============================================================%n");

        Player gameWinner = game.getGameWinner();

        if (gameWinner != null) {
            System.out.printf("Winner        : %s%n", gameWinner.getPlayerName());
            System.out.printf("Total Rounds  : %d%n", game.getRound());
            System.out.printf("Cards Owned   : %d%n%n", gameWinner.getDeck().getCards().size());
            System.out.printf("Final Deck%n");
            System.out.printf("----------%n");
            System.out.println(gameWinner.getDeck());
        }else {
            System.out.printf("No winner was determined.%n");
            System.out.printf("The game ended without a player collecting all 52 cards.%n");
            System.out.printf("Total Rounds : %d%n", game.getRound());
        }
    }


}