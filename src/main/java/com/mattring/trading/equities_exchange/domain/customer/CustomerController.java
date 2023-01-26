package com.mattring.trading.equities_exchange.domain.customer;

import com.mattring.trading.equities_exchange.util.WebUtils;
import jakarta.validation.Valid;
import java.util.UUID;
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
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("customer") final CustomerDTO customerDTO) {
        return "customer/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("customer") @Valid final CustomerDTO customerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("name") && customerService.nameExists(customerDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.customer.name");
        }
        if (bindingResult.hasErrors()) {
            return "customer/add";
        }
        customerService.create(customerDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("customer.create.success"));
        return "redirect:/customers";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final UUID id, final Model model) {
        model.addAttribute("customer", customerService.get(id));
        return "customer/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final UUID id,
            @ModelAttribute("customer") @Valid final CustomerDTO customerDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("name") &&
                !customerService.get(id).getName().equalsIgnoreCase(customerDTO.getName()) &&
                customerService.nameExists(customerDTO.getName())) {
            bindingResult.rejectValue("name", "Exists.customer.name");
        }
        if (bindingResult.hasErrors()) {
            return "customer/edit";
        }
        customerService.update(id, customerDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("customer.update.success"));
        return "redirect:/customers";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final UUID id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = customerService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            customerService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("customer.delete.success"));
        }
        return "redirect:/customers";
    }

}
