package com.mattring.trading.equities_exchange.domain.buy_order;

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
@RequestMapping(value = "/api/buyOrders", produces = MediaType.APPLICATION_JSON_VALUE)
public class BuyOrderResource {

    private final BuyOrderService buyOrderService;

    public BuyOrderResource(final BuyOrderService buyOrderService) {
        this.buyOrderService = buyOrderService;
    }

    @GetMapping
    public ResponseEntity<List<BuyOrderDTO>> getAllBuyOrders() {
        return ResponseEntity.ok(buyOrderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuyOrderDTO> getBuyOrder(@PathVariable final Long id) {
        return ResponseEntity.ok(buyOrderService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createBuyOrder(@RequestBody @Valid final BuyOrderDTO buyOrderDTO) {
        return new ResponseEntity<>(buyOrderService.create(buyOrderDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBuyOrder(@PathVariable final Long id,
            @RequestBody @Valid final BuyOrderDTO buyOrderDTO) {
        buyOrderService.update(id, buyOrderDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBuyOrder(@PathVariable final Long id) {
        buyOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
