package com.mattring.trading.equities_exchange.matching_engine;

import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrder;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchTrackerTest {

    @Test
    void testSinglePerfectMatch() {
        SellOrder sell1 = new SellOrder();
        sell1.setSymbol("ABCD");
        sell1.setNumShares(100);
        sell1.setLimitPrice(123.45);
        BuyOrder buy1 = new BuyOrder();
        buy1.setSymbol("ABCD");
        buy1.setNumShares(100);
        buy1.setLimitPrice(123.55);
        MatchTracker tracker = new MatchTracker();
        boolean validMatch = tracker.checkMatchAndAddIfValid(sell1, buy1);
        assertTrue(validMatch);
        assertFalse(tracker.isEmpty());
        assertEquals(1, tracker.getMatches().size());
        assertEquals(0, tracker.getNumSharesBalance());
        assertEquals(0, sell1.getNumShares());
        assertEquals(0, buy1.getNumShares());
        assertEquals(100, tracker.getMatches().get(0).getMatchShares());
        assertEquals(247.00d/2d, tracker.getMatches().get(0).getMatchPrice());
    }

    @Test
    void testOneSellManyBuys() {
        MatchTracker tracker = new MatchTracker();
        SellOrder sell1 = (SellOrder) CSV.toOrder("sell,1,EBAY,100,40.40");
        BuyOrder buy1 = (BuyOrder) CSV.toOrder("buy,1,EBAY,50,40.50");
        boolean validMatch = tracker.checkMatchAndAddIfValid(sell1, buy1);
        assertTrue(validMatch);
        BuyOrder buy2 = (BuyOrder) CSV.toOrder("buy,2,EBAY,25,40.60");
        validMatch = tracker.checkMatchAndAddIfValid(sell1, buy2);
        assertTrue(validMatch);
        BuyOrder buy3 = (BuyOrder) CSV.toOrder("buy,3,EBAY,50,40.70");
        validMatch = tracker.checkMatchAndAddIfValid(sell1, buy3);
        assertTrue(validMatch);
        BuyOrder buy4 = (BuyOrder) CSV.toOrder("buy,4,EBAY,50,40.80");
        validMatch = tracker.checkMatchAndAddIfValid(sell1, buy4);
        assertFalse(validMatch);
        assertEquals(
                "-25,3,1,1,50,40.45,1,2,25,40.5,1,3,25,40.55",
                CSV.fromMatchTracker(tracker));
    }
}