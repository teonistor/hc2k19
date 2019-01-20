package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.cards.Card;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.atomic.AtomicReference;
import static io.github.teonistor.hc2k19.BidAction.*;
import static java.util.Arrays.asList;

public class Controller implements Player {

    public static Controller instance; // LOL KEK demo

    static Color white = Color.valueOf("white"),
                 blue  = Color.valueOf("#333a68");

    @FXML TextField cardOnLeft, cardOnRight, cardOne, cardTwo, cardThree, cardFour, cardFive;
    @FXML Rectangle cardLeft, cardRight, flop_1, flop_2, flop_3, turn, river;

    private final AtomicReference<BidAction> action = new AtomicReference<>(); // LOL KEK demo

    public Controller() {
        instance = this;
    }

    public void fold() {
        action.set(Fold);
    }

    public void call() {
        action.set(Call);
    }

    public void raise() {
        action.set(Raise);
    }

    @Override
    public void beginPlay() {
        asList(cardOnLeft, cardOnRight, cardOne, cardTwo, cardThree, cardFour, cardFive).forEach(tf -> tf.setText(""));
        asList(flop_1, flop_2, flop_3, turn, river).forEach(rekt -> rekt.setFill(blue));
    }

    @Override
    public void deal(Card c) {
        if (cardOnLeft.getText().equals("")) {
            cardOnLeft.setText(c.toString());
        } else {
            cardOnRight.setText(c.toString());
        }
    }

    @Override
    public void reveal(Card c) {
        asList(cardOne, cardTwo, cardThree, cardFour, cardFive)
            .stream()
            .filter(tf -> tf.getText().equals(""))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(" Dude WTF"))
            .setText(c.toString());
        asList(flop_1, flop_2, flop_3, turn, river)
            .stream()
            .filter(rekt -> rekt.getFill().equals(blue))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(" Dude WTF"))
            .setFill(white);
    }

    @Override
    public void announce(Player other, BidAction action) {

    }

    @Override
    public void endPlay(int dolla) {

    }

    @Override
    public BidAction takeTurn() {
        BidAction ac;
        while ((ac = action.getAndSet(null)) == null);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ac;
    }
}
