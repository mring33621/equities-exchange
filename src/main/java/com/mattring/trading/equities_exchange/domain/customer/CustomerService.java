package com.mattring.trading.equities_exchange.domain.customer;

import com.mattring.trading.equities_exchange.util.NotFoundException;
import com.mattring.trading.equities_exchange.util.WebUtils;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDTO> findAll() {
        final List<Customer> customers = customerRepository.findAll(Sort.by("id"));
        return customers.stream()
                .map((customer) -> mapToDTO(customer, new CustomerDTO()))
                .collect(Collectors.toList());
    }

    public CustomerDTO get(final UUID id) {
        return customerRepository.findById(id)
                .map(customer -> mapToDTO(customer, new CustomerDTO()))
                .orElseThrow(() -> new NotFoundException());
    }

    public UUID create(final CustomerDTO customerDTO) {
        final Customer customer = new Customer();
        mapToEntity(customerDTO, customer);
        return customerRepository.save(customer).getId();
    }

    public void update(final UUID id, final CustomerDTO customerDTO) {
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        mapToEntity(customerDTO, customer);
        customerRepository.save(customer);
    }

    public void delete(final UUID id) {
        customerRepository.deleteById(id);
    }

    private CustomerDTO mapToDTO(final Customer customer, final CustomerDTO customerDTO) {
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        return customerDTO;
    }

    private Customer mapToEntity(final CustomerDTO customerDTO, final Customer customer) {
        customer.setName(customerDTO.getName());
        return customer;
    }

    public boolean nameExists(final String name) {
        return customerRepository.existsByNameIgnoreCase(name);
    }

    @Transactional
    public String getReferencedWarning(final UUID id) {
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        if (!customer.getBuyOrderCustomerBuyOrders().isEmpty()) {
            return WebUtils.getMessage("customer.buyOrder.manyToOne.referenced", customer.getBuyOrderCustomerBuyOrders().iterator().next().getId());
        } else if (!customer.getSellOrderCustomerSellOrders().isEmpty()) {
            return WebUtils.getMessage("customer.sellOrder.manyToOne.referenced", customer.getSellOrderCustomerSellOrders().iterator().next().getId());
        }
        return null;
    }

}
