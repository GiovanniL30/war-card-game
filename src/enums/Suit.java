package enums;

public enum Suit {
    CLUBS(1, "♣"),
    SPADES(2, "♠"),
    HEARTS(3, "♥"),
    DIAMONDS(4, "♦");

    private final int value;
    private final String symbol;

    Suit(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public static Suit fromString(String suit) {
        switch (suit) {
            case "D": return DIAMONDS;
            case "H": return HEARTS;
            case "S": return SPADES;
            case "C": return CLUBS;
            default:
                throw new IllegalArgumentException("Invalid suit: " + suit);
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
