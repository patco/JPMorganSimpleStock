package com.kpatakas.jpmorgan.simplestock.repository;

import com.kpatakas.jpmorgan.simplestock.JPMorganTestConfiguration;
import com.kpatakas.jpmorgan.simplestock.model.Stock;
import com.kpatakas.jpmorgan.simplestock.model.StockType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
/**
 * Created by Kostas Patakas on 2/10/2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JPMorganTestConfiguration.class)
public class StockRepositoryTest {

    private static final Logger T = LoggerFactory.getLogger(StockRepositoryTest.class);

    @Autowired
    private StockRepository stockRepository;

    private Stock stock1;
    private Stock stock2;

    @Before
    public void setup(){
        stock1 = new Stock("KP", StockType.COMMON,1d,null,1d,100d);
        stock2 = new Stock("AP", StockType.PREFERRED,1d,null,1d,100d);
    }

    @After
    public void cleanup(){
        stockRepository.removeStock(stock1);
        stockRepository.removeStock(stock2);
    }

    @Test
    public void testAddRetrieveStockBySymbol(){
        T.info("<----Test testAddRetrieveStockBySymbol is being executed---->");
        stockRepository.addStock(stock1);
        assertNotNull("Stock not found in the repository",stockRepository.findBySymbol("KP"));
        assertEquals("Symbol retrieved is not the same as the one expected", stockRepository.findBySymbol("KP").getSymbol(), "KP");

        stockRepository.addStock(stock2);
        Map<String,Stock> stocks = stockRepository.findAll();
        assertTrue("Repository size is different from what expected", stocks.size()==2);
        T.info("<----Test testAddRetrieveStockBySymbol ended---->");
    }

}
