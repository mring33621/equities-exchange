package com.mattring.trading.equities_exchange.domain;

import java.util.UUID;

public interface Order {
    Long getId();

    void setId(Long id);

    String getSymbol();

    void setSymbol(String symbol);

    Integer getNumShares();

    void setNumShares(Integer numShares);

    Double getLimitPrice();

    void setLimitPrice(Double limitPrice);

    Boolean getActive();

    void setActive(Boolean active);

}
