package enums;

public enum Rank {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14);

    private final int value;

    Rank(int value) {
        this.value = value;
    }

    public static Rank fromString(String rank) {
        switch (rank) {
            case "2": return TWO;
            case "3": return THREE;
            case "4": return FOUR;
            case "5": return FIVE;
            case "6": return SIX;
            case "7": return SEVEN;
            case "8": return EIGHT;
            case "9": return NINE;
            case "10": return TEN;
            case "J": return JACK;
            case "Q": return QUEEN;
            case "K": return KING;
            case "A": return ACE;
            default:
                throw new IllegalArgumentException("Invalid rank: " + rank);
        }
    }

    public int getValue() {
        return value;
    }

}