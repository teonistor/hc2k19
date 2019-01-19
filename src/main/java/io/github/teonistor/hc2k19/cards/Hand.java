package io.github.teonistor.hc2k19.cards;

import com.google.common.collect.ImmutableSet;

import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.range;

public enum Hand {
    RoyalFlush(4324L, 0.0032, 0.0032, "30,939 : 1", Hand::matchesRoyalFlush),
    StraightFlush(37260L, 0.0279, 0.0311, "3,589.6 : 1", Hand::matchesStraightFlush),
    FourOfAKind(224848L, 0.168, 0.199, "594 : 1", Hand::matchesFourOfAKind),
    FullHouse(3473184L, 2.60, 2.80, "37.5 : 1", Hand::matchesFullHouse),
    Flush(4047644L, 3.03, 5.82, "32.1 : 1", Hand::matchesFlush),
    Straight(6180020L, 4.62, 10.4, "20.6 : 1", Hand::matchesStraight),
    ThreeOfAKind(6461620L, 4.83, 15.3, "19.7 : 1", Hand::matchesThreeOfAKind),
    TwoPairs(31433400L, 23.5, 38.8, "3.26 : 1", Hand::matchesTwoPairs),
    Pair(58627800L, 43.8, 82.6, "1.28 : 1", Hand::matchesPair),
    HighCard(23294460L, 17.4, 100, "4.74 : 1", Hand::matchesHighCard);

    private final long frequency;
    private final double probability, cumulativeProbability;
    private final String odds;
    private final Predicate<List<Card>> matches;

    Hand(long frequency, double probability, double cumulativeProbability, String odds, Predicate<List<Card>> matches) {
        this.frequency = frequency;
        this.probability = probability;
        this.cumulativeProbability = cumulativeProbability;
        this.odds = odds;
        this.matches = matches;
    }

    public boolean matches(List<Card> cards) {
        return matches.test(cards);
    }

    private static boolean matchesRoyalFlush(List<Card> cards) {
        return matchesFlush(cards)
                &&

                cards.stream()
                        .map(Card::getKind)
                        .sorted()
                        .map(Kind::getShortString)
                        .reduce(String::concat)
                        .orElse("")
                        .equals("10JQKA");
    }

    private static boolean matchesStraightFlush(List<Card> cards) {
        return matchesFlush(cards) && matchesStraight(cards);
    }

    private static boolean matchesFourOfAKind(List<Card> cards) {
        return cards.size() == 5
                &&

            cards.stream()
                .collect(groupingBy(Card::getKind))
                .values()
                .stream()
                .map(List::size)
                .anyMatch(i -> i==4);

    }

    private static boolean matchesFullHouse(List<Card> cards) {
        return cards.size() == 5
                &&

            cards.stream()
                .collect(groupingBy(Card::getKind))
                .values()
                .stream()
                .map(List::size)
                .collect(toSet())
                .equals(ImmutableSet.of(2, 3));
    }

    private static boolean matchesFlush(List<Card> cards) {
        return cards.size() == 5
                &&

                cards.stream()
                        .map(Card::getSuit)
                        .distinct()
                        .count()==1L;
    }

    private static boolean matchesStraight(List<Card> cards) {
        if (cards.size() != 5) return false;

        List<Integer> cardOrders = cards
            .stream()
            .map(Card::getKind)
            .map(Kind::getOrder)
            .collect(toList());

        return range(0,4)
            .map(i -> cardOrders.get(i) - cardOrders.get(i+1))
            .allMatch(i -> i==-1 || i==12); // 12 for A-2-... case
    }

    private static boolean matchesThreeOfAKind(List<Card> cards) {
        return cards.size() == 5
                &&

            cards.stream()
                .collect(groupingBy(Card::getKind))
                .values()
                .stream()
                .map(List::size)
                .anyMatch(i -> i >= 3);
    }

    private static boolean matchesTwoPairs(List<Card> cards) {
        return cards.size() == 5
                &&

            cards.stream()
                .collect(groupingBy(Card::getKind))
                .values()
                .stream()
                .map(List::size)
                .filter(i -> i >= 2)
                .count() == 2L;
    }

    private static boolean matchesPair(List<Card> cards) {
        return cards.size() == 5
                &&

            cards.stream()
                .collect(groupingBy(Card::getKind))
                .values()
                .stream()
                .map(List::size)
                .filter(i -> i >= 2)
                .count() >= 1L;
    }

    private static boolean matchesHighCard(List<Card> cards) {
        return true;
    }
}
