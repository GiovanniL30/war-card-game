import java.util.ArrayList;

public class Game {
    private final Deck playingDeck;
    private final ArrayList<Player> players;
    private int round;

    public Game(Deck playingDeck, int playerCount) {
        this.playingDeck = playingDeck;

        players = new ArrayList<>(playerCount);

        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player("Player " + i, new Deck(new ArrayList<>())));
        }

        round = 0;
    }

    public void startRound() {
        round++;

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

        System.out.printf("%nRound Winner : %s%n", players.get(winner).getPlayerName());
        System.out.printf("Winning Card  : %s%n", playedCards.getCards().get(winner));
        System.out.printf("Cards Owned  : %d%n", players.get(winner).getDeck().getCards().size());

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
            System.out.printf("%-10s : %2d cards%n", player.getPlayerName(), player.getDeck().getCards().size());
        }
    }

    public void distributeCards() {
        int currentIdx = 0;

        while (!playingDeck.getCards().isEmpty()) {
            Card card = playingDeck.drawCard();
            players.get(currentIdx).getDeck().addCard(card);
            currentIdx = (currentIdx + 1) % players.size();
        }

        System.out.printf("%n============================================================%n");
        System.out.printf("INITIAL PLAYER DECKS%n");
        System.out.printf("============================================================%n\n");

        for (Player player : players) {
            System.out.println(player + "\n");
        }
    }

    public int getRound() {
        return round;
    }

    public int getActivePlayersSize() {
        return players.size();
    }

    public Player getGameWinner() {
        if (players.size() > 1) {
            System.out.println("There is no game winner yet");
            return null;
        }

        return players.get(0);
    }



}
