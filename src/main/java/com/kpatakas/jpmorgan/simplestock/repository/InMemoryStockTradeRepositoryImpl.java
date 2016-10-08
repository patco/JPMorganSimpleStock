package com.kpatakas.jpmorgan.simplestock.repository;

import com.kpatakas.jpmorgan.simplestock.model.Stock;
import com.kpatakas.jpmorgan.simplestock.model.StockTrade;
import com.kpatakas.jpmorgan.simplestock.repository.exceptions.StockRepositoryException;
import com.kpatakas.jpmorgan.simplestock.repository.exceptions.StockTradeRepositoryException;
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
    public void  addStockTrade(StockTrade trade) throws StockTradeRepositoryException {
        validateTrade(trade);
        String stock = trade.getStock().getSymbol();
        List<StockTrade> trades = new LinkedList<>();
        if(tradesStorage.containsKey(stock)){
           trades = tradesStorage.get(stock);
        }
        trades.add(trade);
        Collections.sort(trades);
        tradesStorage.put(stock,trades);
    }

    private void validateTrade(StockTrade trade) throws StockTradeRepositoryException {
        if(trade==null){
            T.error("Trade is empty. Please provide valid data");
            throw new StockTradeRepositoryException("Trade is empty. Please provide valid data");
        }
        if(trade.getStock()==null){
            T.error("Trade is invalid. Stock value is empty. Please provide valid data");
            throw new StockTradeRepositoryException("Trade is invalid. Stock value is empty. Please provide valid data");
        }

        if(trade.getTradeType()==null){
            T.error("Trade is invalid. Trade type is empty. Please provide valid data");
            throw new StockTradeRepositoryException("Trade is invalid. Trade type is empty. Please provide valid data");
        }

        if(trade.getPrice()==null){
            T.error("Trade is invalid. Price is empty. Please provide valid data");
            throw new StockTradeRepositoryException("Trade is invalid. Price is empty. Please provide valid data");
        }
        if(trade.getPrice()<=0){
            T.error("Trade is invalid. Price is negative. Please provide valid data");
            throw new StockTradeRepositoryException("Trade is invalid. Trade price "+trade.getPrice()+" is not valid");
        }

        if(trade.getQuantity()==null){
            T.error("Trade is invalid. Quantity is empty. Please provide valid data");
            throw new StockTradeRepositoryException("Trade is invalid. Trade price "+trade.getPrice()+" is not valid");
        }
        if(trade.getQuantity()<=0){
            T.error("Trade is invalid. Negative or 0 shares quantity is not allowed.");
            throw new StockTradeRepositoryException("Trade is invalid. Negative or 0 shares quantity is not allowed.");
        }

        if(trade.getTradeTimestamp()==null){
            T.error("Trade is invalid. Trade timestamp is empty. Please provide valid data");
            throw new StockTradeRepositoryException("Trade is invalid. Trade timestamp is empty. Please provide valid data");
        }

    }

    @Override
    public List<StockTrade> getStockTrades(Stock stock) {
        return tradesStorage.get(stock.getSymbol());
    }

    @Override
    public List<StockTrade> getStockTrades(Stock stock, Integer minutesOffset) throws StockRepositoryException{
        if(stock==null||stock.getSymbol()==null){
            T.error("Not a valid stock. Not deleting anything from repository");
            throw new StockRepositoryException("Invalid stock data(null stock or null stock symbol");
        }
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
        if(stock==null||stock.getSymbol()==null){
            T.error("Not a valid stock. Not deleting anything from repository");
            return;
        }
        tradesStorage.remove(stock.getSymbol());
    }

}
