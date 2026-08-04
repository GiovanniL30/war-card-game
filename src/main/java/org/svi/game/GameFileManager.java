package org.svi.game;

import org.svi.enums.Rank;
import org.svi.enums.Suit;
import org.svi.model.Card;
import org.svi.model.Deck;

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

    /**
     * Creating a new instance of this class checks all available files in the "files" folder
     * */
    public GameFileManager() {
        filePaths = getFolderFilePaths();
    }

    /**
     * Saves the winner's deck to a text file.
     * Each card is written in the format Suit-Rank (e.g. H-A).
     */
    public void saveDeckToFile(Deck deck) {
        try {
            Path directory = Paths.get(BASE_PATH);
            Files.createDirectories(directory);

            String fileName = directory.resolve("game" + (filePaths.size() + 1) + ".txt").toString();

            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {

                for (int i = 0; i < deck.cardsCount(); i++) {

                    Card currCard = deck.getCard(i);
                    boolean isLastItem = i == deck.cardsCount() - 1;

                    writer.write(String.format("%s-%s%s",
                            currCard.getSuitCode(),
                            currCard.getRankCode(),
                            isLastItem ? "" : ","));
                }
            }
            System.out.printf("Winner's deck saved successfully to '%s'%n", fileName);
        } catch (IOException e) {
            System.err.println("Failed to save deck: " + e.getMessage());
        }
    }

    /**
     * Reads a deck from a file and validates its
     */
    public Deck readAndInitializeDeck() {

        System.out.println("\n========================================");
        System.out.println("         Available Input Files");
        System.out.println("========================================");

        for (int i = 0; i < filePaths.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, filePaths.get(i).getFileName());
        }

        System.out.println("========================================");
        String fileName = askFileName();

        // try to parse number input
        try {
            int fileNumber = Integer.parseInt(fileName);
            fileName = filePaths.get(fileNumber - 1).getFileName().toString();
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\nFile number not found");
            return null;
        } catch (NumberFormatException _){}

        try (BufferedReader reader = new BufferedReader(new FileReader(BASE_PATH + fileName))) {

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
            System.out.println("\nFailed to load cards: " + e.getMessage());
            return null;
        }
    }

    public int getAvailableFilesLength() {
        return filePaths.size();
    }

    private String askFileName() {
        Scanner sc = new Scanner(System.in);
        String userInput;

        do {
            System.out.print("Enter the file number or filename for the cards to be loaded" + ": ");
            userInput = sc.nextLine().trim();

            if(userInput.isEmpty()) {
                System.out.println("Please enter at least 1 character");
            }

        } while (userInput.isEmpty());

        return userInput;
    }

    private List<Path> getFolderFilePaths() {
        try(Stream<Path> fileStream =  Files.list(Paths.get(BASE_PATH))) {
            return fileStream.filter(Files::isRegularFile).toList();
        }catch (IOException e) {
            return new ArrayList<>();
        }

    }
}
