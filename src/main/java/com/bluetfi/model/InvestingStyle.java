package com.bluetfi.model;

public enum InvestingStyle {
    SAFE(0.2, 0.75, 0.05),
    BALANCED(0.3, 0.6, 0.1),
    AGGRESSIVE(0.4, 0.2, 0.4);

    private Double polishPerc, foreignPerc, cashPerc;

    private InvestingStyle(Double polishPerc, Double foreignPerc, Double cashPerc) {
        this.polishPerc = polishPerc;
        this.foreignPerc = foreignPerc;
        this.cashPerc = cashPerc;
    }

    public Double getPolishPerc() {
        return polishPerc;
    }

    public Double getForeignPerc() {
        return foreignPerc;
    }

    public Double getCashPerc() {
        return cashPerc;
    }

}
