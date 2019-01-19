package io.github.teonistor.hc2k19.cards;

public enum Hand {
    RoyalFlush(4324L, 0.0032, 0.0032, "30,939 : 1"),
    StraightFlush(37260L, 0.0279, 0.0311, "3,589.6 : 1"),
    FourOfAKind(224848L, 0.168, 0.199, "594 : 1"),
    FullHouse(3473184L, 2.60, 2.80, "37.5 : 1"),
    Flush(4047644L, 3.03, 5.82, "32.1 : 1"),
    Straight(6180020L, 4.62, 10.4, "20.6 : 1"),
    ThreeOfAKind(6461620L, 4.83, 15.3, "19.7 : 1"),
    TwoPairs(31433400L, 23.5, 38.8, "3.26 : 1"),
    Pair(58627800L, 43.8, 82.6, "1.28 : 1"),
    HighCard(23294460L, 17.4, 100, "4.74 : 1");

    private final long frequency;
    private final double probability, cumulativeProbability;
    private final String odds;

    Hand(long frequency, double probability, double cumulativeProbability, String odds) {
        this.frequency = frequency;
        this.probability = probability;
        this.cumulativeProbability = cumulativeProbability;
        this.odds = odds;
    }
}
