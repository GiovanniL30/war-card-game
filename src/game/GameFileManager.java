package game;

import enums.Rank;
import enums.Suit;
import model.Card;
import model.Deck;

import java.io.*;
import java.util.*;


/**
 * Handles saving and loading a deck of cards from text files.
 */
public class GameFileManager {

    private final static String BASE_PATH = "src/files/";

    /**
     * Saves the winner's deck to a text file.
     * Each card is written in the format Suit-Rank (e.g. H-A).
     */
    public static void saveDeckToFile(Deck deck) {

        String name = "";

        do {
             name = askFilePath("\nEnter output file name");

             if(name.equals("input.txt")) {
                 System.out.println("You cannot override input.txt file, please enter different file name");
             }

        }while (name.equals("input.txt"));


        String fileName = BASE_PATH + name;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            for (int i = 0; i < deck.cardsCount(); i++) {

                Card currCard = deck.getCard(i);
                boolean isLastItem = i == deck.cardsCount() - 1;

                writer.append(String.format("%s-%s%s",
                        currCard.getSuitCode(),
                        currCard.getRankCode(),
                        isLastItem ? "" : ","
                ));
            }

            System.out.println("Winners Deck Saved Successfully");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Reads a deck from a file and validates its
     */
    public static Deck readAndInitializeDeck() {

        System.out.println("\nFiles are located under src/files path, you can just enter the file name (eg. input.txt)");
        String fileName = BASE_PATH + askFilePath("Enter a file name for the card to be loaded");

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line = reader.readLine();

            if (line == null || line.trim().isEmpty()) {
                throw new RuntimeException("Input file is empty.");
            }

            String[] cardsStr = line.split(",");

            Deck playingDeck = new Deck(new ArrayList<>());
            Set<String> seenCards = new HashSet<>();

            for (String s : cardsStr) {
                String[] currCardStr = s.trim().split("-");

                if (currCardStr.length != 2) {
                    throw new RuntimeException("Invalid Card Detected: " + Arrays.toString(currCardStr));
                }

                String suitStr = currCardStr[0];
                String rankStr = currCardStr[1];

                Suit suit = Suit.fromString(suitStr);
                Rank rank = Rank.fromString(rankStr);

                String key = suit + "-" + rank;

                if (!seenCards.add(key)) {
                    throw new RuntimeException("Duplicate Card Detected: " + key);
                }

                playingDeck.addLast(new Card(suit, rank));
            }


            if (playingDeck.cardsCount() != 52) {
                throw new RuntimeException("Invalid Cards Length: Expected " + 52 + ", Given " + playingDeck.cardsCount());
            }

            System.out.println("\nPlaying Deck initialized Successfully!");
            return playingDeck;

        } catch (RuntimeException | IOException e) {
            System.out.println("Failed to load cards: " + e.getMessage());
            return null;
        }
    }

    private static String askFilePath(String message) {
        Scanner sc = new Scanner(System.in);
        String userInput;

        do {
            System.out.print(message + ": ");
            userInput = sc.nextLine().trim();

            if(userInput.length() < 2) {
                System.out.println("Please enter at least 2 characters");
            }

        } while (userInput.length() < 2);

        return userInput;
    }

}
