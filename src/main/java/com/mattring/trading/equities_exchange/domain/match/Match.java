package com.mattring.trading.equities_exchange.domain.match;

import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrder;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Match {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private Double matchPrice;

    @Column(nullable = false)
    private Integer matchShares;

    @Column(nullable = false)
    private Boolean sellerNotified;

    @Column(nullable = false)
    private Boolean buyerNotified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_buy_order_id", nullable = false)
    private BuyOrder matchBuyOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_sell_order_id", nullable = false)
    private SellOrder matchSellOrder;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

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

    public BuyOrder getMatchBuyOrder() {
        return matchBuyOrder;
    }

    public void setMatchBuyOrder(final BuyOrder matchBuyOrder) {
        this.matchBuyOrder = matchBuyOrder;
    }

    public SellOrder getMatchSellOrder() {
        return matchSellOrder;
    }

    public void setMatchSellOrder(final SellOrder matchSellOrder) {
        this.matchSellOrder = matchSellOrder;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(final OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Match{");
        sb.append("id=").append(id);
        sb.append(", matchPrice=").append(matchPrice);
        sb.append(", matchShares=").append(matchShares);
        sb.append(", sellerNotified=").append(sellerNotified);
        sb.append(", buyerNotified=").append(buyerNotified);
        sb.append(", matchBuyOrderId=").append(matchBuyOrder.getId());
        sb.append(", matchSellOrderId=").append(matchSellOrder.getId());
        sb.append(", dateCreated=").append(dateCreated);
        sb.append(", lastUpdated=").append(lastUpdated);
        sb.append('}');
        return sb.toString();
    }
}
