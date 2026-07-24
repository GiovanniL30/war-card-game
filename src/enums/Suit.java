package enums;

public enum Suit {
    CLUBS(1,"C", "♣"),
    SPADES(2,"S", "♠"),
    HEARTS(3, "H","♥"),
    DIAMONDS(4, "D","♦");

    private final int value;
    private final String code;
    private final String symbol;

    Suit(int value, String code, String symbol) {
        this.value = value;
        this.code = code;
        this.symbol = symbol;
    }

    public static Suit fromString(String code) {
        return switch (code) {
            case "D" -> DIAMONDS;
            case "H" -> HEARTS;
            case "S" -> SPADES;
            case "C" -> CLUBS;
            default -> throw new IllegalArgumentException("Invalid suit code: " + code);
        };
    }

    public int getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
