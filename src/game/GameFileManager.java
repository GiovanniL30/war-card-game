package game;

import enums.Rank;
import enums.Suit;
import model.Card;
import model.Deck;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


/**
 * Handles saving and loading a deck of cards from text files.
 */
public class GameFileManager {

    private final String BASE_PATH = "files/";
    private final List<Path> filePaths;

    public GameFileManager() {
        filePaths = getFolderFilePaths();
    }

    /**
     * Saves the winner's deck to a text file.
     * Each card is written in the format Suit-Rank (e.g. H-A).
     */
    public void saveDeckToFile(Deck deck) {

        String fileName = BASE_PATH + "game" + (filePaths.size() + 1);

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

            System.out.printf("Winners Deck Saved Successfully to '%s'%n",fileName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Reads a deck from a file and validates its
     */
    public Deck readAndInitializeDeck() {

        System.out.println("========================================");
        System.out.println("         Available Input Files");
        System.out.println("========================================");
        System.out.println("Files are located in the 'files' directory.");
        System.out.println("Enter only the file name (e.g., input.txt).\n");

        System.out.printf("Found %d file(s):%n", filePaths.size());

        for (int i = 0; i < filePaths.size(); i++) {
            System.out.printf("  %2d. %s%n", i + 1, filePaths.get(i).getFileName());
        }

        System.out.println("========================================");

        String fileName = BASE_PATH + askFilePath();

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

    private String askFilePath() {
        Scanner sc = new Scanner(System.in);
        String userInput;

        do {
            System.out.print("Enter a file name for the cards to be loaded" + ": ");
            userInput = sc.nextLine().trim();

            if(userInput.length() < 2) {
                System.out.println("Please enter at least 2 characters");
            }

        } while (userInput.length() < 2);

        return userInput;
    }

    private List<Path> getFolderFilePaths() {

        try(Stream<Path> fileStream =  Files.list(Paths.get(BASE_PATH))) {
            return fileStream.filter(Files::isRegularFile).toList();
        }catch (IOException e) {
            System.out.println("Failed to load folder: " +  e.getMessage());
            return null;
        }

    }
}
