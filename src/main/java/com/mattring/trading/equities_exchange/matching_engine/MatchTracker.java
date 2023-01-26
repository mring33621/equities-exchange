package com.mattring.trading.equities_exchange.matching_engine;

import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrder;
import com.mattring.trading.equities_exchange.domain.match.Match;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.LinkedList;
import java.util.List;

public class MatchTracker {

    private final Log log = LogFactory.getLog(MatchTracker.class);
    private List<Match> matches = new LinkedList<>();
    private int numSharesBalance;

    /**
     * @param sellOrder
     * @param buyOrder
     * @return true if valid matching_engine based on symbol & price
     */
    public boolean checkMatchAndAddIfValid(SellOrder sellOrder, BuyOrder buyOrder) {
        log.debug(CSV.fromOrder(sellOrder));
        log.debug(CSV.fromOrder(buyOrder));
        // validity check
        boolean validMatch = false;
        if (sellOrder.getSymbol().equals(buyOrder.getSymbol())
                && sellOrder.getLimitPrice() <= buyOrder.getLimitPrice()
                && sellOrder.getNumShares() > 0) {
            // calculate matching_engine-related values
            final int matchShares = Math.min(sellOrder.getNumShares(), buyOrder.getNumShares());
            final double matchPrice = (sellOrder.getLimitPrice() + buyOrder.getLimitPrice()) / 2d; // midpoint
            numSharesBalance = sellOrder.getNumShares() - buyOrder.getNumShares();
            // build and add matching_engine object
            final Match match = new Match();
            match.setMatchShares(matchShares);
            match.setMatchPrice(matchPrice);
            match.setMatchBuyOrder(buyOrder);
            match.setMatchSellOrder(sellOrder);
            matches.add(match);
            // adjust buy and sell order remaining shares by matching_engine share amount
            sellOrder.setNumShares(sellOrder.getNumShares() - matchShares);
            buyOrder.setNumShares(buyOrder.getNumShares() - matchShares);
            validMatch = true;
        }
        log.debug(validMatch);
        return validMatch;
    }

    public int getNumSharesBalance() {
        return numSharesBalance;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public boolean isEmpty() {
        return matches.isEmpty();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MatchTracker{");
        sb.append("matches=").append(matches);
        sb.append(",\nnumSharesBalance=").append(numSharesBalance);
        sb.append(",\nempty=").append(isEmpty());
        sb.append('}');
        return sb.toString();
    }
}
