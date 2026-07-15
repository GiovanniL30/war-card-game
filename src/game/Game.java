package game;

import model.Card;
import model.Deck;
import model.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Deck playingDeck;
    private final List<Player> players;
    private int round;

    public Game(Deck playingDeck, int playerCount) {
        this.playingDeck = playingDeck;
        this.players = createPlayers(playerCount);
        this.round = 0;
    }

    public Player startGame() {

        distributeCards();

        while (getActivePlayersSize() > 1) {
            startRound();
        }

        return getGameWinner();
    }

    public int getRound() {
        return round;
    }

    private void startRound() {
        round++;

        System.out.printf("%n============================================================%n");
        System.out.printf("ROUND %-3d%n", round);
        System.out.printf("============================================================%n");

        // Player Draw Cards
        Deck playedCards = new Deck(new ArrayList<>());

        for (Player player : players) {
            playedCards.addLast(player.getDeck().drawLastCard());
        }

        System.out.printf("%nPlayed Cards%n");
        System.out.printf("------------%n");
        System.out.println(playedCards);


        //Check for Round Winner
        int winner = 0;

        for (int i = 1; i < players.size(); i++) {

            if (playedCards.getCard(winner).compareTo(playedCards.getCard(i)) < 0) {
                winner = i;
            }

        }

        //Place playedCards to the bottom of the winners deck
        for (int i = 0; i < playedCards.cardsCount(); i++) {
            int currIdx = (winner + i) % playedCards.cardsCount();
            players.get(winner).getDeck().addFirst(playedCards.getCard(currIdx));
        }

        System.out.printf("%nRound Winner : %s%n", players.get(winner).getPlayerName());
        System.out.printf("Winning Card  : %s%n", playedCards.getCard(winner));
        System.out.printf("Cards Owned  : %d%n", players.get(winner).getDeck().cardsCount());

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
            System.out.printf("%-10s : %2d cards%n", player.getPlayerName(), player.getDeck().cardsCount());
        }
    }

    private void distributeCards() {
        int currentIdx = 0;

        while (!playingDeck.isEmpty()) {
            Card card = playingDeck.drawFirstCard();
            players.get(currentIdx).getDeck().addLast(card);
            currentIdx = (currentIdx + 1) % players.size();
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

    private int getActivePlayersSize() {
        return players.size();
    }

    private List<Player> createPlayers(int playerCount) {
        List<Player> players = new ArrayList<>(playerCount);

        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player("Player " + i, new Deck(new ArrayList<>())));
        }

        return players;
    }

}
