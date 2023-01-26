package com.mattring.trading.equities_exchange.domain.buy_order;

import com.mattring.trading.equities_exchange.domain.Order;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;


public class BuyOrderDTO implements Order {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String symbol;

    @NotNull
    private Integer numShares;

    @NotNull
    private Double limitPrice;

    @NotNull
    private Boolean active;

    @NotNull
    private UUID buyOrderCustomer;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public Integer getNumShares() {
        return numShares;
    }

    public void setNumShares(final Integer numShares) {
        this.numShares = numShares;
    }

    public Double getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(final Double limitPrice) {
        this.limitPrice = limitPrice;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public UUID getBuyOrderCustomer() {
        return buyOrderCustomer;
    }

    public void setBuyOrderCustomer(final UUID buyOrderCustomer) {
        this.buyOrderCustomer = buyOrderCustomer;
    }

}
