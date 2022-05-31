package atm.model;

import atm.model.BanknoteType;

import java.util.Map;

public interface Atm {

    void putBanknotes(Map<BanknoteType, Integer> banknotes);

    Map<BanknoteType, Integer> popBanknotes(long neededSum);

    long getBalance();
}
