package atm.service;

import atm.model.BanknoteType;
import atm.repository.AtmRepository;

import java.util.Map;

public class AtmServiceImpl implements atm.service.AtmService {

    private final AtmRepository atmRepository;

    public AtmServiceImpl(AtmRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Override
    public void putBanknotes(Map<BanknoteType, Integer> banknotes) {
        atmRepository.putBanknotes(banknotes);
    }

    @Override
    public Map<BanknoteType, Integer> popBanknotes(long neededSum) {
        return atmRepository.popBanknotes(neededSum);
    }

    @Override
    public long getBalance() {
        return atmRepository.getBalance();
    }
}
