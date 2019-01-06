package com.bluetfi.model;

public class Fund {

    private Long id;
    private String name;
    private FundType type;
    private Double balance;

    public Fund(Long id, FundType type, String name) {
        this.id = id;
        this.name = name;
        this.type = type;
        resetBalance();
    }

    public void resetBalance() {
        balance = 0.0;
    }

    public Double increaseBalance(Double amount) {
        Double roundedAmount = Math.floor(amount);
        this.balance += roundedAmount;
        return roundedAmount;
    }

    public String getName() {
        return name;
    }

    public FundType getType() {
        return type;
    }

    public Double getBalance() {
        return balance;
    }

}
