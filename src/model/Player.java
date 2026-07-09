package model;

public class Player {

    private final String playerName;
    private final Deck deck;

    public Player(String playerName, Deck deck) {
        this.playerName = playerName;
        this.deck = deck;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Deck getDeck() {
        return deck;
    }

    public boolean isDeckEmpty() {
        return deck.isEmpty();
    }

    @Override
    public String toString() {
        return playerName + "'s Deck\n" +
                "----------------------------------------\n" +
                deck;
    }
}
