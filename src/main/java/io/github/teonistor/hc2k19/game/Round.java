package io.github.teonistor.hc2k19.game;

import io.github.teonistor.hc2k19.Player;
import io.github.teonistor.hc2k19.cards.Card;

import java.util.function.Supplier;

import static java.util.stream.IntStream.range;

public enum Round {
    Initial, Flop, Turn, River;

    public void preBid(Supplier<Card> deck, Player[] players) {
        switch (this) {
            case Initial:
                for (Player p : players) {
                    p.deal(deck.get(), deck.get());
                }
                break;
            case Flop:
                range(0,3).forEach(ignore -> {
                    final Card card = deck.get();
                    for (Player p : players) {
                        p.reveal(card);
                    }
                    // TODO Let the game know what card was revealed
                });
                break;
            default:
                final Card card = deck.get();
                for (Player p : players) {
                    p.reveal(card);
                }
                // TODO Let the game know what card was revealed
        }
    }

    public void postBid() {
        switch (this){
            case River:
                // TODO handle game end


                break;

            default:
                // Nothing.
        }
    }
}
