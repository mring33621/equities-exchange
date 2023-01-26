package com.mattring.trading.equities_exchange.matching_engine;

import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrder;
import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrderRepository;
import com.mattring.trading.equities_exchange.domain.match.MatchRepository;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrder;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchingEngineTest {

    @Mock
    SellOrderRepository sellOrderRepository;
    @Mock
    BuyOrderRepository buyOrderRepository;
    @Mock
    MatchRepository matchRepository;

    @Test
    void performOrderMatching() {
        MatchingEngine engine = new MatchingEngine(sellOrderRepository, buyOrderRepository, matchRepository);
        engine.setMatchRecorder(mt -> System.out.println(CSV.fromMatchTracker(mt)));
        List<SellOrder> sellOrders = Arrays.stream(
                        new String[]{
                                "sell,1,AAAA,100,20.00",
                                "sell,2,AAAA,100,20.20",
                                "sell,3,BBBB,100,30.00",
                                "sell,4,BBBB,100,30.30",
                                "sell,5,CCCC,100,40.00"})
                .map(CSV::toOrder)
                .map(o -> (SellOrder) o)
                .collect(Collectors.toList());
        List<BuyOrder> buyOrdersList1 = Arrays.stream(
                        new String[]{
                                "buy,11,AAAA,20,20.09",
                                "buy,12,AAAA,30,20.03",
                                "buy,13,AAAA,20,20.00",
                                "buy,14,AAAA,50,20.08",
                                "buy,15,AAAA,50,20.05"})
                .map(CSV::toOrder)
                .map(o -> (BuyOrder) o)
                .collect(Collectors.toList());
        List<BuyOrder> buyOrdersList2 = Collections.emptyList();
        List<BuyOrder> buyOrdersList3 = Collections.emptyList();
        List<BuyOrder> buyOrdersList4 = Collections.emptyList();
        List<BuyOrder> buyOrdersList5 = Arrays.stream(
                        new String[]{
                                "buy,51,CCCC,45,40.01",
                                "buy,52,CCCC,100,40.00",
                                "buy,53,CCCC,20,40.02",
                                "buy,54,CCCC,50,40.06",
                                "buy,55,CCCC,50,40.03"})
                .map(CSV::toOrder)
                .map(o -> (BuyOrder) o)
                .collect(Collectors.toList());

        when(sellOrderRepository
                .findByActiveOrderBySymbolAscDateCreatedAsc(true))
                .thenReturn(sellOrders);
        when(buyOrderRepository
                .findBySymbolAndActiveAndLimitPriceGreaterThanEqualOrderByDateCreatedAsc("AAAA", true, 20.00d))
                .thenReturn(buyOrdersList1);
        when(buyOrderRepository
                .findBySymbolAndActiveAndLimitPriceGreaterThanEqualOrderByDateCreatedAsc("AAAA", true, 20.20d))
                .thenReturn(buyOrdersList2);
        when(buyOrderRepository
                .findBySymbolAndActiveAndLimitPriceGreaterThanEqualOrderByDateCreatedAsc("BBBB", true, 30.00d))
                .thenReturn(buyOrdersList3);
        when(buyOrderRepository
                .findBySymbolAndActiveAndLimitPriceGreaterThanEqualOrderByDateCreatedAsc("BBBB", true, 30.30d))
                .thenReturn(buyOrdersList4);
        when(buyOrderRepository
                .findBySymbolAndActiveAndLimitPriceGreaterThanEqualOrderByDateCreatedAsc("CCCC", true, 40.00d))
                .thenReturn(buyOrdersList5);

        engine.performOrderMatching();
    }
}