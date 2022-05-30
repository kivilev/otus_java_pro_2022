package atm.model;

import java.security.InvalidParameterException;

public class AtmRepositoryCell {
    private final BanknoteType banknoteType;
    private int banknoteCount = 0;

    public AtmRepositoryCell(BanknoteType banknoteType) {
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

    public BanknoteType getBanknoteType() {
        return banknoteType;
    }

    public int getBanknoteValue() {
        return banknoteType.getValue();
    }

}