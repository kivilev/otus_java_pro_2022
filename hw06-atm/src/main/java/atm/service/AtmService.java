package atm.service;

import atm.model.BanknoteType;

import java.util.Map;

public interface AtmService {

    void putBanknotes(Map<BanknoteType, Integer> banknotes);

    Map<BanknoteType, Integer> popBanknotes(long neededSum);

    long getBalance();
}
