package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.cards.Card;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.util.concurrent.atomic.AtomicReference;
import static io.github.teonistor.hc2k19.BidAction.*;
import static java.util.Arrays.asList;

public class Controller implements Player {

    public static Controller instance; // LOL KEK demo

    @FXML TextField cardOnLeft, cardOnRight, cardOne, cardTwo, cardThree, cardFour, cardFive;

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
    public void deal(Card c) {
        if (cardOnLeft.getText().equals("")) {
            cardOnLeft.setText(c.toString());

            // Hack
            asList(cardOne, cardTwo, cardThree, cardFour, cardFive).forEach(tf -> tf.setText(""));
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
    }

    @Override
    public void announce(Player other, BidAction action) {

    }

    @Override
    public void remind(int dolla) {

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
