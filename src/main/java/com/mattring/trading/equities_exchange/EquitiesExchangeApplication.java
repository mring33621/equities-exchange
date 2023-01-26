package com.mattring.trading.equities_exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class EquitiesExchangeApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EquitiesExchangeApplication.class, args);
    }

}
