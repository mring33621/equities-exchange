package com.mattring.trading.equities_exchange.domain.sell_order;

import com.mattring.trading.equities_exchange.domain.Order;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;


public class SellOrderDTO implements Order {

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
    private UUID sellOrderCustomer;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    @Override
    public Integer getNumShares() {
        return numShares;
    }

    @Override
    public void setNumShares(final Integer numShares) {
        this.numShares = numShares;
    }

    @Override
    public Double getLimitPrice() {
        return limitPrice;
    }

    @Override
    public void setLimitPrice(final Double limitPrice) {
        this.limitPrice = limitPrice;
    }

    @Override
    public Boolean getActive() {
        return active;
    }

    @Override
    public void setActive(final Boolean active) {
        this.active = active;
    }

    public UUID getSellOrderCustomer() {
        return sellOrderCustomer;
    }

    public void setSellOrderCustomer(final UUID sellOrderCustomer) {
        this.sellOrderCustomer = sellOrderCustomer;
    }

}
