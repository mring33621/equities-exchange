package com.mattring.trading.equities_exchange.domain.customer;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    boolean existsByNameIgnoreCase(String name);

}
