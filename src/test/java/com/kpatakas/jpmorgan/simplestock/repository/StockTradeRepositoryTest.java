package com.kpatakas.jpmorgan.simplestock.repository;

import com.kpatakas.jpmorgan.simplestock.JPMorganTestConfiguration;
import com.kpatakas.jpmorgan.simplestock.model.Stock;
import com.kpatakas.jpmorgan.simplestock.model.StockTrade;
import com.kpatakas.jpmorgan.simplestock.model.StockTradeType;
import com.kpatakas.jpmorgan.simplestock.model.StockType;
import com.kpatakas.jpmorgan.simplestock.repository.exceptions.StockRepositoryException;
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

import static org.junit.Assert.assertEquals;

/**
 * Created by Kostas Patakas on 2/10/2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JPMorganTestConfiguration.class)
public class StockTradeRepositoryTest {

    private static final Logger T = LoggerFactory.getLogger(StockRepositoryTest.class);

    @Autowired
    private StockTradeRepository stockTradeRepository;

    @Autowired
    private StockRepository stockRepository;


    private Stock stockA;
    private Stock stockB;

    @Before
    public void setup(){
        stockA = new Stock("A", StockType.COMMON, 1d, 0d, 100d,300d);
        stockB = new Stock("B", StockType.PREFERRED, 1d, 0d, 100d,400d);
    }

    @After
    public void cleanUp(){
        stockTradeRepository.removeStockTrades(stockA);
        stockTradeRepository.removeStockTrades(stockB);
        stockRepository.removeStock(stockA);
        stockRepository.removeStock(stockB);
    }

    @Test
    public void testCreateStockTrades(){
        T.info("<----Test testCreateStockTrades is being executed---->");
        StockTrade trade = new StockTrade(stockA,StockTradeType.BUY, Calendar.getInstance().getTime(), 1, 1.0);
        stockTradeRepository.addStockTrade(trade);
        assertEquals(1, stockTradeRepository.getStockTrades(stockA).size());
        T.info("<----Test testCreateStockTrades ended---->");
    }

    @Test
    public void testRetrieveByTimeTrades() throws StockRepositoryException {
        T.info("<----Test testRetrieveByTimeTrades is being executed---->");

        StockTrade trade = new StockTrade(stockA,StockTradeType.BUY, Calendar.getInstance().getTime(), 1, 1.0);
        stockTradeRepository.addStockTrade(trade);

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -16);

        StockTrade trade2 = new StockTrade(stockA,StockTradeType.BUY, c.getTime(), 1, 1.0);
        stockTradeRepository.addStockTrade(trade2);
        assertEquals(1, stockTradeRepository.getStockTrades(stockA, 15).size());

        assertEquals(2, stockTradeRepository.getStockTrades(stockA).size());
        T.info("<----Test testRetrieveByTimeTrades ended---->");

    }


}
