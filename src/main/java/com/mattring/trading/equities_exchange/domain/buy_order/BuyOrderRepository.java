package com.mattring.trading.equities_exchange.domain.buy_order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BuyOrderRepository extends JpaRepository<BuyOrder, Long> {

    List<BuyOrder> findBySymbolAndActiveAndLimitPriceGreaterThanEqualOrderByDateCreatedAsc(String symbol, boolean active, double limitPrice);

}
