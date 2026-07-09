package model;

import java.util.ArrayList;

public class Deck {

    private ArrayList<Card> cards;

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void shuffle() {

        ArrayList<Card> original = cards;
        ArrayList<Card> shuffled = new ArrayList<>();

        int middle = original.size() / 2;

        for (int i = 0; i < middle; i++) {
            shuffled.add(original.get(i));          // first half
            shuffled.add(original.get(i + middle)); // second half
        }

        cards = shuffled;
    }

    public Card drawBottomCard() {

        if(cards.isEmpty())
            throw new IllegalStateException("Deck is empty");

        return cards.remove(cards.size() - 1);
    }

    public Card drawTopCard() {
        return cards.remove(0);
    }

    public void addBottom(Card card) {
        cards.add(0, card);
    }

    public void addCard(Card card) {
        cards.add(cards.size(), card);
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Card get(int index) {
        return cards.get(index);
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
