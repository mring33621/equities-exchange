package com.mattring.trading.equities_exchange.matching_engine;

import com.mattring.trading.equities_exchange.domain.Order;
import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrder;
import com.mattring.trading.equities_exchange.domain.match.Match;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrder;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CSV {

    /**
     * Intended to facilitate testing
     * NOTES:
     * 1) any id < 0 will be ignored
     * 2) sets order.active = true
     *
     * @param CSV String: type,id,symbol,numShares,limitPrice
     * @return SellOrder, BuyOrder or null
     */
    public static Order toOrder(String record) {
        final String[] parts = record.split("\s*,\s*");
        final String type = parts[0];

        Order order = null;
        if ("buy".equalsIgnoreCase(type)) {
            order = new BuyOrder();
        } else if ("sell".equalsIgnoreCase(type)) {
            order = new SellOrder();
        }

        if (order != null) {
            final long id = Long.parseLong(parts[1]);
            final String symbol = parts[2];
            final int numShares = Integer.parseInt(parts[3]);
            final double limitPrice = Double.parseDouble(parts[4]);
            order.setActive(true);
            if (id > 0L) {
                order.setId(id);
            }
            order.setSymbol(symbol);
            order.setNumShares(numShares);
            order.setLimitPrice(limitPrice);
        }

        return order;
    }

    public static String fromOrder(Order o) {
        // NOTE: instanceof & ternary operator didn't play nice for picking the type
        String type = "sell";
        if (o instanceof BuyOrder) type = "buy";
        return
                type + ','
                + o.getId() + ','
                + o.getSymbol() + ','
                + o.getNumShares() + ','
                + o.getLimitPrice();
    }

    /**
     * Intended to facilitate testing
     * @param tracker
     * @return numSharesBalance,numMatches,matchN.sellOrderId,matchN.buyOrderId,matchN.matchShares,matchN.matchPrice...
     */
    public static String fromMatchTracker(MatchTracker tracker) {
        final List<Object> fields = new LinkedList<>();
        fields.add(tracker.getNumSharesBalance());
        fields.add(tracker.getMatches().size());
        for (Match match : tracker.getMatches()) {
            fields.add(match.getMatchSellOrder().getId());
            fields.add(match.getMatchBuyOrder().getId());
            fields.add(match.getMatchShares());
            fields.add(match.getMatchPrice());
        }
        return fields.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

}
