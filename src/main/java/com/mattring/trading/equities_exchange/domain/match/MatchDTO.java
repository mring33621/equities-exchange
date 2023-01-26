package com.mattring.trading.equities_exchange.domain.match;

import jakarta.validation.constraints.NotNull;


public class MatchDTO {

    private Long id;

    @NotNull
    private Double matchPrice;

    @NotNull
    private Integer matchShares;

    @NotNull
    private Boolean sellerNotified;

    @NotNull
    private Boolean buyerNotified;

    @NotNull
    private Long matchBuyOrder;

    @NotNull
    private Long matchSellOrder;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Double getMatchPrice() {
        return matchPrice;
    }

    public void setMatchPrice(final Double matchPrice) {
        this.matchPrice = matchPrice;
    }

    public Integer getMatchShares() {
        return matchShares;
    }

    public void setMatchShares(final Integer matchShares) {
        this.matchShares = matchShares;
    }

    public Boolean getSellerNotified() {
        return sellerNotified;
    }

    public void setSellerNotified(final Boolean sellerNotified) {
        this.sellerNotified = sellerNotified;
    }

    public Boolean getBuyerNotified() {
        return buyerNotified;
    }

    public void setBuyerNotified(final Boolean buyerNotified) {
        this.buyerNotified = buyerNotified;
    }

    public Long getMatchBuyOrder() {
        return matchBuyOrder;
    }

    public void setMatchBuyOrder(final Long matchBuyOrder) {
        this.matchBuyOrder = matchBuyOrder;
    }

    public Long getMatchSellOrder() {
        return matchSellOrder;
    }

    public void setMatchSellOrder(final Long matchSellOrder) {
        this.matchSellOrder = matchSellOrder;
    }

}
