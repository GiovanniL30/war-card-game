package org.svi.enums;

public enum Rank {
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(11, "J"),
    QUEEN(12, "Q"),
    KING(13, "K"),
    ACE(14, "A");

    private final int value;
    private final String code;

    Rank(int value, String code) {
        this.value = value;
        this.code = code;
    }

    public static Rank fromString(String code) {
        return switch (code) {
            case "2" -> TWO;
            case "3" -> THREE;
            case "4" -> FOUR;
            case "5" -> FIVE;
            case "6" -> SIX;
            case "7" -> SEVEN;
            case "8" -> EIGHT;
            case "9" -> NINE;
            case "10" -> TEN;
            case "J" -> JACK;
            case "Q" -> QUEEN;
            case "K" -> KING;
            case "A" -> ACE;
            default -> throw new IllegalArgumentException("Invalid rank code: " + code);
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
        return code;
    }

}