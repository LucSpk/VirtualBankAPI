package br.com.lucas.virtualBankAPI.enums.transactions;

public enum TransactionType {
    TRANSFER(0),
    PAYMENT(1),
    DEPOSIT(2),
    WITHDRAW(3);

    private final int value;

    TransactionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TransactionType fromValue(int value) throws IllegalAccessException {
        for (TransactionType type : TransactionType.values()) {
            if(type.value == value) {
                return type;
            }
        }
        throw new IllegalAccessException("Invalid TransactionType value: " + value);
    }
}
