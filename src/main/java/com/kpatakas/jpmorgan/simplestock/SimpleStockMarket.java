package com.kpatakas.jpmorgan.simplestock;

import com.kpatakas.jpmorgan.simplestock.exception.SimpleStockException;
import com.kpatakas.jpmorgan.simplestock.model.Stock;
import com.kpatakas.jpmorgan.simplestock.model.StockTrade;
import com.kpatakas.jpmorgan.simplestock.model.StockTradeType;
import com.kpatakas.jpmorgan.simplestock.model.StockType;
import com.kpatakas.jpmorgan.simplestock.service.StockService;
import com.kpatakas.jpmorgan.simplestock.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleStockMarket{
    private static final Logger T = LoggerFactory.getLogger(SimpleStockMarket.class);

    @Autowired
    StockService stockService;

    List<Stock> stocks;

    List<StockTrade> stockTrades;



    public void runTransactions(){
        setup();
        T.info("Simple stock Market started.");
        for(Stock stock:stocks){
            try {
                stockService.addStock(stock);
            } catch (SimpleStockException e) {
                T.error("Unable to add stock " + stock.getSymbol(),e);
            }
        }

        for(StockTrade stockTrade:stockTrades){
            try {
                stockService.recordTrade(stockTrade);
            } catch (SimpleStockException e) {
                T.error("Failed  to record  trade " + stockTrade.toString(),e);
            }
        }

        for(Stock stock:stocks){
            T.info("----Calculations for stock: " + stock.getSymbol() + " ------");
            calculateDividendYield(stock);
            calculatePERatio(stock);
            calculatePrice(stock);
        }
        calculateGBCEAllShareIndex();
    }

    private void calculateDividendYield(Stock stock){
        try {
            Double dy = stockService.calculateDividendYield(stock.getSymbol());
            T.info("Dividend Yield: [ " + dy +" ]");
        } catch (SimpleStockException e) {
            T.error("Failed  to calculate Dividend Yield for " + stock.getSymbol(),e);
        }
    }

    private void calculatePERatio(Stock stock){
        try {
            Double peRatio = stockService.calculatePERatio(stock.getSymbol());
            T.info("P/E Ratio: [ " + peRatio +" ]");
        } catch (SimpleStockException e) {
            T.error("Failed  to calculate P/E Ratio for " + stock.getSymbol(),e);
        }
    }
    private void calculatePrice(Stock stock){
        try {
            Double price = stockService.calculatePrice(stock.getSymbol());
            T.info("Stock price: [ " + price +" ]");
        } catch (SimpleStockException e) {
            T.error("Failed to calculate price for " + stock.getSymbol(),e);
        }
    }

    private void calculateGBCEAllShareIndex(){
        try {
            Double gbcea = stockService.calculateGBCEAllShareIndex();
            T.info("GBCE All Share Index: [ " + gbcea +" ]");
        } catch (SimpleStockException e) {
            T.error("Failed  to calculate  GBCEAllShareIndex " );
        }
    }


    private void setup(){
        stocks = new ArrayList<>();
        stockTrades = new ArrayList<>();
        //Stock TEA
        Stock stockTEA = new Stock("TEA", StockType.COMMON,0d,null,100d,100d);
        //Trades for TEA stock
        stocks.add(stockTEA);
        stockTrades.add(new StockTrade(stockTEA, StockTradeType.BUY, DateUtils.getDateByMinuteOffset(20),10,102d));
        stockTrades.add(new StockTrade(stockTEA, StockTradeType.SELL, DateUtils.getDateByMinuteOffset(2),5,210d));
        stockTrades.add(new StockTrade(stockTEA, StockTradeType.SELL, DateUtils.getDateByMinuteOffset(14),5,143d));
        stockTrades.add(new StockTrade(stockTEA, StockTradeType.BUY, DateUtils.getDateByMinuteOffset(5),8,158d));


        //Stock POP
        Stock stockPOP = new Stock("POP", StockType.COMMON,8d,null,100d,100d);
        stocks.add(stockPOP);
        //Trades for POP stock
        stockTrades.add(new StockTrade(stockPOP, StockTradeType.BUY, DateUtils.getDateByMinuteOffset(18),10,102d));
        stockTrades.add(new StockTrade(stockPOP, StockTradeType.SELL, DateUtils.getDateByMinuteOffset(14),5,190d));
        stockTrades.add(new StockTrade(stockPOP, StockTradeType.BUY, DateUtils.getDateByMinuteOffset(2),7,212d));
        stockTrades.add(new StockTrade(stockPOP, StockTradeType.SELL, DateUtils.getDateByMinuteOffset(1),5,310d));

        //Stock ALE
        Stock stockALE = new Stock("ALE", StockType.COMMON,23d,null,60d,60d);
        stocks.add(stockALE);
        //Trades for ALE stock
        stockTrades.add(new StockTrade(stockALE, StockTradeType.BUY, DateUtils.getDateByMinuteOffset(12),5,65d));
        stockTrades.add(new StockTrade(stockALE, StockTradeType.SELL, DateUtils.getDateByMinuteOffset(11),14,166d));
        stockTrades.add(new StockTrade(stockALE, StockTradeType.BUY, DateUtils.getDateByMinuteOffset(4),10,82d));
        stockTrades.add(new StockTrade(stockALE, StockTradeType.SELL, DateUtils.getDateByMinuteOffset(3),12,62d));

        //Stock GIN
        Stock stockGIN = new Stock("GIN", StockType.PREFERRED,8d,2d,100d,100d);
        stocks.add(stockGIN);
        //Trades for GIN stock
        stockTrades.add(new StockTrade(stockGIN, StockTradeType.BUY, DateUtils.getDateByMinuteOffset(25),5,165d));
        stockTrades.add(new StockTrade(stockGIN, StockTradeType.SELL, DateUtils.getDateByMinuteOffset(22),14,166d));
        stockTrades.add(new StockTrade(stockGIN, StockTradeType.BUY, DateUtils.getDateByMinuteOffset(3),10,182d));
        stockTrades.add(new StockTrade(stockGIN, StockTradeType.SELL, DateUtils.getDateByMinuteOffset(1),12,162d));

        //Stock JOE
        Stock stockJOE = new Stock("JOE", StockType.COMMON,13d,null,250d,250d);
        stocks.add(stockJOE);
        //Trades for JOE stock
        stockTrades.add(new StockTrade(stockJOE, StockTradeType.BUY, DateUtils.getDateByMinuteOffset(10),5,261d));
        stockTrades.add(new StockTrade(stockJOE, StockTradeType.SELL, DateUtils.getDateByMinuteOffset(8),14,266d));
        stockTrades.add(new StockTrade(stockJOE, StockTradeType.BUY, DateUtils.getDateByMinuteOffset(5),10,282d));
        stockTrades.add(new StockTrade(stockGIN, StockTradeType.SELL, DateUtils.getDateByMinuteOffset(4),12,312d));

    }

}
