package com.kpatakas.jpmorgan.simplestock.repository;

import com.kpatakas.jpmorgan.simplestock.model.Stock;

import java.util.Map;

/**
 * Created by Kostas Patakas on 2/10/2016.
 */
public interface StockRepository {

    /**
     * Finds a {@link Stock} in the repository using stock symbol
     * @param symbol
     * @return
     */
    Stock findBySymbol(String symbol);


    /**
     * Finds all available {@link Stock} in the repository
     * @return
     */
    Map<String, Stock> findAll();

    /**
     * Adds a new {@link Stock} in the repository
     * @param stock
     */
    void addStock(Stock stock);

    /**
     * Removes a stock from the repository
     * @param stock
     */
    void removeStock(Stock stock);


}
