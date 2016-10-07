package com.kpatakas.jpmorgan.simplestock.repository;

import com.kpatakas.jpmorgan.simplestock.model.Stock;
import com.kpatakas.jpmorgan.simplestock.model.StockTrade;
import com.kpatakas.jpmorgan.simplestock.repository.exceptions.StockRepositoryException;

import java.util.List;

/**
 * Created by Kostas Patakas on 2/10/2016.
 */
public interface StockTradeRepository {

    /**
     * Add a new {@link StockTrade} in the repository
     * @param trade
     */
    void addStockTrade(StockTrade trade);

    /**
     * Get all {@link StockTrade} for a specific {@link Stock}
     * @param stock
     * @return List of {@link StockTrade}
     */
    List<StockTrade> getStockTrades(Stock stock);


    /**
     * Get all {@link StockTrade} for a specific {@link Stock} that was done in last minutesOffset minutes
     * @param stock
     * @param minutesOffset
     * @return List of {@link StockTrade}
     */
    List<StockTrade> getStockTrades(Stock stock, Integer minutesOffset) throws StockRepositoryException;

    public void removeStockTrades(Stock stock);
}
