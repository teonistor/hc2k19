package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.cards.Card;

import java.util.Random;

public class RandomPlayer implements Player {

    private final String name;
    private final Random random;

    public RandomPlayer(String name) {
        this.name = name;
        random = new Random();
    }

    @Override
    public void deal(Card c) {
        System.out.printf("%s was dealt %s%n", name, c);
    }

    @Override
    public void reveal(Card c) {

    }

    @Override
    public void announce(Player other, BidAction action) {

    }

    @Override
    public void remind(int dolla) {
        System.out.printf("%s has %s dolla%n", name, dolla);
    }

    @Override
    public BidAction takeTurn() {
        return BidAction.values()[random.nextInt(BidAction.values().length)];
    }

    @Override public String toString(){
        return name;
    }
}
