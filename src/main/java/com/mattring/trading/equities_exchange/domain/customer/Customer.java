package com.mattring.trading.equities_exchange.domain.customer;

import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrder;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Customer {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "UUID")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "buyOrderCustomer")
    private Set<BuyOrder> buyOrderCustomerBuyOrders;

    @OneToMany(mappedBy = "sellOrderCustomer")
    private Set<SellOrder> sellOrderCustomerSellOrders;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<BuyOrder> getBuyOrderCustomerBuyOrders() {
        return buyOrderCustomerBuyOrders;
    }

    public void setBuyOrderCustomerBuyOrders(final Set<BuyOrder> buyOrderCustomerBuyOrders) {
        this.buyOrderCustomerBuyOrders = buyOrderCustomerBuyOrders;
    }

    public Set<SellOrder> getSellOrderCustomerSellOrders() {
        return sellOrderCustomerSellOrders;
    }

    public void setSellOrderCustomerSellOrders(final Set<SellOrder> sellOrderCustomerSellOrders) {
        this.sellOrderCustomerSellOrders = sellOrderCustomerSellOrders;
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
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", dateCreated=").append(dateCreated);
        sb.append(", lastUpdated=").append(lastUpdated);
        sb.append('}');
        return sb.toString();
    }
}
