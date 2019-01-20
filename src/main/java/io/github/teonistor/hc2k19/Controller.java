package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.cards.Card;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.concurrent.atomic.AtomicReference;
import static io.github.teonistor.hc2k19.BidAction.*;

public class Controller implements Player {

    public static Controller instance; // LOL KEK demo

    @FXML TextField cardOnLeft, cardOnRight;
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
        } else {
            cardOnRight.setText(c.toString());
        }
    }

    @Override
    public void reveal(Card c) {

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
