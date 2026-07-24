package enums;

public enum Suit {
    CLUBS(1,"C", "♣", "clubs"),
    SPADES(2,"S", "♠", "spades"),
    HEARTS(3, "H","♥", "hearts"),
    DIAMONDS(4, "D","♦", "diamonds");

    private final int value;
    private final String code;
    private final String symbol;
    private final String suitWord;

    Suit(int value, String code, String symbol, String suitWord) {
        this.value = value;
        this.code = code;
        this.symbol = symbol;
        this.suitWord = suitWord;
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

    public String getSuitWord() {
        return suitWord;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
