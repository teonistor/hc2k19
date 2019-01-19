package io.github.teonistor.hc2k19.game;

import io.github.teonistor.hc2k19.Player;
import io.github.teonistor.hc2k19.cards.Card;
import java.util.function.Supplier;

public class Game {

    void what() {
        System.out.println("Begin.");
        Player[] players = new Player[2]; // TODO instantiate players

        Supplier<Card> deck = Card.getShuffledDeck();

        for (Round round : Round.values()) {
            round.preBid(deck, players);
            for (Player p : players) {
                // TODO Correct order
                p.takeTurn(this);
            }
            round.postBid();
        }
    }


}
