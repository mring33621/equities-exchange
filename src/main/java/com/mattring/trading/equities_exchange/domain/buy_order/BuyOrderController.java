package com.mattring.trading.equities_exchange.domain.buy_order;

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
@RequestMapping("/buyOrders")
public class BuyOrderController {

    private final BuyOrderService buyOrderService;
    private final CustomerRepository customerRepository;

    public BuyOrderController(final BuyOrderService buyOrderService,
            final CustomerRepository customerRepository) {
        this.buyOrderService = buyOrderService;
        this.customerRepository = customerRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("buyOrderCustomerValues", customerRepository.findAll(Sort.by("id"))
                .stream()
                .collect(Collectors.toMap(Customer::getId, Customer::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("buyOrders", buyOrderService.findAll());
        return "buyOrder/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("buyOrder") final BuyOrderDTO buyOrderDTO) {
        return "buyOrder/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("buyOrder") @Valid final BuyOrderDTO buyOrderDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "buyOrder/add";
        }
        buyOrderService.create(buyOrderDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("buyOrder.create.success"));
        return "redirect:/buyOrders";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("buyOrder", buyOrderService.get(id));
        return "buyOrder/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("buyOrder") @Valid final BuyOrderDTO buyOrderDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "buyOrder/edit";
        }
        buyOrderService.update(id, buyOrderDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("buyOrder.update.success"));
        return "redirect:/buyOrders";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = buyOrderService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            buyOrderService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("buyOrder.delete.success"));
        }
        return "redirect:/buyOrders";
    }

}
