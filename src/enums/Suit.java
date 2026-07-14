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
        return switch (suit) {
            case "D" -> DIAMONDS;
            case "H" -> HEARTS;
            case "S" -> SPADES;
            case "C" -> CLUBS;
            default -> throw new IllegalArgumentException("Invalid suit: " + suit);
        };
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
