package com.kpatakas.jpmorgan.simplestock.repository.exceptions;

import com.kpatakas.jpmorgan.simplestock.exception.SimpleStockException;

/**
 * Created by Kostis on 8/10/2016.
 */
public class StockTradeRepositoryException extends SimpleStockException {
    public StockTradeRepositoryException(String message) {
        super(message);
    }
}
