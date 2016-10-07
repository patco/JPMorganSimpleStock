package com.kpatakas.jpmorgan.simplestock;

import com.kpatakas.jpmorgan.simplestock.repository.InMemoryStockRepositoryImpl;
import com.kpatakas.jpmorgan.simplestock.repository.InMemoryStockTradeRepositoryImpl;
import com.kpatakas.jpmorgan.simplestock.repository.StockRepository;
import com.kpatakas.jpmorgan.simplestock.repository.StockTradeRepository;
import com.kpatakas.jpmorgan.simplestock.service.StockService;
import com.kpatakas.jpmorgan.simplestock.service.StockServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JPMorganTestConfiguration {

    @Bean(name = "testStockService")
    public StockService StockService(){
        StockServiceImpl stockService = new StockServiceImpl();
        stockService.setStockRepository(StockRepository());
        stockService.setStockTradeRepository(StockTradeRepository());
        return stockService;
    }

    @Bean(name= "testStockRepository")
    public StockRepository StockRepository(){
        return new InMemoryStockRepositoryImpl();
    }

    @Bean(name = "testStockTradeRepository")
    StockTradeRepository StockTradeRepository(){
        return new InMemoryStockTradeRepositoryImpl();
    }

}
