package io.github.teonistor.hc2k19.game;

import io.github.teonistor.hc2k19.Player;
import io.github.teonistor.hc2k19.RandomPlayer;
import io.github.teonistor.hc2k19.cards.Card;

import java.util.Arrays;
import java.util.function.Supplier;

public class Game {

    private final Player[] players;
    private final Supplier<Card> deck;

    public Game (final int playerCount) {
        players = new Player[playerCount];
        Arrays.setAll(players, i -> new RandomPlayer("Player " + i));
        deck = Card.getShuffledDeck();
    }

    public void play() {
        System.out.println("Begin.");

        for (Round round : Round.values()) {
            round.preBid(deck, players);
            for (Player p : players) {
                // TODO Correct order
                p.takeTurn();
            }
            round.postBid();
        }
    }


}
