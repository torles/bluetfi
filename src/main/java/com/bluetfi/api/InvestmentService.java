package com.bluetfi.api;

import com.bluetfi.model.Fund;
import com.bluetfi.model.InvestingStyle;
import com.bluetfi.service.FundNotFoundException;
import com.bluetfi.service.NoFundsGivenException;

import java.util.List;
import java.util.Map;

public interface InvestmentService {

    Double getFundBalance(String fundName);
    Double getReturnToInvestorBalance();
    void resetBalances();
    void serviceInvestment(Double amount,
                           InvestingStyle style,
                           List<String> fundNames) throws FundNotFoundException,
                                                            NoFundsGivenException;

}
