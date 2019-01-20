package io.github.teonistor.hc2k19.cards;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Arrays.stream;
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

    public static Hand bestHand(List<Card> cards) {
        if (cards.size() != 7) {
            System.out.printf("Strange! You're asking for the best hand out of %d%n", cards.size());
        }

        return stream(values())
            .filter(h -> h.matches(cards))
            .findFirst()
            .orElse(HighCard);
    }

    public boolean matches(List<Card> cards) {
        return matches.test(cards);
    }

    private static boolean matchesRoyalFlush(List<Card> cards) { // TODO no longer works with 7 and string bad
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
        return matchesFlush(cards) && matchesStraight(cards); // TODO no longer guaranteed with 7
    }

    private static boolean matchesFourOfAKind(List<Card> cards) {
        return cards.stream()
            .collect(groupingBy(Card::getKind))
            .values()
            .stream()
            .map(List::size)
            .anyMatch(i -> i==4);

    }

    private static boolean matchesFullHouse(List<Card> cards) {
        List<Integer> collect = cards.stream()
            .collect(groupingBy(Card::getKind))
            .values()
            .stream()
            .map(List::size)
            .sorted((a, b) -> Integer.compare(b, a))
            .collect(toList());
        return collect.size() >= 2
            && collect.get(0) >= 3
            && collect.get(1) >= 2;
    }

    private static boolean matchesFlush(List<Card> cards) {
        return cards.stream()
            .collect(groupingBy(Card::getSuit))
            .values()
            .stream()
            .mapToInt(List::size)
            .max()
            .orElse(0) >= 5;
    }

    private static boolean matchesStraight(List<Card> cards) {
        List<Integer> cardOrders = cards
            .stream()
            .map(Card::getKind)
            .map(Kind::getOrder)
            .sorted()
            .collect(toList());

        List<Boolean> consecutiveness = range(0,cardOrders.size()-1)
            .map(i -> cardOrders.get(i) - cardOrders.get(i+1))
            .mapToObj(i -> i==-1 || i==12) // 12 for A-2-... case
            .collect(toList());
        return range(0, consecutiveness.size() - 4)
            .mapToObj(i -> range(i, i + 4)
                .mapToObj(consecutiveness::get)
                .reduce(true, (a, b) -> a && b))
            .reduce(false, (a, b) -> a || b);
    }

    private static boolean matchesThreeOfAKind(List<Card> cards) {
        return cards.stream()
            .collect(groupingBy(Card::getKind))
            .values()
            .stream()
            .map(List::size)
            .anyMatch(i -> i >= 3);
    }

    private static boolean matchesTwoPairs(List<Card> cards) {
        return cards.stream()
            .collect(groupingBy(Card::getKind))
            .values()
            .stream()
            .map(List::size)
            .filter(i -> i >= 2)
            .count() >= 2L;
    }

    private static boolean matchesPair(List<Card> cards) {
        return cards.stream()
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
