package com.bluetfi.service;

import com.bluetfi.api.InvestmentService;
import com.bluetfi.model.Fund;
import com.bluetfi.model.FundType;
import com.bluetfi.model.InvestingStyle;

import java.util.*;
import java.util.stream.Collectors;

public class BlueInvestmentService implements InvestmentService {

    private Map<String, Fund> funds;
    private Double returnToInvestorBalance;

    public BlueInvestmentService(Map<String, Fund> funds) {
        this.funds = funds;
        returnToInvestorBalance = 0.0;
    }

    public Double getFundBalance(String fundName) {
        return funds.get(fundName).getBalance();
    }

    public Double getReturnToInvestorBalance() {
        return returnToInvestorBalance;
    }

    public void resetBalances() {
        funds.forEach((key, value) -> value.resetBalance());
        returnToInvestorBalance = 0.0d;
    }

    public void serviceInvestment(Double amount,
                                        InvestingStyle style,
                                        List<String> fundNames) throws FundNotFoundException, NoFundsGivenException {

        if(fundNames == null || fundNames.isEmpty()) {
            throw new NoFundsGivenException("No funds in the investment order");
        }

        if(funds.keySet().containsAll(fundNames)) {
            LinkedHashMap<FundType, List<Fund>> fundsByType =
                    funds.values()
                            .stream()
                            .filter(fund -> fundNames.contains(fund.getName()))
                            .collect(Collectors.groupingBy(Fund::getType, LinkedHashMap::new,
                                    Collectors.mapping(fund -> fund, Collectors.toList())));
            Double amountToReturn = Double.valueOf(amount);
            amountToReturn -= serviceInvestmentByFundType(amount, style.getPolishPerc(), fundsByType, FundType.POLISH);
            amountToReturn -= serviceInvestmentByFundType(amount, style.getForeignPerc(), fundsByType, FundType.FOREIGN);
            amountToReturn -= serviceInvestmentByFundType(amount, style.getCashPerc(), fundsByType, FundType.CASH);
            if(amountToReturn > 0) {
                returnToInvestor(amountToReturn);
            }
        } else {
            throw new FundNotFoundException("One or more funds are unavailable");
        }
    }

    private Double serviceInvestmentByFundType(Double amount, Double percent, Map<FundType, List<Fund>> fundsByType,
                                                   FundType fundType) {
        Double amountToService = amount * percent;
        Integer numberOfFunds = 0;
        Double actuallyServiced = 0.0;
        List<Fund> fundsList = fundsByType.get(fundType);
        List<String> fundsInvolved = null;
        if(fundsList != null) {
            fundsInvolved = fundsList
                    .stream()
                    .filter(Objects::nonNull)
                    .map(Fund::getName)
                    .collect(Collectors.toList());
            numberOfFunds = fundsInvolved.size();
            Double amountPerFund = numberOfFunds > 0 ? amountToService / numberOfFunds : 0;

            String firstFundName = null;
            for (String fundName: funds.keySet()) {
                if(fundsInvolved.contains(fundName)) {
                    if(firstFundName == null) firstFundName = fundName;
                    actuallyServiced += funds.get(fundName).increaseBalance(amountPerFund);
                }
            }
            Double changeFromRoundings = amountToService - actuallyServiced;
            actuallyServiced += serviceChangeFromRoundings(firstFundName, changeFromRoundings);
        }
        return actuallyServiced;
    }

    private Double serviceChangeFromRoundings(String fundName, Double amount) {
        return funds.get(fundName).increaseBalance(Math.floor(amount));
    }

    private void returnToInvestor(Double amount) {
        if(amount != 0.0) {
            returnToInvestorBalance += amount;
            System.out.println(String.format("%s PLN of change will be returned to investor", amount));
        }
    }

}
