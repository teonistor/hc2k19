package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.cards.Card;

public interface Player {
    public void deal(Card a, Card b);
    public void announce (Player other, Action action);
    public void takeTurn (GameState state);
}
