package com.mattring.trading.equities_exchange.domain.sell_order;

import com.mattring.trading.equities_exchange.domain.customer.Customer;
import com.mattring.trading.equities_exchange.domain.customer.CustomerRepository;
import com.mattring.trading.equities_exchange.util.WebUtils;
import jakarta.validation.Valid;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/sellOrders")
public class SellOrderController {

    private final SellOrderService sellOrderService;
    private final CustomerRepository customerRepository;

    public SellOrderController(final SellOrderService sellOrderService,
            final CustomerRepository customerRepository) {
        this.sellOrderService = sellOrderService;
        this.customerRepository = customerRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("sellOrderCustomerValues", customerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(Collectors.toMap(Customer::getId, Customer::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("sellOrders", sellOrderService.findAll());
        return "sellOrder/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("sellOrder") final SellOrderDTO sellOrderDTO) {
        return "sellOrder/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("sellOrder") @Valid final SellOrderDTO sellOrderDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sellOrder/add";
        }
        sellOrderService.create(sellOrderDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sellOrder.create.success"));
        return "redirect:/sellOrders";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("sellOrder", sellOrderService.get(id));
        return "sellOrder/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("sellOrder") @Valid final SellOrderDTO sellOrderDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "sellOrder/edit";
        }
        sellOrderService.update(id, sellOrderDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("sellOrder.update.success"));
        return "redirect:/sellOrders";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = sellOrderService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            sellOrderService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("sellOrder.delete.success"));
        }
        return "redirect:/sellOrders";
    }

}
