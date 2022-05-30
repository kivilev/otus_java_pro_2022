package atm.model;

import java.security.InvalidParameterException;

public class AtmCeil {
    private final atm.model.BanknoteType banknoteType;
    private int banknoteCount = 0;

    public AtmCeil(BanknoteType banknoteType) {
        this.banknoteType = banknoteType;
    }

    public void putBanknote(int count) {
        if (count <= 0) {
            throw new InvalidParameterException();
        }
        banknoteCount += count;
    }

    public void popBanknote(int count) {
        if (count <= 0) {
            throw new InvalidParameterException();
        }
        banknoteCount -= count;
    }

    public int getBanknoteCount() {
        return banknoteCount;
    }

    public Long getTotalBalance() {
        return (long) banknoteType.getValue() * banknoteCount;
    }

    public atm.model.BanknoteType getBanknoteType() {
        return banknoteType;
    }
}