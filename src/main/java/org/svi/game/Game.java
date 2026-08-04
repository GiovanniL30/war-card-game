package org.svi.game;

import org.svi.model.Card;
import org.svi.model.Deck;
import org.svi.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages the flow of the card Game.
 * Handles card distribution, rounds, and determines the winner.
 */
public class Game {
    private final Scanner sc = new Scanner(System.in);
    private final Deck playingDeck;
    private final List<Player> players;
    private int round;

    public Game(Deck playingDeck, int playerCount) {
        this.playingDeck = playingDeck;
        playingDeck.flipDeck(); // mimics the face down of the whole deck

        this.players = createPlayers(playerCount);
        this.round = 1;
    }

    public Player startGame() {

        boolean isAutoPlay = false;
        distributeCards();

        System.out.print("\nPress Enter to start the Game: ");
        sc.nextLine();

        while (players.size() > 1) {
            startRound();

            if(!isAutoPlay) {
                System.out.print("\nPress Enter to play the next round, or type 'auto' to enable auto-play: ");
                String input = sc.nextLine();
                if(input.trim().equals("auto")) isAutoPlay = true;
            }

        }

        return getGameWinner();
    }

    private void startRound() {
        System.out.printf("%n============================================================%n");
        System.out.printf("ROUND %-3d%n", round);
        System.out.printf("============================================================%n");

        Deck playedCards = drawCards();
        int roundWinnerIdx = getRoundWinnerIdx(playedCards);
        winnerCollectPlayedCards(playedCards, roundWinnerIdx);

        System.out.printf("%nRound Winner : %s%n", players.get(roundWinnerIdx).getPlayerName());
        System.out.printf("Winning Card  : %s%n", playedCards.getCard(roundWinnerIdx));
        System.out.printf("Cards Owned  : %d%n", players.get(roundWinnerIdx).getDeck().cardsCount());

        System.out.printf("%nWinner's Deck%n");
        System.out.printf("-------------%n");
        System.out.println(players.get(roundWinnerIdx).getDeck());

        eliminatePlayers();

        System.out.printf("%nRemaining Players%n");
        System.out.printf("-----------------%n");
        for (Player player : players) {
            System.out.printf("%-10s : %2d cards%n", player.getPlayerName(), player.getDeck().cardsCount());
        }

        round++;
    }

    public int getRound() {
        return round;
    }

    /**
     * Returns index of the player with the highest played card
     * */
    private int getRoundWinnerIdx (Deck playedCards) {
        int roundWinnerIdx = 0;
        for (int i = 1; i < players.size(); i++) {
            if (playedCards.getCard(roundWinnerIdx).isOtherCardHigher(playedCards.getCard(i))) {
                roundWinnerIdx = i;
            }
        }
        return roundWinnerIdx;
    }

    /**
     * Round-robin card collection starting from the winners index
     * */
    private void winnerCollectPlayedCards (Deck playedCards, int roundWinnerIdx ) {
        //Place playedCards to the bottom of the winners deck
        for (int i = 0; i < playedCards.cardsCount(); i++) {
            int currIdx = (roundWinnerIdx + i) % playedCards.cardsCount(); // round-robin computation
            players.get(roundWinnerIdx).getDeck().addFirst(playedCards.getCard(currIdx));
        }
    }

    /**
     * Returns a played cards deck
     * */
    private Deck drawCards() {
        Deck playedCards = new Deck(new ArrayList<>());

        for (Player player : players) {
            playedCards.addLast(player.getDeck().drawLastCard());
        }

        System.out.printf("%nPlayed Cards%n");
        System.out.printf("------------%n");
        printPlayedCards(playedCards);

        return  playedCards;
    }

    /**
     * Remove players with no Cards
     * */
    private void eliminatePlayers() {
        for (int i = players.size() - 1; i >= 0; i--) {
            Player cPlayer = players.get(i);
            if (cPlayer.isDeckEmpty()) {
                players.remove(i);
                System.out.printf("%s eliminated%n", cPlayer.getPlayerName());
            }
        }
    }

    /**
     * Round-robin card distribution until the deck becomes empty
     * */
    private void distributeCards() {
        int currentIdx = 0;

        while (!playingDeck.isEmpty()) {
            Card card = playingDeck.drawLastCard();
            players.get(currentIdx).getDeck().addLast(card);
            currentIdx = (currentIdx + 1) % players.size(); // round-robin computation
        }

        System.out.printf("%n============================================================%n");
        System.out.printf("INITIAL PLAYER DECKS%n");
        System.out.printf("============================================================%n\n");

        for (Player player : players) {
            System.out.println(player + "\n");
        }
    }

    private Player getGameWinner() {
        return players.size() == 1 ? players.getFirst() : null;
    }

    private List<Player> createPlayers(int playerCount) {
        List<Player> players = new ArrayList<>(playerCount);

        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player("Player " + i, new Deck(new ArrayList<>())));
        }

        return players;
    }

    private void printPlayedCards(Deck deck) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < deck.cardsCount(); i++) {
            sb.append(String.format("%s = %-5s ", players.get(i).getPlayerName(), deck.getCard(i)));

            if(( (i + 1) % 5) == 0) {
                sb.append("\n");
            }

        }
        System.out.println(sb);
    }

}
