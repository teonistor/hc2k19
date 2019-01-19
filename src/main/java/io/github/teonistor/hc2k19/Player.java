package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.cards.Card;
import io.github.teonistor.hc2k19.game.Game;

public interface Player {
    public void deal(Card a, Card b);
    public void reveal(Card c);
    public void announce (Player other, Action action);
    public void takeTurn (Game state);
}
