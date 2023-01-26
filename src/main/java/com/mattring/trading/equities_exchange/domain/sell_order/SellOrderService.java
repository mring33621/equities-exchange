package com.mattring.trading.equities_exchange.domain.sell_order;

import com.mattring.trading.equities_exchange.domain.customer.Customer;
import com.mattring.trading.equities_exchange.domain.customer.CustomerRepository;
import com.mattring.trading.equities_exchange.util.NotFoundException;
import com.mattring.trading.equities_exchange.util.WebUtils;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SellOrderService {

    private final SellOrderRepository sellOrderRepository;
    private final CustomerRepository customerRepository;

    public SellOrderService(final SellOrderRepository sellOrderRepository,
            final CustomerRepository customerRepository) {
        this.sellOrderRepository = sellOrderRepository;
        this.customerRepository = customerRepository;
    }

    public List<SellOrderDTO> findAll() {
        final List<SellOrder> sellOrders = sellOrderRepository.findAll(Sort.by("id"));
        return sellOrders.stream()
                .map((sellOrder) -> mapToDTO(sellOrder, new SellOrderDTO()))
                .collect(Collectors.toList());
    }

    public SellOrderDTO get(final Long id) {
        return sellOrderRepository.findById(id)
                .map(sellOrder -> mapToDTO(sellOrder, new SellOrderDTO()))
                .orElseThrow(() -> new NotFoundException());
    }

    public Long create(final SellOrderDTO sellOrderDTO) {
        final SellOrder sellOrder = new SellOrder();
        mapToEntity(sellOrderDTO, sellOrder);
        return sellOrderRepository.save(sellOrder).getId();
    }

    public void update(final Long id, final SellOrderDTO sellOrderDTO) {
        final SellOrder sellOrder = sellOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        mapToEntity(sellOrderDTO, sellOrder);
        sellOrderRepository.save(sellOrder);
    }

    public void delete(final Long id) {
        sellOrderRepository.deleteById(id);
    }

    private SellOrderDTO mapToDTO(final SellOrder sellOrder, final SellOrderDTO sellOrderDTO) {
        sellOrderDTO.setId(sellOrder.getId());
        sellOrderDTO.setSymbol(sellOrder.getSymbol());
        sellOrderDTO.setNumShares(sellOrder.getNumShares());
        sellOrderDTO.setLimitPrice(sellOrder.getLimitPrice());
        sellOrderDTO.setActive(sellOrder.getActive());
        sellOrderDTO.setSellOrderCustomer(sellOrder.getSellOrderCustomer() == null ? null : sellOrder.getSellOrderCustomer().getId());
        return sellOrderDTO;
    }

    private SellOrder mapToEntity(final SellOrderDTO sellOrderDTO, final SellOrder sellOrder) {
        sellOrder.setSymbol(sellOrderDTO.getSymbol());
        sellOrder.setNumShares(sellOrderDTO.getNumShares());
        sellOrder.setLimitPrice(sellOrderDTO.getLimitPrice());
        sellOrder.setActive(sellOrderDTO.getActive());
        final Customer sellOrderCustomer = sellOrderDTO.getSellOrderCustomer() == null ? null : customerRepository.findById(sellOrderDTO.getSellOrderCustomer())
                .orElseThrow(() -> new NotFoundException("sellOrderCustomer not found"));
        sellOrder.setSellOrderCustomer(sellOrderCustomer);
        return sellOrder;
    }

    @Transactional
    public String getReferencedWarning(final Long id) {
        final SellOrder sellOrder = sellOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        if (!sellOrder.getMatchSellOrderMatches().isEmpty()) {
            return WebUtils.getMessage("sellOrder.match.manyToOne.referenced", sellOrder.getMatchSellOrderMatches().iterator().next().getId());
        }
        return null;
    }

}
