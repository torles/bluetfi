package com.bluetfi;

import com.bluetfi.api.InvestmentService;
import config.ConfigFileReader;
import com.bluetfi.model.Fund;
import com.bluetfi.model.InvestingStyle;
import com.bluetfi.service.BlueInvestmentService;
import com.bluetfi.service.FundNotFoundException;
import com.bluetfi.service.NoFundsGivenException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestClass {

    private static Map<String, Fund> funds;
    private static InvestmentService service;

    @BeforeClass
    public static void initTestSuite() throws Exception {
        try {
            Path path = Paths.get("src", "test", "resources", "blue_funds.txt");
            funds = ConfigFileReader.readFundsFromFile(path);
            service = new BlueInvestmentService(funds);
        } catch (IOException e) {
            System.out.println("Aborting");
        }
    }

    @Before
    public void prepareTest() {
        service.resetBalances();
    }

    @Test
    public void shouldServiceSafeInvestmentProperly()
            throws FundNotFoundException, NoFundsGivenException {
        String fundName1 = "Fundusz Polski 1";
        String fundName2 = "Fundusz Polski 2";
        String fundName3 = "Fundusz Zagraniczny 1";
        String fundName4 = "Fundusz Zagraniczny 2";
        String fundName5 = "Fundusz Zagraniczny 3";
        String fundName6 = "Fundusz Pieniężny 1";

        List<String> fundNames = Arrays.asList(fundName1, fundName2, fundName3, fundName4, fundName5, fundName6);
        service.serviceInvestment(10000.0, InvestingStyle.SAFE, fundNames);

        assertEquals(Double.valueOf(1000.0), service.getFundBalance(fundName1));
        assertEquals(Double.valueOf(1000.0), service.getFundBalance(fundName2));
        assertEquals(Double.valueOf(2500.0), service.getFundBalance(fundName3));
        assertEquals(Double.valueOf(2500.0), service.getFundBalance(fundName4));
        assertEquals(Double.valueOf(2500.0), service.getFundBalance(fundName5));
        assertEquals(Double.valueOf(500.0), service.getFundBalance(fundName6));

        assertEquals(Double.valueOf(0.0), service.getReturnToInvestorBalance());
    }

    @Test
    public void shouldServiceSafeInvestmentWithReturnAmountProperly()
            throws FundNotFoundException, NoFundsGivenException {
        String fundName1 = "Fundusz Polski 1";
        String fundName2 = "Fundusz Polski 2";
        String fundName3 = "Fundusz Zagraniczny 1";
        String fundName4 = "Fundusz Zagraniczny 2";
        String fundName5 = "Fundusz Zagraniczny 3";
        String fundName6 = "Fundusz Pieniężny 1";

        List<String> fundNames = Arrays.asList(fundName1, fundName2, fundName3, fundName4, fundName5, fundName6);
        service.serviceInvestment(10001.0, InvestingStyle.SAFE, fundNames);

        assertEquals(Double.valueOf(1000.0), service.getFundBalance(fundName1));
        assertEquals(Double.valueOf(1000.0), service.getFundBalance(fundName2));
        assertEquals(Double.valueOf(2500.0), service.getFundBalance(fundName3));
        assertEquals(Double.valueOf(2500.0), service.getFundBalance(fundName4));
        assertEquals(Double.valueOf(2500.0), service.getFundBalance(fundName5));
        assertEquals(Double.valueOf(500.0), service.getFundBalance(fundName6));

        assertEquals(Double.valueOf(1.0), service.getReturnToInvestorBalance());
    }

    @Test
    public void shouldServiceSafeInvestmentWithNotRoundPercentageProperly()
            throws FundNotFoundException, NoFundsGivenException {
        String fundName1 = "Fundusz Polski 1";
        String fundName2 = "Fundusz Polski 2";
        String fundName3 = "Fundusz Polski 3";
        String fundName4 = "Fundusz Zagraniczny 1";
        String fundName5 = "Fundusz Zagraniczny 2";
        String fundName6 = "Fundusz Pieniężny 1";

        List<String> fundNames = Arrays.asList(fundName1, fundName2, fundName3, fundName4, fundName5, fundName6);
        service.serviceInvestment(10000.0, InvestingStyle.SAFE, fundNames);

        assertEquals(Double.valueOf(668.0), service.getFundBalance(fundName1));
        assertEquals(Double.valueOf(666.0), service.getFundBalance(fundName2));
        assertEquals(Double.valueOf(666.0), service.getFundBalance(fundName3));
        assertEquals(Double.valueOf(3750.0), service.getFundBalance(fundName4));
        assertEquals(Double.valueOf(3750.0), service.getFundBalance(fundName5));
        assertEquals(Double.valueOf(500.0), service.getFundBalance(fundName6));

        assertEquals(Double.valueOf(0.0), service.getReturnToInvestorBalance());
    }

    @Test
    public void shouldServiceBalancedInvestmentProperly()
            throws FundNotFoundException, NoFundsGivenException {
        String fundName1 = "Fundusz Polski 1";
        String fundName2 = "Fundusz Polski 2";
        String fundName3 = "Fundusz Zagraniczny 1";
        String fundName4 = "Fundusz Zagraniczny 2";
        String fundName5 = "Fundusz Zagraniczny 3";
        String fundName6 = "Fundusz Pieniężny 1";

        List<String> fundNames = Arrays.asList(fundName1, fundName2, fundName3, fundName4, fundName5, fundName6);
        service.serviceInvestment(10000.0, InvestingStyle.BALANCED, fundNames);

        assertEquals(Double.valueOf(1500.0), service.getFundBalance(fundName1));
        assertEquals(Double.valueOf(1500.0), service.getFundBalance(fundName2));
        assertEquals(Double.valueOf(2000.0), service.getFundBalance(fundName3));
        assertEquals(Double.valueOf(2000.0), service.getFundBalance(fundName4));
        assertEquals(Double.valueOf(2000.0), service.getFundBalance(fundName5));
        assertEquals(Double.valueOf(1000.0), service.getFundBalance(fundName6));

        assertEquals(Double.valueOf(0.0), service.getReturnToInvestorBalance());
    }

    @Test
    public void shouldServiceAggressiveInvestmentProperly()
            throws FundNotFoundException, NoFundsGivenException {
        String fundName1 = "Fundusz Polski 1";
        String fundName2 = "Fundusz Polski 2";
        String fundName3 = "Fundusz Polski 3";
        String fundName4 = "Fundusz Zagraniczny 1";
        String fundName5 = "Fundusz Zagraniczny 2";
        String fundName6 = "Fundusz Pieniężny 1";

        List<String> fundNames = Arrays.asList(fundName1, fundName2, fundName3, fundName4, fundName5, fundName6);
        service.serviceInvestment(20001.0, InvestingStyle.AGGRESSIVE, fundNames);

        assertEquals(Double.valueOf(2668.0), service.getFundBalance(fundName1));
        assertEquals(Double.valueOf(2666.0), service.getFundBalance(fundName2));
        assertEquals(Double.valueOf(2666.0), service.getFundBalance(fundName3));
        assertEquals(Double.valueOf(2000.0), service.getFundBalance(fundName4));
        assertEquals(Double.valueOf(2000.0), service.getFundBalance(fundName5));
        assertEquals(Double.valueOf(8000.0), service.getFundBalance(fundName6));

        assertEquals(Double.valueOf(1.0), service.getReturnToInvestorBalance());
    }

    @Test
    public void shouldServiceReturnToInvestorProperly()
            throws FundNotFoundException, NoFundsGivenException {
        String fundName1 = "Fundusz Pieniężny 1";

        List<String> fundNames = Arrays.asList(fundName1);
        service.serviceInvestment(0.76, InvestingStyle.BALANCED, fundNames);

        assertEquals(Double.valueOf(0.0), service.getFundBalance(fundName1));

        assertEquals(Double.valueOf(0.76), service.getReturnToInvestorBalance());
    }

    @Test(expected = FundNotFoundException.class)
    public void shouldThrowFundNotFoundException()
            throws FundNotFoundException, NoFundsGivenException {
        String fundName1 = "Fundusz Nieistniejący 1";

        List<String> fundNames = Arrays.asList(fundName1);
        service.serviceInvestment(1000.0, InvestingStyle.AGGRESSIVE, fundNames);
    }

    @Test(expected = NoFundsGivenException.class)
    public void shouldThrowFundNotFoundExceptionWhenNoFundsGiven()
            throws FundNotFoundException, NoFundsGivenException {
        List<String> fundNames = null;
        service.serviceInvestment(1000.0, InvestingStyle.AGGRESSIVE, fundNames);
    }
}
