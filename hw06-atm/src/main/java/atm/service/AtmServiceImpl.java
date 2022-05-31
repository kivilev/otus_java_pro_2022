package atm.service;

import atm.model.BanknoteType;
import atm.model.Atm;

import java.util.Map;

public class AtmServiceImpl implements atm.service.AtmService {

    private final Atm atm;

    public AtmServiceImpl(Atm atm) {
        this.atm = atm;
    }

    @Override
    public void putBanknotes(Map<BanknoteType, Integer> banknotes) {
        atm.putBanknotes(banknotes);
    }

    @Override
    public Map<BanknoteType, Integer> popBanknotes(long neededSum) {
        return atm.popBanknotes(neededSum);
    }

    @Override
    public long getBalance() {
        return atm.getBalance();
    }
}
