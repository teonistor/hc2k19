package io.github.teonistor.hc2k19.game;

import io.github.teonistor.hc2k19.*;
import io.github.teonistor.hc2k19.cards.Card;
import io.github.teonistor.hc2k19.cards.Hand;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class Game {

    private final Player[] players;
    private Supplier<Card> deck;
//    private final Map<Player, Boolean> inGame; // Not needed until we implement death by loss of all money
    private final Map<Player, Integer> dolla;
    private final Map<Player, Integer> bid;
    private final Map<Player, Card> card1, card2;
    private final List<Card> revealedCards;
    private List<Player> activePlayers;

    public Game() {
        players = new Player[] {
            new RandomPlayer("Bot 1"),
            new ProbabilisticPlayer(),
            new RandomPlayer("Bot 2")
        };
        dolla = new HashMap<>();
        bid = new HashMap<>();
//        inGame = new HashMap<>();
        card1 = new HashMap<>();
        card2 = new HashMap<>();
        revealedCards = new ArrayList<>();

        Arrays.stream(players).forEach(p -> {
//            inGame.put(p, true);
            dolla.put(p, 0);
        });
    }

    public void play() {
        System.out.printf("A %d-player game has begun.%n%n", players.length);

//        Arrays.stream(players)
//                .forEach(p -> p.announce(dolla, 0, 0, new RandomPlayer("Test"), BidAction));


        for (int i=0;i<10;i++) {
            System.out.printf("Play %d dolla: %s%n", i, dolla);
            revealedCards.clear();
            playOne();

            // Analyse hands and see who tf won
            Player winner = null;
            Hand winningHand = null;
            for (Player player : players) {
                if (activePlayers.contains(player)) {
                    Hand hand = Hand.bestHand(computeHand(player));
                    if (winningHand == null || winningHand.compareTo(hand) < 0) {
                        winningHand = hand;
                        winner = player;
                    }
                }
            }

            System.out.printf("Play %d won by %s%n", i, winner);
            dolla.compute(winner, (p, d) -> d + bid.values().stream().mapToInt(ii -> ii).sum());
        }

        System.out.printf("Final dolla: %s%n", dolla);
    }

    public void playOne() {
        deck = Card.getShuffledDeck();
        int nextToBid = 0;
        Arrays.stream(players).forEach(p -> bid.put(p, 0));

        final AtomicInteger currentBid = new AtomicInteger(5); // atomic because lambda
        boolean blind = true;
//        List<Player> activePlayers = Arrays.stream(players).filter(inGame::get).collect(toList()); // That's not what that is!
        activePlayers = new ArrayList<>(Arrays.asList(players));
        activePlayers.forEach(Player::beginPlay);

        for (Round round : Round.values()) {

            round.preBid(this, activePlayers);

            for (int uncalled = activePlayers.size(); uncalled > 0; uncalled--) {
                Player currentPlayer = activePlayers.get(nextToBid);
                BidAction action = currentPlayer.takeTurn();
                System.out.printf("%s %sed%n", currentPlayer, action);
                switch (action) {
                    case Fold:
                        if (blind) {
                            System.out.println("Cannot fold on blind! Treating as Call.");
                        } else {
                            activePlayers.remove(currentPlayer);
//                            inGame.put(currentPlayer, false);
                            nextToBid--;
                            break;
                        }
                    case Raise: // Consider a fixed raise amount
                        uncalled = activePlayers.size();
                        currentBid.addAndGet(10);
                    case Call:
                        dolla.compute(currentPlayer, (p, d) -> d - currentBid.get());
                        bid.compute(currentPlayer, (p, d) -> d + currentBid.get());

                }

                Arrays.stream(players)
                    .forEach(p -> p.announce(dolla, currentBid.get(),
                            bid.values().stream().mapToInt(ii -> ii).sum(), currentPlayer, action));

                if (activePlayers.size() == 1) {
                    return;
                }

                blind = false;
                nextToBid = (nextToBid + 1) % activePlayers.size();
            }


//            round.postBid(); // not good with the return above
        }
    }

    void dealFirstCardToPlayer(Player player, Card card) {
        player.deal(card);
        card1.put(player, card);
    }

    void dealSecondCardToPlayer(Player player, Card card) {
        player.deal(card);
        card2.put(player, card);
    }

    public Supplier<Card> deck() {
        return deck;
    }

    public void revealCard(final Card card) {
        if (revealedCards.size() > 4) {
            throw new IllegalStateException("5 cards were already revealed");
        }
        revealedCards.add(card);
        Arrays.stream(players).forEach(p -> p.reveal(card));
        System.out.printf("%s Has been revealed%n", card);
    }

    private List<Card> computeHand (Player winner) {
        List<Card> hand = new ArrayList<>(revealedCards);
        hand.add(card1.get(winner));
        hand.add(card2.get(winner));
        return hand;
    }
}
