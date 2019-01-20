package io.github.teonistor.hc2k19.cards;

public enum Suit {
    Clubs ("♣"),
    Diamonds ("♦"),
    Hearts ("♥"),
    Spades ("♠");

    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
