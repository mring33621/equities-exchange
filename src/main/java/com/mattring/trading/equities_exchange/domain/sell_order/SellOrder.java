package com.mattring.trading.equities_exchange.domain.sell_order;

import com.mattring.trading.equities_exchange.domain.Order;
import com.mattring.trading.equities_exchange.domain.customer.Customer;
import com.mattring.trading.equities_exchange.domain.match.Match;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class SellOrder implements Order {

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

    @Column(nullable = false, length = 30)
    private String symbol;

    @Column(nullable = false)
    private Integer numShares;

    @Column(nullable = false)
    private Double limitPrice;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sell_order_customer_id", nullable = false)
    private Customer sellOrderCustomer;

    @OneToMany(mappedBy = "matchSellOrder")
    private Set<Match> matchSellOrderMatches;

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

    public Customer getSellOrderCustomer() {
        return sellOrderCustomer;
    }

    public void setSellOrderCustomer(final Customer sellOrderCustomer) {
        this.sellOrderCustomer = sellOrderCustomer;
    }

    public Set<Match> getMatchSellOrderMatches() {
        return matchSellOrderMatches;
    }

    public void setMatchSellOrderMatches(final Set<Match> matchSellOrderMatches) {
        this.matchSellOrderMatches = matchSellOrderMatches;
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
        final StringBuilder sb = new StringBuilder("SellOrder{");
        sb.append("id=").append(id);
        sb.append(", symbol='").append(symbol).append('\'');
        sb.append(", numShares=").append(numShares);
        sb.append(", limitPrice=").append(limitPrice);
        sb.append(", active=").append(active);
        sb.append(", sellOrderCustomer=").append(sellOrderCustomer);
        sb.append(", dateCreated=").append(dateCreated);
        sb.append(", lastUpdated=").append(lastUpdated);
        sb.append('}');
        return sb.toString();
    }
}
