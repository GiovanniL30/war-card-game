public enum Suit {
    CLUBS(1),
    SPADES(2),
    HEARTS(3),
    DIAMONDS(4);

    private final int value;

    Suit(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
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
}
