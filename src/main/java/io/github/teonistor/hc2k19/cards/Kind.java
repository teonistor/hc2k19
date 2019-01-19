package io.github.teonistor.hc2k19.cards;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public enum Kind {
    Two("2", 2),
    Three("3", 3),
    Four("4", 4),
    Five("5", 5),
    Six("6", 6),
    Seven("7", 7),
    Eight("8", 8),
    Nine("9", 9),
    Ten("10", 10),
    Jack("J", 11),
    Queen("Q", 12),
    King("K", 13),
    Ace("A", 14);

    private final String shortString;
    private final int order;

    Kind(String shortString, int order) {
        this.shortString = shortString;
        this.order = order;
    }

    public String getShortString() {
        return shortString;
    }

    public int getOrder() {
        return order;
    }

    public static boolean areConsecutive(Kind... kinds) {
        Set<Integer> orders = stream(kinds)
            .map(Kind::getOrder)
            .collect(Collectors.toSet());
        // TODO
    }
}
