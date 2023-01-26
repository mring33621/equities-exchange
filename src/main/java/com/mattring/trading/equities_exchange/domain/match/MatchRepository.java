package com.mattring.trading.equities_exchange.domain.match;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MatchRepository extends JpaRepository<Match, Long> {
}
