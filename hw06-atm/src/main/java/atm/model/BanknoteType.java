package atm.model;

public enum BanknoteType {
    RUB100(100),
    RUB200(200),
    RUB500(500),
    RUB1000(1000),
    RUB5000(5000);

    private int value;

    BanknoteType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
