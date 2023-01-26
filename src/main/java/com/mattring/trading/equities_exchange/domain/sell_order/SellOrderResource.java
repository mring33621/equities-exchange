package com.mattring.trading.equities_exchange.domain.sell_order;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/sellOrders", produces = MediaType.APPLICATION_JSON_VALUE)
public class SellOrderResource {

    private final SellOrderService sellOrderService;

    public SellOrderResource(final SellOrderService sellOrderService) {
        this.sellOrderService = sellOrderService;
    }

    @GetMapping
    public ResponseEntity<List<SellOrderDTO>> getAllSellOrders() {
        return ResponseEntity.ok(sellOrderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellOrderDTO> getSellOrder(@PathVariable final Long id) {
        return ResponseEntity.ok(sellOrderService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSellOrder(
            @RequestBody @Valid final SellOrderDTO sellOrderDTO) {
        return new ResponseEntity<>(sellOrderService.create(sellOrderDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSellOrder(@PathVariable final Long id,
            @RequestBody @Valid final SellOrderDTO sellOrderDTO) {
        sellOrderService.update(id, sellOrderDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSellOrder(@PathVariable final Long id) {
        sellOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
