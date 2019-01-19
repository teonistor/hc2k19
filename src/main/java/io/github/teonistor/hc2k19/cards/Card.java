package io.github.teonistor.hc2k19.cards;

import com.google.common.collect.ImmutableMap;
import io.improbable.keanu.tensor.Tensor;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.ConstantDoubleVertex;
import io.improbable.keanu.vertices.generic.probabilistic.discrete.CategoricalVertex;

import java.util.*;
import java.util.function.*;

import static io.github.teonistor.hc2k19.cards.Kind.*;
import static io.github.teonistor.hc2k19.cards.Suit.*;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.toCollection;

public enum Card {
    C_2(Clubs, Two),
    C_3(Clubs, Three),
    C_4(Clubs, Four),
    C_5(Clubs, Five),
    C_6(Clubs, Six),
    C_7(Clubs, Seven),
    C_8(Clubs, Eight),
    C_9(Clubs, Nine),
    C_10(Clubs, Ten),
    C_J(Clubs, Jack),
    C_Q(Clubs, Queen),
    C_K(Clubs, King),
    C_A(Clubs, Ace),
    D_2(Diamonds, Two),
    D_3(Diamonds, Three),
    D_4(Diamonds, Four),
    D_5(Diamonds, Five),
    D_6(Diamonds, Six),
    D_7(Diamonds, Seven),
    D_8(Diamonds, Eight),
    D_9(Diamonds, Nine),
    D_10(Diamonds, Ten),
    D_J(Diamonds, Jack),
    D_Q(Diamonds, Queen),
    D_K(Diamonds, King),
    D_A(Diamonds, Ace),
    H_2(Hearts, Two),
    H_3(Hearts, Three),
    H_4(Hearts, Four),
    H_5(Hearts, Five),
    H_6(Hearts, Six),
    H_7(Hearts, Seven),
    H_8(Hearts, Eight),
    H_9(Hearts, Nine),
    H_10(Hearts, Ten),
    H_J(Hearts, Jack),
    H_Q(Hearts, Queen),
    H_K(Hearts, King),
    H_A(Hearts, Ace),
    S_2(Spades, Two),
    S_3(Spades, Three),
    S_4(Spades, Four),
    S_5(Spades, Five),
    S_6(Spades, Six),
    S_7(Spades, Seven),
    S_8(Spades, Eight),
    S_9(Spades, Nine),
    S_10(Spades, Ten),
    S_J(Spades, Jack),
    S_Q(Spades, Queen),
    S_K(Spades, King),
    S_A(Spades, Ace);

    private final Suit suit;
    private final Kind kind;

    Card(Suit suit, Kind kind) {
        this.suit = suit;
        this.kind = kind;
    }

    public Kind getKind() {
        return kind;
    }

    public Suit getSuit() {
        return suit;
    }

    public String toString() {
        return String.format("%s of %s", kind, suit);
    }

    public static Supplier<Card> getShuffledDeck() {
        Integer[] shuffledIndexes = new Integer[values().length];
        setAll(shuffledIndexes, i->i);
        Collections.shuffle(asList(shuffledIndexes));

        return stream(shuffledIndexes)
            .map(i -> values()[i])
            .collect(toCollection(LinkedList::new))
            ::remove;
    }

    public static CategoricalVertex<Card, Tensor<Card>> getCategoricalVertex() {
        final ImmutableMap.Builder<Card, DoubleVertex> builder = ImmutableMap.builder();
        for (Card c : values())
            builder.put(c, new ConstantDoubleVertex(1.0/values().length));
        return new CategoricalVertex<>(builder.build());
    }
}
