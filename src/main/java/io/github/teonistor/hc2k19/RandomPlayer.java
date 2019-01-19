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
    public void deal(Card a, Card b) {
        System.out.printf("%s was dealt %s and %s%n", name, a, b);
    }

    @Override
    public void reveal(Card c) {

    }

    @Override
    public void announce(Player other, Action action) {

    }

    @Override
    public void remind(int dolla) {
        System.out.printf("%s has %s dolla%n", name, dolla);
    }

    @Override
    public Action takeTurn() {
        return Action.values()[random.nextInt(Action.values().length)];
    }

    @Override public String toString(){
        return name;
    }
}
