package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.cards.Card;

import java.util.Map;
import java.util.Random;
import static io.github.teonistor.hc2k19.BidAction.*;

public class RandomPlayer implements Player {

    private final String name;
    private final Random random;

    public RandomPlayer(String name) {
        this.name = name;
        random = new Random();
    }

    @Override
    public void beginPlay() {

    }

    @Override
    public void deal(Card c) {
        System.out.printf("%s was dealt %s%n", name, c);
    }

    @Override
    public void reveal(Card c) {

    }

    @Override
    public void announce(Map<Player, Integer> dolla, int bid, int pot, Player other, BidAction action) {

    }

    @Override
    public BidAction takeTurn() {
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int r = random.nextInt(20);
        if (r < 1) return Fold;
        if (r > 15) return Raise;
        return Call;
    }

    @Override public String toString(){
        return name;
    }
}
