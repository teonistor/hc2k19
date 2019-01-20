package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.cards.Card;
import io.github.teonistor.hc2k19.cards.Hand;
import io.improbable.keanu.algorithms.NetworkSamples;
import io.improbable.keanu.algorithms.PosteriorSamplingAlgorithm;
import io.improbable.keanu.algorithms.mcmc.MetropolisHastings;
import io.improbable.keanu.network.BayesianNetwork;
import io.improbable.keanu.tensor.Tensor;
import io.improbable.keanu.tensor.bool.BooleanTensor;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.bool.BooleanVertex;
import io.improbable.keanu.vertices.bool.nonprobabilistic.operators.binary.AndBinaryVertex;
import io.improbable.keanu.vertices.bool.nonprobabilistic.operators.multiple.OrMultipleVertex;
import io.improbable.keanu.vertices.bool.probabilistic.BernoulliVertex;
import io.improbable.keanu.vertices.dbl.KeanuRandom;
import io.improbable.keanu.vertices.generic.probabilistic.discrete.CategoricalVertex;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

import static io.github.teonistor.hc2k19.cards.Hand.*;

public class ProbabilisticPlayer implements Player {

    public ProbabilisticPlayer() {

        Map<Hand, BooleanVertex> handHappens = new EnumMap<>(Hand.class);
        Map<Hand, BooleanVertex> handWins = new EnumMap<>(Hand.class);
        Map<Hand, BooleanVertex> handHappensAndWins = new EnumMap<>(Hand.class);
        for (Hand hand : Hand.values()) {
            BernoulliVertex h = new BernoulliVertex(hand.getProbability() / 100);
            BernoulliVertex w = new BernoulliVertex(hand.getWinningProbability());
            handHappens.put(hand, h);
            handWins.put(hand, w);
            handHappensAndWins.put(hand, h.and(w));
        }
        OrMultipleVertex anyHappensAndWins = new OrMultipleVertex(handHappensAndWins.values().stream().map(shit -> (Vertex<BooleanTensor>)shit).collect(Collectors.toList()));

        // Some simple observations we can make about cards in the hand
        BooleanVertex cardsSameKind = handHappens.get(FourOfAKind).and(new BernoulliVertex(0.9))
                                  .or(handHappens.get(FullHouse).and(new BernoulliVertex(0.7)))
                                  .or(handHappens.get(ThreeOfAKind).and(new BernoulliVertex(0.5)))
                                  .or(handHappens.get(TwoPairs).and(new BernoulliVertex(0.45)));

        BooleanVertex cardsSameSuit = handHappens.get(StraightFlush).and(new BernoulliVertex(0.95))
                                  .or(handHappens.get(Flush).and(new BernoulliVertex(0.95)))
                                  .or(handHappens.get(RoyalFlush).and(new BernoulliVertex(0.95)));




        ////

        System.out.println(anyHappensAndWins.getValue().scalar());

        BayesianNetwork net = new BayesianNetwork(cardsSameSuit.getConnectedGraph());
        PosteriorSamplingAlgorithm sampler = MetropolisHastings.withDefaultConfig();
        NetworkSamples samples = sampler.getPosteriorSamples(net, anyHappensAndWins, 20000).drop(1000);


        System.out.println("Initial Probabilty it's anyHappensAndWins: "
                + samples.get(anyHappensAndWins).probability(val -> val.scalar()));

        cardsSameSuit.observe(true);

        net.probeForNonZeroProbability(1000);
        samples = sampler.getPosteriorSamples(net, anyHappensAndWins, 20000).drop(1000);
        System.out.println("After Observation : "
                + samples.get(anyHappensAndWins).probability(val -> val.scalar()));
    }

    @Override
    public void beginPlay() {

    }

    @Override
    public void deal(Card c) {

    }

    @Override
    public void reveal(Card c) {

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
        return null;
    }
}
