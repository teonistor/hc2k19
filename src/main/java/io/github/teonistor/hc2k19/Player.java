package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.cards.Card;

import java.util.Map;

public interface Player {
    public void beginPlay();
    public void deal(Card c);
    public void reveal(Card c);
    public void announce (Player other, BidAction action);
    public void announce (Map<Player, Integer> dolla, int bid, int pot);
    public void endPlay(int dolla);
    public BidAction takeTurn ();
}
