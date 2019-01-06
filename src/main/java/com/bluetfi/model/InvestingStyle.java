package com.bluetfi.model;

public enum InvestingStyle {
    SAFE(0.2, 0.75, 0.05),
    BALANCED(0.3, 0.6, 0.1),
    AGGRESSIVE(0.4, 0.2, 0.4);

    private Double[] percentages;

    private InvestingStyle(Double polishPerc, Double foreignPerc, Double cashPerc) {
        percentages = new Double[3];
        percentages[0] = polishPerc;
        percentages[1] = foreignPerc;
        percentages[2] = cashPerc;
    }

    public Double[] getPercentages() {
        return percentages;
    }

}
