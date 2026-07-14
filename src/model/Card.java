package model;

import enums.Rank;
import enums.Suit;

public class Card implements Comparable<Card> {

    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getRankValue() {
        return this.rank.getValue();
    }

    public int getSuitValue() {
        return this.suit.getValue();
    }

    @Override
    public int compareTo(Card otherCard) {

        if(this.getRankValue() == otherCard.getRankValue()) {
            return Integer.compare(this.getSuitValue(), otherCard.getSuitValue());
        }

        return Integer.compare(this.getRankValue(), otherCard.getRankValue());
    }

    @Override
    public String toString() {
        return rank + "" + suit;
    }

}
