package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.cards.Card;
import io.improbable.keanu.tensor.Tensor;
import io.improbable.keanu.vertices.dbl.KeanuRandom;
import io.improbable.keanu.vertices.generic.probabilistic.discrete.CategoricalVertex;

import java.util.Map;

public class ProbabilisticPlayer implements Player {

    public ProbabilisticPlayer() {

        CategoricalVertex<Card, Tensor<Card>> ccv = Card.getCategoricalVertex();


        System.out.println(ccv.sample(new KeanuRandom()).scalar());
        System.out.println(ccv.sample(new KeanuRandom()).scalar());
        System.out.println(ccv.sample(new KeanuRandom()).scalar());
        System.out.println(ccv.sample(new KeanuRandom()).scalar());
        System.out.println(ccv.sample(new KeanuRandom()).scalar());
        System.out.println(ccv.sample(new KeanuRandom()).scalar());
        System.out.println(ccv.sample(new KeanuRandom()).scalar());
        System.out.println(ccv.sample(new KeanuRandom()).scalar());

//        IntegerVertex[] cards = new IntegerVertex[7];
//        Arrays.setAll(cards, ignore -> new UniformIntVertex(0, 52));
//
//        BooleanVertex bv = new
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
