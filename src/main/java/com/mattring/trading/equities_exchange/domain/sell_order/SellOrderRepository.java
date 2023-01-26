package com.mattring.trading.equities_exchange.domain.sell_order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SellOrderRepository extends JpaRepository<SellOrder, Long> {

    List<SellOrder> findByActiveOrderBySymbolAscDateCreatedAsc(boolean active);
}
