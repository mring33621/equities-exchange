package com.mattring.trading.equities_exchange.domain.customer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;


public class CustomerDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String name;

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

}
