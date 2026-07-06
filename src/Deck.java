import java.util.ArrayList;

public class Deck {

    private final ArrayList<Card> cards;

    public Deck (ArrayList<Card> cards) {
        this.cards = cards;
    }

    public static Deck shuffleDeck(Deck givenDeck) {

        ArrayList<Card> original = givenDeck.cards;
        ArrayList<Card> shuffled = new ArrayList<>();

        int middle = original.size() / 2;

        for (int i = 0; i < middle; i++) {
            shuffled.add(original.get(i));          // first half
            shuffled.add(original.get(i + middle)); // second half
        }

        return new Deck(shuffled);
    }

    public Card drawTopCard () {
        return cards.remove(0);
    }

    public void addLast (Card card) {
        cards.add(cards.size(), card);
    }

    public void addFirst (Card card) {
        cards.add(0, card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < cards.size(); i++) {
            sb.append(String.format("%-24s", cards.get(i)));

            if ((i + 1) % 13 == 0) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

}
