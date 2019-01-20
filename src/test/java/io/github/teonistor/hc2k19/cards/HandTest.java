package io.github.teonistor.hc2k19.cards;

import org.junit.Test;
import java.util.*;
import static io.github.teonistor.hc2k19.cards.Hand.*;
import static io.github.teonistor.hc2k19.cards.Card.*;
import static org.junit.Assert.*;

public class HandTest {
    @Test public void testFlush() {
        assertTrue(Flush.matches(Arrays.asList(C_A, C_3, C_10, C_4, C_7)));
        assertTrue(Flush.matches(Arrays.asList(C_A, C_3, C_10, C_6, C_5, C_7)));
        assertTrue(Flush.matches(Arrays.asList(S_7, S_9, S_J, S_A, S_6)));

        assertFalse(Flush.matches(Arrays.asList(C_A, S_3, S_4, S_7, C_9)));
        assertFalse(Flush.matches(Arrays.asList(C_A, S_3, S_4, S_7, C_9, S_10)));
        assertFalse(Flush.matches(Arrays.asList(D_A, D_2, D_3, D_4)));
    }

    @Test public void testStraight() {
        assertTrue(Straight.matches(Arrays.asList(C_2, C_3, D_4, S_5, S_6)));
        assertTrue(Straight.matches(Arrays.asList(C_2, C_3, D_10, S_5, S_6, H_4)));
        assertTrue(Straight.matches(Arrays.asList(C_2, C_3, D_10, S_5, H_10, S_6, H_4)));
        assertTrue(Straight.matches(Arrays.asList(H_Q, C_K, D_J, H_A, H_10)));
        assertTrue(Straight.matches(Arrays.asList(C_2, C_3, H_5, S_A, H_10, S_6, H_4)));

        assertFalse(Straight.matches(Arrays.asList(H_2, H_3, H_5, H_6, H_10)));
        assertFalse(Straight.matches(Arrays.asList(D_2, H_9, C_9, H_6, H_10)));
        assertFalse(Straight.matches(Arrays.asList(D_8, H_9, C_9, C_J, H_10)));
    }

    @Test public void testTwoPairs() {
        assertTrue(TwoPairs.matches(Arrays.asList(C_A, C_7, D_7, D_A, H_2)));
        assertTrue(TwoPairs.matches(Arrays.asList(C_A, C_8, D_8, D_A, H_A)));
        assertTrue(TwoPairs.matches(Arrays.asList(S_7, C_7, D_9, D_A, H_9, C_A)));

        assertFalse(TwoPairs.matches(Arrays.asList(C_A, C_7, D_8, D_6, H_2)));
        assertFalse(TwoPairs.matches(Arrays.asList(C_9, C_7, D_8, D_A, H_2)));
        assertFalse(TwoPairs.matches(Arrays.asList(C_A, D_A, C_7, D_8, H_2)));
        assertFalse(TwoPairs.matches(Arrays.asList(C_A, D_8, C_7, D_A, H_A)));
    }

    @Test public void testPair() {
        assertTrue(Pair.matches(Arrays.asList(C_A, C_7, D_8, D_A, H_2)));
        assertTrue(Pair.matches(Arrays.asList(C_A, C_7, D_8, D_A, H_A)));
        assertTrue(Pair.matches(Arrays.asList(S_7, C_7, D_7, D_A, H_2)));

        assertFalse(Pair.matches(Arrays.asList(C_A, C_7, D_8, D_6, H_2)));
        assertFalse(Pair.matches(Arrays.asList(C_9, C_7, D_8, D_A, H_2)));
    }
}