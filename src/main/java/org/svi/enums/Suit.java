package org.svi.enums;

public enum Suit {
    CLUBS(1,"C"),
    SPADES(2,"S"),
    HEARTS(3, "H"),
    DIAMONDS(4, "D");

    private final int value;
    private final String code;

    Suit(int value, String code) {
        this.value = value;
        this.code = code;
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
        return getCode();
    }
}
