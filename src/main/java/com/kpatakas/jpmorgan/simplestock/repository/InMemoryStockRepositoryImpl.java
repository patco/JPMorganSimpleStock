package com.kpatakas.jpmorgan.simplestock.repository;

import com.kpatakas.jpmorgan.simplestock.model.Stock;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kostis on 2/10/2016.
 */
@Repository
public class InMemoryStockRepositoryImpl implements StockRepository {

    private Map<String, Stock> stockStorage = new HashMap<String, Stock>();

    @Override
    public Stock findBySymbol(String symbol) {
        return stockStorage.get(symbol);
    }

    @Override
    public Map<String, Stock> findAll() {
        return stockStorage;
    }

    @Override
    public void addStock(Stock stock) {
        stockStorage.put(stock.getSymbol(),stock);
    }

    @Override
    public void removeStock(Stock stock) {
        stockStorage.remove(stock.getSymbol());
    }
}
