package model;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> cards;

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

    public void shuffle() {

        List<Card> original = cards;
        List<Card> shuffled = new ArrayList<>();

        int middle = original.size() / 2;

        for (int i = 0; i < middle; i++) {
            shuffled.add(original.get(i));          // first half
            shuffled.add(original.get(i + middle)); // second half
        }

        cards = shuffled;
    }

    public Card drawLastCard() {

        if(cards.isEmpty())
            throw new IllegalStateException("Deck is empty");

        return cards.removeLast();
    }

    public Card drawFirstCard() {
        return cards.removeFirst();
    }

    public void addFirst(Card card) {
        cards.addFirst(card);
    }

    public void addLast(Card card) {
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
            sb.append(String.format("%-7s", cards.get(i)));

            if ((i + 1) % 13 == 0) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

}
