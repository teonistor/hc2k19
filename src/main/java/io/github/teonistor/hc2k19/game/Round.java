package io.github.teonistor.hc2k19.game;

import io.github.teonistor.hc2k19.Player;
import io.github.teonistor.hc2k19.cards.Card;

import java.util.List;

import static java.util.stream.IntStream.range;

public enum Round {
    Initial, Flop, Turn, River;

    public void preBid(Game game, List<Player> activePlayers) {
        switch (this) {
            case Initial:
                for (Player p : activePlayers) {
                    game.dealFirstCardToPlayer(p, game.deck().get());
                    game.dealSecondCardToPlayer(p, game.deck().get());
                }
                break;
            case Flop:
                range(0,3).forEach(ignore -> {
                    final Card card = game.deck().get();
                    activePlayers.forEach(p -> p.reveal(card));
                    game.revealCard(card);
                });
                break;
            default:
                final Card card = game.deck().get();
                activePlayers.forEach(p -> p.reveal(card));
                game.revealCard(card);
        }
    }

//    public void postBid() {
//        switch (this){
//            case River:
//                // TODO handle game end
//
//
//                break;
//
//            default:
//                // Nothing.
//        }
//    }
}
