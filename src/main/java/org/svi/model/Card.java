package org.svi.model;

import org.svi.enums.Rank;
import org.svi.enums.Suit;

public class Card {

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

    public String getRankCode() {
        return this.rank.getCode();
    }

    public String getSuitCode() {
        return this.suit.getCode();
    }


    public boolean isOtherCardHigher(Card otherCard) {
        if (this.getRankValue() == otherCard.getRankValue()) {
            return otherCard.getSuitValue() > this.getSuitValue();
        }

        return otherCard.getRankValue() > this.getRankValue();
    }

    @Override
    public String toString() {
        return rank + "-" + suit;
    }

}
