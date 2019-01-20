package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.cards.Card;
import io.github.teonistor.hc2k19.cards.Hand;
import io.improbable.keanu.algorithms.NetworkSamples;
import io.improbable.keanu.algorithms.PosteriorSamplingAlgorithm;
import io.improbable.keanu.algorithms.mcmc.MetropolisHastings;
import io.improbable.keanu.network.BayesianNetwork;
import io.improbable.keanu.tensor.bool.BooleanTensor;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.bool.BooleanVertex;
import io.improbable.keanu.vertices.bool.nonprobabilistic.operators.multiple.OrMultipleVertex;
import io.improbable.keanu.vertices.bool.probabilistic.BernoulliVertex;
import java.util.*;
import java.util.stream.Collectors;
import static io.github.teonistor.hc2k19.BidAction.*;
import static io.github.teonistor.hc2k19.cards.Hand.*;

public class ProbabilisticPlayer implements Player {

    private final Map<Hand, BooleanVertex> handHappens;
    private final Map<Hand, BooleanVertex> handWins;
    private final Map<Hand, BooleanVertex> handHappensAndWins;
    private final BooleanVertex anyHappensAndWins;
    private final BooleanVertex cardsSameKind;
    private final BooleanVertex cardsSameSuit;

    private final Stack<BooleanVertex> observedVertices;
    private final BayesianNetwork network;
    private final PosteriorSamplingAlgorithm sampler;

    private final List<Card> cards;
    private double currentWinProbability = 0.5;

    public ProbabilisticPlayer() {

        handHappens = new EnumMap<>(Hand.class);
        handWins = new EnumMap<>(Hand.class);
        handHappensAndWins = new EnumMap<>(Hand.class);
        for (Hand hand : Hand.values()) {
            BernoulliVertex h = new BernoulliVertex(hand.getProbability() / 100);
            BernoulliVertex w = new BernoulliVertex(hand.getWinningProbability());
            handHappens.put(hand, h);
            handWins.put(hand, w);
            handHappensAndWins.put(hand, h.and(w));
        }
        anyHappensAndWins = new OrMultipleVertex(handHappensAndWins.values().stream().map(ve -> (Vertex<BooleanTensor>)ve).collect(Collectors.toList()));

        // Some simple observations we can make about cards in the hand
        cardsSameKind = handHappens.get(FourOfAKind).and(new BernoulliVertex(0.9))
                    .or(handHappens.get(FullHouse).and(new BernoulliVertex(0.7)))
                    .or(handHappens.get(ThreeOfAKind).and(new BernoulliVertex(0.5)))
                    .or(handHappens.get(TwoPairs).and(new BernoulliVertex(0.45)));

        cardsSameSuit = handHappens.get(StraightFlush).and(new BernoulliVertex(0.95))
                    .or(handHappens.get(Flush).and(new BernoulliVertex(0.95)))
                    .or(handHappens.get(RoyalFlush).and(new BernoulliVertex(0.95)));


        observedVertices=new Stack<>();
        network = new BayesianNetwork(anyHappensAndWins.getConnectedGraph());
        sampler = MetropolisHastings.withDefaultConfig();
        cards = new ArrayList<>();
    }

    @Override
    public void beginPlay() {
        while (!observedVertices.empty()){
            observedVertices.pop().unobserve();
        }
        cards.clear();
    }

    @Override
    public void deal(Card c) {
        System.out.printf("%s was dealt %s%n", this, c);
        cards.add(c);
        if (cards.size() == 2) {
            observeAndRecord(cardsSameKind, cards.get(0).getKind().equals(cards.get(1).getKind()));
            observeAndRecord(cardsSameSuit, cards.get(0).getSuit().equals(cards.get(1).getSuit()));
            recalculateProbability();
        }
    }

    @Override
    public void reveal(Card c) {
        cards.add(c);
        if (cards.size() > 4) {
            Hand bestHand = Hand.bestHand(cards);
            observeAndRecord(handHappens.get(bestHand), true);
            recalculateProbability();
        }
    }

    @Override
    public void announce(Player other, BidAction action) {

    }

    @Override
    public void announce(Map<Player, Integer> dolla, int bid, int pot) {

    }

    @Override
    public void endPlay(int dolla) {

    }

    @Override
    public BidAction takeTurn() {
        if (currentWinProbability > 0.85) {
            return Raise;
        } else if (currentWinProbability > 0.2) {
            return Call;
        } else {
            return Fold;
        }
    }

    private void observeAndRecord (BooleanVertex vertex, boolean value) {
        vertex.observe(value);
        observedVertices.push(vertex);
    }

    private void recalculateProbability() {
        try {
            network.probeForNonZeroProbability(1000);
            NetworkSamples samples = sampler.getPosteriorSamples(network, anyHappensAndWins, 20000).drop(1000);
            currentWinProbability = samples.get(anyHappensAndWins).probability(val -> val.scalar());
            System.out.printf("%s recalculated winning probability as %.6f%n", this, currentWinProbability);
        } catch (Exception e) {
            System.err.println("Cannot recalculate probability - network is in impossible state");
            e.printStackTrace();
        }
    }

    @Override public String toString () {
        return "Probabilisticus";
    }
}
