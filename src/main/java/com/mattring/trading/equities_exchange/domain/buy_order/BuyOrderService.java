package com.mattring.trading.equities_exchange.domain.buy_order;

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
public class BuyOrderService {

    private final BuyOrderRepository buyOrderRepository;
    private final CustomerRepository customerRepository;

    public BuyOrderService(final BuyOrderRepository buyOrderRepository,
            final CustomerRepository customerRepository) {
        this.buyOrderRepository = buyOrderRepository;
        this.customerRepository = customerRepository;
    }

    public List<BuyOrderDTO> findAll() {
        final List<BuyOrder> buyOrders = buyOrderRepository.findAll(Sort.by("id"));
        return buyOrders.stream()
                .map((buyOrder) -> mapToDTO(buyOrder, new BuyOrderDTO()))
                .collect(Collectors.toList());
    }

    public BuyOrderDTO get(final Long id) {
        return buyOrderRepository.findById(id)
                .map(buyOrder -> mapToDTO(buyOrder, new BuyOrderDTO()))
                .orElseThrow(() -> new NotFoundException());
    }

    public Long create(final BuyOrderDTO buyOrderDTO) {
        final BuyOrder buyOrder = new BuyOrder();
        mapToEntity(buyOrderDTO, buyOrder);
        return buyOrderRepository.save(buyOrder).getId();
    }

    public void update(final Long id, final BuyOrderDTO buyOrderDTO) {
        final BuyOrder buyOrder = buyOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        mapToEntity(buyOrderDTO, buyOrder);
        buyOrderRepository.save(buyOrder);
    }

    public void delete(final Long id) {
        buyOrderRepository.deleteById(id);
    }

    private BuyOrderDTO mapToDTO(final BuyOrder buyOrder, final BuyOrderDTO buyOrderDTO) {
        buyOrderDTO.setId(buyOrder.getId());
        buyOrderDTO.setSymbol(buyOrder.getSymbol());
        buyOrderDTO.setNumShares(buyOrder.getNumShares());
        buyOrderDTO.setLimitPrice(buyOrder.getLimitPrice());
        buyOrderDTO.setActive(buyOrder.getActive());
        buyOrderDTO.setBuyOrderCustomer(buyOrder.getBuyOrderCustomer() == null ? null : buyOrder.getBuyOrderCustomer().getId());
        return buyOrderDTO;
    }

    private BuyOrder mapToEntity(final BuyOrderDTO buyOrderDTO, final BuyOrder buyOrder) {
        buyOrder.setSymbol(buyOrderDTO.getSymbol());
        buyOrder.setNumShares(buyOrderDTO.getNumShares());
        buyOrder.setLimitPrice(buyOrderDTO.getLimitPrice());
        buyOrder.setActive(buyOrderDTO.getActive());
        final Customer buyOrderCustomer = buyOrderDTO.getBuyOrderCustomer() == null ? null : customerRepository.findById(buyOrderDTO.getBuyOrderCustomer())
                .orElseThrow(() -> new NotFoundException("buyOrderCustomer not found"));
        buyOrder.setBuyOrderCustomer(buyOrderCustomer);
        return buyOrder;
    }

    @Transactional
    public String getReferencedWarning(final Long id) {
        final BuyOrder buyOrder = buyOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        if (!buyOrder.getMatchBuyOrderMatches().isEmpty()) {
            return WebUtils.getMessage("buyOrder.match.manyToOne.referenced", buyOrder.getMatchBuyOrderMatches().iterator().next().getId());
        }
        return null;
    }

}
