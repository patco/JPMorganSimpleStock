package com.kpatakas.jpmorgan.simplestock.repository.exceptions;

import com.kpatakas.jpmorgan.simplestock.exception.SimpleStockException;

/**
 * Created by Kostas Patakas on 2/10/2016.
 */
public class StockRepositoryException extends SimpleStockException{

    public StockRepositoryException(String message) {
        super(message);
    }
}
