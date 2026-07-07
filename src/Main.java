import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

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
                String[] card = cardInput.split("-");

                if (card.length != 2) {
                    throw new RuntimeException("Invalid Card Detected: " + Arrays.toString(card));
                }

                String suitStr = card[0];
                String rankStr = card[1];

                Suit suit;
                Rank rank;

                try {
                    suit = Suit.fromString(suitStr);
                    rank = Rank.fromString(rankStr);
                } catch (IllegalArgumentException e) {
                  throw new IllegalArgumentException(e.getMessage());
                }

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

    private static void distributeCards(Deck playingDeck, ArrayList<Player> players) {

        int currentIdx = 0;

        while (!playingDeck.getCards().isEmpty()) {
            Card card = playingDeck.drawCard();
            players.get(currentIdx).getDeck().addCard(card);
            currentIdx = (currentIdx + 1) % players.size();
        }

    }

    private static int getNumberFromUser(String message) {

        Scanner sc = new Scanner(System.in);
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

        Deck playingDeck = readInitialDeck();

        System.out.printf("%n");
        System.out.printf("============================================================%n");
        System.out.printf("                     WAR CARD GAME%n");
        System.out.printf("============================================================%n");

        System.out.println(playingDeck);


        int shuffleCount = 0;

        while (shuffleCount < 1 || shuffleCount > 100) {
            shuffleCount = getNumberFromUser("Enter Desired Shuffle Count (1 - 100)");

            if (shuffleCount > 100 || shuffleCount < 1) {
                System.out.println("Please Enter values between 1-100");
            }
        }

        for (int i = 0; i < shuffleCount; i++) {
            playingDeck.shuffle();
        }

        System.out.printf("%n============================================================%n");
        System.out.printf("PLAYING DECK AFTER SHUFFLE%n");
        System.out.printf("============================================================%n");
        System.out.println(playingDeck);

        int numberOfPlayers = 0;

        while (numberOfPlayers < 2 || numberOfPlayers > 8) {
            numberOfPlayers = getNumberFromUser("Enter Number of Players (2 - 8)");

            if (numberOfPlayers < 2 || numberOfPlayers > 8) {
                System.out.println("Please Enter values between 2-8");
            }
        }

        ArrayList<Player> players = new ArrayList<>(numberOfPlayers);

        for (int i = 1; i <= numberOfPlayers; i++) {
            players.add(new Player("Player " + i, new Deck(new ArrayList<>())));
        }

        distributeCards(playingDeck, players);

        int round = 1;

        System.out.printf("%n============================================================%n");
        System.out.printf("INITIAL PLAYER DECKS%n");
        System.out.printf("============================================================%n\n");

        for (Player player : players) {
            System.out.println(player + "\n");
        }

        while (players.size() > 1) {

            System.out.printf("%n============================================================%n");
            System.out.printf("ROUND %-3d%n", round);
            System.out.printf("============================================================%n");

            // Player Draw Cards
            Deck playedCards = new Deck(new ArrayList<>());

            for (Player player : players) {
                playedCards.addCard(player.getDeck().drawCard());
            }

            System.out.printf("%nPlayed Cards%n");
            System.out.printf("------------%n");
            System.out.println(playedCards);


            //Check for Round Winner
            int winner = 0;

            for (int i = 1; i < players.size(); i++) {

                if (playedCards.getCards().get(winner).compareTo(playedCards.getCards().get(i)) < 0) {
                    winner = i;
                }

            }

            //Place playedCards to the bottom of the winners deck
            for (int i = 0; i < playedCards.getCards().size(); i++) {
                int currIdx = (winner + i) % playedCards.getCards().size();
                players.get(winner).getDeck().addBottom(playedCards.getCards().get(currIdx));
            }

            System.out.printf("%nRound Winner : %s%n",
                    players.get(winner).getPlayerName());
            System.out.printf("Winning Card  : %s%n",
                    playedCards.getCards().get(winner));
            System.out.printf("Cards Owned  : %d%n",
                    players.get(winner).getDeck().getCards().size());


            System.out.printf("%nWinner's Deck%n");
            System.out.printf("-------------%n");
            System.out.println(players.get(winner).getDeck());


            //Remove players with no Cards
            for (int i = players.size() - 1; i >= 0; i--) {

                if (players.get(i).isDeckEmpty()) {
                    players.remove(i);
                }

            }

            System.out.printf("%nRemaining Players%n");
            System.out.printf("-----------------%n");
            for (Player player : players) {
                System.out.printf("%-10s : %2d cards%n",
                        player.getPlayerName(),
                        player.getDeck().getCards().size());
            }

            round++;
        }

        System.out.printf("%n============================================================%n");
        System.out.printf("GAME OVER%n");
        System.out.printf("============================================================%n");

        Player gameWinner = players.get(0);

        System.out.printf("Winner        : %s%n",
                gameWinner.getPlayerName());

        System.out.printf("Total Rounds  : %d%n",
                round);

        System.out.printf("Cards Owned   : %d%n%n",
                gameWinner.getDeck().getCards().size());

        System.out.printf("Final Deck%n");
        System.out.printf("----------%n");
        System.out.println(gameWinner.getDeck());

    }


}