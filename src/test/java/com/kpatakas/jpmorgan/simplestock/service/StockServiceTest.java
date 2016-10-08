package com.kpatakas.jpmorgan.simplestock.service;

import com.kpatakas.jpmorgan.simplestock.JPMorganTestConfiguration;
import com.kpatakas.jpmorgan.simplestock.exception.SimpleStockException;
import com.kpatakas.jpmorgan.simplestock.model.Stock;
import com.kpatakas.jpmorgan.simplestock.model.StockTrade;
import com.kpatakas.jpmorgan.simplestock.model.StockTradeType;
import com.kpatakas.jpmorgan.simplestock.model.StockType;
import com.kpatakas.jpmorgan.simplestock.repository.StockRepositoryTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Kostas Patakas on 2/10/2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JPMorganTestConfiguration.class)
public class StockServiceTest {

    private static final Logger T = LoggerFactory.getLogger(StockRepositoryTest.class);

    @Autowired
    private StockService stockService;

    private Stock stockA;
    private Stock stockB;
    private Stock stockC;

    @Before
    public void setup() throws SimpleStockException {
        stockA = new Stock("ALE", StockType.COMMON,23d,null,100d,120d);
        stockService.addStock(stockA);
        stockB = new Stock("POP", StockType.COMMON,8d,null,100d,120d);
        stockService.addStock(stockB);
        stockC = new Stock("GIN", StockType.PREFERRED,8d,0.02,100d,120d);
        stockService.addStock(stockC);
    }

    @After
    public void cleanup() throws SimpleStockException {
        stockService.removeStock(stockA);
        stockService.removeStock(stockB);
        stockService.removeStock(stockC);
    }

    @Test
    public void testCalculateDividedYield() throws SimpleStockException {
        T.info("<----Test testCalculateDividedYield is being executed---->");
        assertEquals(new Double(0.19), stockService.calculateDividendYield(stockA.getSymbol()));
        assertEquals(new Double(0.07), stockService.calculateDividendYield(stockB.getSymbol()));
        assertEquals(new Double(0.02), stockService.calculateDividendYield(stockC.getSymbol()));
        T.info("<----Test testCalculateDividedYield ended---->");
    }

    @Test(expected = SimpleStockException.class )
    public void testCalculateDividedYieldNullStock() throws SimpleStockException {
        stockService.calculateDividendYield("stockD");
    }

    @Test
    public void testCalculatePERatio() throws SimpleStockException {
        T.info("<----Test testCalculatePERatio is being executed---->");
        assertEquals(new Double(5.22), stockService.calculatePERatio(stockA.getSymbol()));
        assertEquals(new Double(15.0), stockService.calculatePERatio(stockB.getSymbol()));
        assertEquals(new Double(60), stockService.calculatePERatio(stockC.getSymbol()));
        T.info("<----Test testCalculatePERatio ended---->");

    }

    @Test(expected = SimpleStockException.class )
    public void testCalculatePERatioNullStock() throws SimpleStockException {
        stockService.calculatePERatio("stockD");
    }

    @Test
    public void testCalculateStockPrice() throws SimpleStockException {
        T.info("<----Test testCalculateStockPrice is being executed---->");
        StockTrade trade1 = new StockTrade(stockA, StockTradeType.BUY, Calendar.getInstance().getTime(), 2, 105d);
        stockService.recordTrade(trade1);
        StockTrade trade2 = new StockTrade(stockA, StockTradeType.BUY, Calendar.getInstance().getTime(),3,110d );
        stockService.recordTrade(trade2);
        StockTrade trade3 = new StockTrade(stockA, StockTradeType.BUY, Calendar.getInstance().getTime(), 5, 115d);
        stockService.recordTrade(trade3);
        assertEquals(new Double(111.5), stockService.calculatePrice(stockA.getSymbol()));
        T.info("<----Test testCalculateStockPrice ended---->");
    }

    @Test(expected = SimpleStockException.class )
    public void testCalculateStockPriceNullStock() throws SimpleStockException {
        stockService.calculatePrice("stockD");
    }


    @Test(expected = SimpleStockException.class )
    public void testCalculateStockPriceNoTrades() throws SimpleStockException {
        stockService.calculatePrice("stockA");
    }


    @Test
    public void testCalculateGBCE() throws SimpleStockException {
        T.info("<----Test testCalculateGBCE is being executed---->");
        StockTrade trade1 = new StockTrade(stockA, StockTradeType.BUY, Calendar.getInstance().getTime(), 2, 105d);
        stockService.recordTrade(trade1);
        StockTrade trade2 = new StockTrade(stockB, StockTradeType.BUY, Calendar.getInstance().getTime(),3,110d );
        stockService.recordTrade(trade2);
        StockTrade trade3 = new StockTrade(stockC, StockTradeType.BUY, Calendar.getInstance().getTime(), 5, 115d);
        stockService.recordTrade(trade3);
        assertEquals(new Double(109.92), stockService.calculateGBCEAllShareIndex());
        T.info("<----Test testCalculateGBCE ended---->");
    }

    @Test(expected = SimpleStockException.class)
    public void testCalculateGBCENoTrades() throws SimpleStockException {
        stockService.calculateGBCEAllShareIndex();
    }

    @Test
    public void testRecordTrades() throws SimpleStockException {
        T.info("<----Test testRecordTrades is being executed---->");
        StockTrade trade1 = new StockTrade(stockA, StockTradeType.BUY, Calendar.getInstance().getTime(), 2, 105d);
        stockService.recordTrade(trade1);
        StockTrade trade2 = new StockTrade(stockA, StockTradeType.BUY, Calendar.getInstance().getTime(),3,110d );
        stockService.recordTrade(trade2);
        StockTrade trade3 = new StockTrade(stockA, StockTradeType.BUY, Calendar.getInstance().getTime(), 5, 115d);
        stockService.recordTrade(trade3);
        StockTrade trade4 = new StockTrade(stockB, StockTradeType.BUY, Calendar.getInstance().getTime(), 2, 115d);
        stockService.recordTrade(trade4);
        StockTrade trade5 = new StockTrade(stockB, StockTradeType.BUY, Calendar.getInstance().getTime(), 8, 101d);
        stockService.recordTrade(trade5);

        List<StockTrade> tradesStockA = stockService.getStockTrades(stockA);
        assertNotNull("Trades for stockA are null",tradesStockA);
        assertEquals("Number of trades for stockA not the one expected",3,tradesStockA.size());

        List<StockTrade> tradesStockB = stockService.getStockTrades(stockB);
        assertNotNull("Trades for stockB are null",tradesStockB);
        assertEquals("Number of trades for stockB not the one expected",2,tradesStockB.size());

        T.info("<----Test testRecordTrades ended---->");
    }





}
