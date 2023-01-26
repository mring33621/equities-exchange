package com.mattring.trading.equities_exchange.domain.match;

import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrder;
import com.mattring.trading.equities_exchange.domain.buy_order.BuyOrderRepository;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrder;
import com.mattring.trading.equities_exchange.domain.sell_order.SellOrderRepository;
import com.mattring.trading.equities_exchange.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final BuyOrderRepository buyOrderRepository;
    private final SellOrderRepository sellOrderRepository;

    public MatchService(final MatchRepository matchRepository,
            final BuyOrderRepository buyOrderRepository,
            final SellOrderRepository sellOrderRepository) {
        this.matchRepository = matchRepository;
        this.buyOrderRepository = buyOrderRepository;
        this.sellOrderRepository = sellOrderRepository;
    }

    public List<MatchDTO> findAll() {
        final List<Match> matches = matchRepository.findAll(Sort.by("id"));
        return matches.stream()
                .map((match) -> mapToDTO(match, new MatchDTO()))
                .collect(Collectors.toList());
    }

    public MatchDTO get(final Long id) {
        return matchRepository.findById(id)
                .map(match -> mapToDTO(match, new MatchDTO()))
                .orElseThrow(() -> new NotFoundException());
    }

    public Long create(final MatchDTO matchDTO) {
        final Match match = new Match();
        mapToEntity(matchDTO, match);
        return matchRepository.save(match).getId();
    }

    public void update(final Long id, final MatchDTO matchDTO) {
        final Match match = matchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        mapToEntity(matchDTO, match);
        matchRepository.save(match);
    }

    public void delete(final Long id) {
        matchRepository.deleteById(id);
    }

    private MatchDTO mapToDTO(final Match match, final MatchDTO matchDTO) {
        matchDTO.setId(match.getId());
        matchDTO.setMatchPrice(match.getMatchPrice());
        matchDTO.setMatchShares(match.getMatchShares());
        matchDTO.setSellerNotified(match.getSellerNotified());
        matchDTO.setBuyerNotified(match.getBuyerNotified());
        matchDTO.setMatchBuyOrder(match.getMatchBuyOrder() == null ? null : match.getMatchBuyOrder().getId());
        matchDTO.setMatchSellOrder(match.getMatchSellOrder() == null ? null : match.getMatchSellOrder().getId());
        return matchDTO;
    }

    private Match mapToEntity(final MatchDTO matchDTO, final Match match) {
        match.setMatchPrice(matchDTO.getMatchPrice());
        match.setMatchShares(matchDTO.getMatchShares());
        match.setSellerNotified(matchDTO.getSellerNotified());
        match.setBuyerNotified(matchDTO.getBuyerNotified());
        final BuyOrder matchBuyOrder = matchDTO.getMatchBuyOrder() == null ? null : buyOrderRepository.findById(matchDTO.getMatchBuyOrder())
                .orElseThrow(() -> new NotFoundException("matchBuyOrder not found"));
        match.setMatchBuyOrder(matchBuyOrder);
        final SellOrder matchSellOrder = matchDTO.getMatchSellOrder() == null ? null : sellOrderRepository.findById(matchDTO.getMatchSellOrder())
                .orElseThrow(() -> new NotFoundException("matchSellOrder not found"));
        match.setMatchSellOrder(matchSellOrder);
        return match;
    }

}
