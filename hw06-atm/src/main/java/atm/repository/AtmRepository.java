package atm.repository;

import atm.model.BanknoteType;

import java.util.Map;

public interface AtmRepository {

    void putBanknotes(Map<BanknoteType, Integer> banknotes);

    Map<BanknoteType, Integer> popBanknotes(long neededSum);

    long getBalance();
}
