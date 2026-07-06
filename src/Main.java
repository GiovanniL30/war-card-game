import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    public static Deck readInitialDeck() {

        String filePath = "src/input.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line = reader.readLine();
            String[] cards = line.split(",");

            if (cards.length != 52) {
                throw new RuntimeException("Invalid Cards Length, Expected 52, Given " + cards.length);
            }

            Deck playingDeck = new Deck(new ArrayList<>());

            for (String cardInput : cards) {
                String[] card = cardInput.split("-");

                if (card.length != 2) {
                    throw new RuntimeException("Invalid Card Detected: " + Arrays.toString(card));
                }

                String suit = card[0];
                String rank = card[1];

                playingDeck.addLast(new Card(Suit.fromString(suit), Rank.fromString(rank)));

            }

            return playingDeck;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void distributeCards(Deck playingDeck, ArrayList<Player> players) {

        int currentIdx = 0;

        while (!playingDeck.getCards().isEmpty()) {

            Card card = playingDeck.drawTopCard();
            players.get(currentIdx).getDeck().addLast(card);
            currentIdx = (currentIdx + 1) % players.size();
        }

    }

    public static void main(String[] args) {

        Deck playingDeck = readInitialDeck();

        System.out.printf("%n========== Initial Playing Deck ==========%n");
        System.out.println(playingDeck);

        Scanner scanner = new Scanner(System.in);
        int shuffleCount = 0;

        while (shuffleCount < 1 || shuffleCount > 100) {
            System.out.print("\n Enter Desired Shuffle Count (1 - 100): ");
            String input = scanner.nextLine();

            try {
                shuffleCount = Integer.parseInt(input);

                if (shuffleCount > 100 || shuffleCount < 1) {
                    System.out.println("Please Enter values between 1-100");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid Input, please enter a number only.");
            }

        }


        while (shuffleCount > 0) {
            playingDeck.shuffle();
            shuffleCount -= 1;
        }

        System.out.printf("%n========== Deck after Shuffle ==========%n");
        System.out.println(playingDeck);

        int numberOfPlayers = 0;

        while (numberOfPlayers < 2 || numberOfPlayers > 8) {
            System.out.print("\n Enter Number of Players (2 - 8): ");
            String input = scanner.nextLine();

            try {
                numberOfPlayers = Integer.parseInt(input);

                if (numberOfPlayers < 2 || numberOfPlayers > 8) {
                    System.out.println("Please Enter values between 2-8");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid Input, please enter a number only.");
            }
        }

        ArrayList<Player> players = new ArrayList<>(numberOfPlayers);

        for (int i = 1; i <= numberOfPlayers; i++) {
            players.add(new Player("Player" + i, new Deck(new ArrayList<>())));
        }

        distributeCards(playingDeck, players);

        int round = 1;
        System.out.println(players);

        while (players.size() > 1) {

            System.out.printf("Round %d", round);

            Deck playedCards = new Deck(new ArrayList<>());

            for (Player player : players) {
                playedCards.addLast(player.getDeck().drawTopCard());
            }

            System.out.println(playedCards);

            int winner = 0;

            for (int i = 1; i < players.size(); i++) {

                if (playedCards.getCards().get(winner).compareTo(playedCards.getCards().get(i)) < 0) {
                    winner = i;
                }

            }

            for (int i = 0; i < playedCards.getCards().size(); i++) {
                int currIdx = (winner + i) % playedCards.getCards().size();
                players.get(winner).getDeck().addLast(playedCards.getCards().get(currIdx));
            }

            System.out.println("\nRound Winner " + players.get(winner));

            for (int i = players.size() - 1; i >= 0; i--) {

                if (players.get(i).getDeck().getCards().isEmpty()) {
                    players.remove(i);
                }

            }

            round++;
        }

        System.out.println("Winner " + players.get(0));
        System.out.println("Total Rounds" + round);

    }


}