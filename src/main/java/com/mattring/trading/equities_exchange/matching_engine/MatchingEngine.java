package com.mattring.trading.equities_exchange.matching_engine;

import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrder;
import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrderRepository;
import com.mattring.trading.equities_exchange.domain.match.MatchRepository;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrder;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrderRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
public class MatchingEngine {

    private final Log log = LogFactory.getLog(MatchingEngine.class);

    private final SellOrderRepository sellOrderRepository;
    private final BuyOrderRepository buyOrderRepository;
    private final MatchRepository matchRepository;
    private Consumer<MatchTracker> matchRecorder;

    public MatchingEngine(SellOrderRepository sellOrderRepository, BuyOrderRepository buyOrderRepository, MatchRepository matchRepository) {
        this.sellOrderRepository = sellOrderRepository;
        this.buyOrderRepository = buyOrderRepository;
        this.matchRepository = matchRepository;
        this.matchRecorder = this::recordMatches;
        log.info("MatchingEngine()");
        log.info("sellOrderRepository=" + sellOrderRepository);
        log.info("buyOrderRepository=" + buyOrderRepository);
        log.info("matchRepository=" + matchRepository);
    }

    /**
     * Intended for use while testing
     * @param matchRecorder
     */
    public void setMatchRecorder(Consumer<MatchTracker> matchRecorder) {
        this.matchRecorder = matchRecorder;
    }

    @Scheduled(fixedDelay = 5000L)
    public void performOrderMatching() {
        log.info("performOrderMatching()");
        final List<SellOrder> sellOrderList =
                sellOrderRepository.findByActiveOrderBySymbolAscDateCreatedAsc(true);
        MatchTracker matchTracker = new MatchTracker();
        for (SellOrder sellOrder : sellOrderList) {
            final List<BuyOrder> buyOrderList =
                    buyOrderRepository.findBySymbolAndActiveAndLimitPriceGreaterThanEqualOrderByDateCreatedAsc(
                            sellOrder.getSymbol(),
                            true,
                            sellOrder.getLimitPrice());
            for (BuyOrder buyOrder : buyOrderList) {
                final boolean validMatch = matchTracker.checkMatchAndAddIfValid(sellOrder, buyOrder);
                if (!validMatch || matchTracker.getNumSharesBalance() <= 0) {
                    matchRecorder.accept(matchTracker);
                    matchTracker = new MatchTracker();
                    break;
                }
            }
        }
        matchRecorder.accept(matchTracker);
    }

    void recordMatches(MatchTracker matchTracker) {
        System.out.println(matchTracker);
    }
}
