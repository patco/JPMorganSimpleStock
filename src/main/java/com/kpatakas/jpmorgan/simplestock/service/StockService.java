package com.kpatakas.jpmorgan.simplestock.service;

import com.kpatakas.jpmorgan.simplestock.exception.SimpleStockException;
import com.kpatakas.jpmorgan.simplestock.model.Stock;
import com.kpatakas.jpmorgan.simplestock.model.StockTrade;

import java.util.List;

/**
 * Created by Kostas Patakas on 2/10/2016.
 */

public interface StockService {

    public void addStock(Stock stock) throws SimpleStockException;

    public void removeStock(Stock stock) throws SimpleStockException;
    /**
     * Calculates the Dividend Yield for a specific {@link Stock} defined by stockSymbol
     * @param stockSymbol Symbol that uniquely defines a STOCK
     * @return Double value that represents the  Dividend Yield
     */
    Double calculateDividendYield(String stockSymbol) throws SimpleStockException;

    /**
     * Calculates the P/E Ratio for a specific {@link Stock} defined by stockSymbol
     * @param stockSymbol Symbol that uniquely defines a STOCK
     * @return Double value that represents the P/E Ratio
     */
    public Double calculatePERatio(String stockSymbol) throws SimpleStockException;


    /**
     * Calculates the {@link Stock} price based on trades recorded in past 15 minutes.
     * @param stockSymbol Symbol that uniquely defines a STOCK
     * @return Double value that represents the stock price
     */
    public Double calculatePrice(String stockSymbol) throws SimpleStockException;

    /**
     * Calculates the GBCE All Share Index using the geometric mean of prices for all stocks
     * @return
     */
    public Double calculateGBCEAllShareIndex() throws SimpleStockException;

    public void recordTrade(StockTrade trade) throws SimpleStockException;

    public List<StockTrade> getStockTrades(Stock stock);


}
