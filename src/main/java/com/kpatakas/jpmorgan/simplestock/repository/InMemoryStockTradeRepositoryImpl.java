package com.kpatakas.jpmorgan.simplestock.repository;

import com.kpatakas.jpmorgan.simplestock.model.Stock;
import com.kpatakas.jpmorgan.simplestock.model.StockTrade;
import com.kpatakas.jpmorgan.simplestock.repository.exceptions.StockRepositoryException;
import com.kpatakas.jpmorgan.simplestock.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by Kostas Patakas on 2/10/2016.
 */

@Repository
public class InMemoryStockTradeRepositoryImpl implements StockTradeRepository{

    private static final Logger T = LoggerFactory.getLogger(InMemoryStockTradeRepositoryImpl.class);

    private Map<String, List<StockTrade>> tradesStorage = new HashMap<>();

    @Override
    public void  addStockTrade(StockTrade trade) {
        String stock = trade.getStock().getSymbol();
        List<StockTrade> trades = new LinkedList<>();
        if(tradesStorage.containsKey(stock)){
           trades = tradesStorage.get(stock);
        }
        trades.add(trade);
        Collections.sort(trades);
        tradesStorage.put(stock,trades);

    }

    @Override
    public List<StockTrade> getStockTrades(Stock stock) {
        return tradesStorage.get(stock.getSymbol());
    }

    @Override
    public List<StockTrade> getStockTrades(Stock stock, Integer minutesOffset) throws StockRepositoryException{
        Date date = Utils.getDateByMinuteOffset(minutesOffset);
        List<StockTrade> result = new ArrayList<>();
        List<StockTrade> storedTrades = tradesStorage.get(stock.getSymbol());
        if (storedTrades == null){
            T.error("No trades found for stock " + stock.getSymbol());
            throw new StockRepositoryException("No trades found for stock " + stock.getSymbol());
        }
        for(StockTrade stockTrade:storedTrades){
            if (stockTrade.getTradeTimestamp().before(date)) {
                // Trades are ordered. Time to break.
                break;
            }
            result.add(stockTrade);
        }

        return result;
    }

    @Override
    public void removeStockTrades(Stock stock) {
        tradesStorage.remove(stock.getSymbol());
    }

}
