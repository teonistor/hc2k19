package io.github.teonistor.hc2k19;

import io.github.teonistor.hc2k19.cards.Card;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import static io.github.teonistor.hc2k19.BidAction.*;
import static java.util.Arrays.asList;

public class Controller implements Player {

    public static Controller instance; // LOL KEK demo

    static Color white = Color.valueOf("white"),
                 blue  = Color.valueOf("#333a68");

    @FXML TextField cardOnLeft, cardOnRight, cardOne, cardTwo, cardThree, cardFour, cardFive;
    @FXML Rectangle cardLeft, cardRight, flop_1, flop_2, flop_3, turn, river;
    @FXML Label bid, pot, meDolla, p1Name, p1Dolla, p2Name, p2Dolla, p3Name, p3Dolla, p1Action, p2Action, p3Action,
                p1Winner, p2Winner, p3Winner, meWinner;

    private final AtomicReference<BidAction> action = new AtomicReference<>(); // LOL KEK demo
    private final Map<Player, Label> dollaLabels;
    private final Map<Player, Label> actionLabels;
    private final Map<Player, Label> winnerLabels;


    public Controller() {
        instance = this;
        dollaLabels = new HashMap<>();
        actionLabels = new HashMap<>();
        winnerLabels = new HashMap<>();
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
            .orElseThrow(() -> new IllegalStateException("Error - card could not be revealed"))
            .setText(c.toString());
        asList(flop_1, flop_2, flop_3, turn, river)
            .stream()
            .filter(rekt -> rekt.getFill().equals(blue))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Error - card could not be revealed"))
            .setFill(white);
    }

    @Override
    public void announce(Map<Player, Integer> dolla, int bid, int pot, Player other, BidAction action) {
        Platform.runLater(() -> {
            this.bid.setText("Bid: $ " + bid);
            this.pot.setText("Pot: $ " + pot);

            dolla.forEach((p,d) -> {
                if (p != this) {
                    if (!dollaLabels.containsKey(p)) {
                        if (!dollaLabels.containsValue(p1Dolla)) {
                            dollaLabels.put(p, p1Dolla);
                            actionLabels.put(p, p1Action);
                            winnerLabels.put(p, p1Winner);
                            p1Name.setText(p.toString());
                        } else if (!dollaLabels.containsValue(p2Dolla)) {
                            dollaLabels.put(p, p2Dolla);
                            actionLabels.put(p, p2Action);
                            winnerLabels.put(p, p2Winner);
                            p2Name.setText(p.toString());
                        } else if (!dollaLabels.containsValue(p3Dolla)) {
                            dollaLabels.put(p, p3Dolla);
                            actionLabels.put(p, p3Action);
                            winnerLabels.put(p, p3Winner);
                            p3Name.setText(p.toString());
                        }
                    }
                    if (dollaLabels.containsKey(p)) {
                        dollaLabels.get(p).setText(String.format("$%d", d));

                    }
                } else {
                    meDolla.setText(String.format("$%d", d));
                }

                if(actionLabels.containsKey(other)) {
                    actionLabels.get(other).setText(action.toString());
                }
            });
        });
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

    @Override
    public void declareWinnerOfPlay(Player p) {
        Label winnerLabel = winnerLabels.containsKey(p) ?
                winnerLabels.get(p)
                : (this == p ? meWinner
                :null);

        // TODO ugly
        if (winnerLabel != null) {
            Platform.runLater(() -> winnerLabel.setVisible(true));

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> winnerLabel.setVisible(false));
        }
    }
}
