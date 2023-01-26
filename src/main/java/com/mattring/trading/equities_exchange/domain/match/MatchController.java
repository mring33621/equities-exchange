package com.mattring.trading.equities_exchange.domain.match;

import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrder;
import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrderRepository;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrder;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrderRepository;
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
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;
    private final BuyOrderRepository buyOrderRepository;
    private final SellOrderRepository sellOrderRepository;

    public MatchController(final MatchService matchService,
            final BuyOrderRepository buyOrderRepository,
            final SellOrderRepository sellOrderRepository) {
        this.matchService = matchService;
        this.buyOrderRepository = buyOrderRepository;
        this.sellOrderRepository = sellOrderRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("matchBuyOrderValues", buyOrderRepository.findAll(Sort.by("id"))
                .stream()
                .collect(Collectors.toMap(BuyOrder::getId, BuyOrder::getSymbol)));
        model.addAttribute("matchSellOrderValues", sellOrderRepository.findAll(Sort.by("id"))
                .stream()
                .collect(Collectors.toMap(SellOrder::getId, SellOrder::getSymbol)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("matches", matchService.findAll());
        return "match/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("match") final MatchDTO matchDTO) {
        return "match/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("match") @Valid final MatchDTO matchDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "match/add";
        }
        matchService.create(matchDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("match.create.success"));
        return "redirect:/matches";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("match", matchService.get(id));
        return "match/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("match") @Valid final MatchDTO matchDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "match/edit";
        }
        matchService.update(id, matchDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("match.update.success"));
        return "redirect:/matches";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        matchService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("match.delete.success"));
        return "redirect:/matches";
    }

}
