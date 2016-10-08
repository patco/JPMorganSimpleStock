package com.kpatakas.jpmorgan.simplestock.repository;

import com.kpatakas.jpmorgan.simplestock.model.Stock;
import com.kpatakas.jpmorgan.simplestock.repository.exceptions.StockRepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kostis on 2/10/2016.
 */
@Repository
public class InMemoryStockRepositoryImpl implements StockRepository {
    private static final Logger T = LoggerFactory.getLogger(InMemoryStockRepositoryImpl.class);

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
    public void addStock(Stock stock) throws StockRepositoryException {
        validateStock(stock);
        stockStorage.put(stock.getSymbol(),stock);
    }
    private void validateStock(Stock stock) throws StockRepositoryException {
        if (stock==null){
            T.error("Stock is empty.Please provide valid data");
            throw new StockRepositoryException("Stock is empty.Please provide valid data");
        }

        if(stock.getSymbol()==null){
            T.error("Stock symbol is empty.Please provide valid data");
            throw new StockRepositoryException("Stock symbol is empty.Please provide valid data");
        }

        if(stock.getStockType()==null){
            T.error("Stock type is empty.Please provide valid data");
            throw new StockRepositoryException("Stock type is empty.Please provide valid data");
        }

        if(stock.getLastDividend()==null){
            T.error("Stock last dividend is empty.Please provide valid data");
            throw new StockRepositoryException("Stock last dividend is empty.Please provide valid data");
        }

        if(stock.getParValue()==null){
            T.error("Stock Par value is empty.Please provide valid data");
            throw new StockRepositoryException("Stock Par value is empty.Please provide valid data");
        }

    }

    @Override
    public void removeStock(Stock stock) {
        stockStorage.remove(stock.getSymbol());
    }
}
