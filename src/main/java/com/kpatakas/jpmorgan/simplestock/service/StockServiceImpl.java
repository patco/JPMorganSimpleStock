package com.kpatakas.jpmorgan.simplestock.service;

import com.kpatakas.jpmorgan.simplestock.exception.SimpleStockException;
import com.kpatakas.jpmorgan.simplestock.model.Stock;
import com.kpatakas.jpmorgan.simplestock.model.StockTrade;
import com.kpatakas.jpmorgan.simplestock.model.StockType;
import com.kpatakas.jpmorgan.simplestock.repository.StockRepository;
import com.kpatakas.jpmorgan.simplestock.repository.StockTradeRepository;
import com.kpatakas.jpmorgan.simplestock.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Kostas Patakas on 2/10/2016.
 */

@Service
public class StockServiceImpl implements StockService{

    private static final Logger T = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockTradeRepository stockTradeRepository;

    @Override
    public void addStock(Stock stock) throws SimpleStockException {
        if(stock==null){
            T.error("Stock data are not valid");
            throw new SimpleStockException("Stock data are not valid");
        }

        stockRepository.addStock(stock);
    }



    @Override
    public void removeStock(Stock stock) throws SimpleStockException {
        if(stock==null){
            T.error("Stock data are not valid");
            throw new SimpleStockException("Stock data are not valid");
        }
        stockTradeRepository.removeStockTrades(stock);
        stockRepository.removeStock(stock);
    }

    @Override
    public Double calculateDividendYield(String stockSymbol) throws SimpleStockException {
        Stock stock = getStock(stockSymbol);
        if(stock.getTickerPrice()==null){
            T.error("No Ticker Price is empty for " + stock.getSymbol());
            throw new SimpleStockException("No Ticker Price is empty for " + stock.getSymbol());
        }
        if (StockType.PREFERRED.equals(stock.getStockType())) {
            return Utils.divide(stock.getFixedDividend() * stock.getParValue(),stock.getTickerPrice());
        }
        return Utils.divide(stock.getLastDividend(), stock.getTickerPrice());

    }

    @Override
    public Double calculatePERatio(String stockSymbol) throws SimpleStockException {
        Stock stock = getStock(stockSymbol);
        if(stock.getTickerPrice()==null){
            throw new SimpleStockException("No Ticker Price for " + stock.getSymbol());
        }
        return Utils.divide(stock.getTickerPrice(),stock.getDividend());
    }

    @Override
    public Double calculatePrice(String stockSymbol) throws SimpleStockException {
        return calculateStockPrice(getStock(stockSymbol),15);
    }

    private Double calculateStockPrice(Stock stock,Integer timeOffset) throws SimpleStockException {
        List<StockTrade> trades;
        if(timeOffset==null){
            List<StockTrade> tradesSet = stockTradeRepository.getStockTrades(stock);
            if(tradesSet==null||tradesSet.isEmpty()){
                throw new SimpleStockException("No trades available for stock " + stock.getSymbol());
            }
            trades=stockTradeRepository.getStockTrades(stock);
        }else {
           trades = stockTradeRepository.getStockTrades(stock, timeOffset);
        }
        if(trades.isEmpty()){
            throw new SimpleStockException("No trades available for stock " + stock.getSymbol());
        }
        double quantitySum = 0.0;
        double tradePriceQuantitySum = 0.0;

        for (StockTrade stockTrade : trades) {
            tradePriceQuantitySum += stockTrade.getPrice()*stockTrade.getQuantity();
            quantitySum += stockTrade.getQuantity();
        }
        if(quantitySum==0){
            throw new SimpleStockException("No stocks of stock " +stock.getSymbol() +" were traded");
        }
        return Utils.divide(tradePriceQuantitySum,quantitySum);
    }

    @Override
    public Double calculateGBCEAllShareIndex() throws SimpleStockException {
        Map<String, Stock> stocks = stockRepository.findAll();
        if (stocks==null || stocks.isEmpty()) {
            throw new SimpleStockException("No stocks available");
        }
        ArrayList<Double> stockPrices = new ArrayList<>();
        for(Stock stock:stocks.values()){
            stockPrices.add(calculateStockPrice(stock, null));
        }
        return Utils.geometricMean(stockPrices);
    }

    @Override
    public void recordTrade(StockTrade trade) throws SimpleStockException {
        validateTrade(trade);
        T.info("------------------------------------------------");
        T.info("Trading stock : " + trade.getStock().getSymbol());
        T.info("------------------------------------------------");
        T.info("Transaction details: ");
        T.info(trade.toString());
        stockTradeRepository.addStockTrade(trade);
        trade.getStock().setTickerPrice(trade.getPrice());
        T.info("Trade for  stock : " + trade.getStock().getSymbol() +" completed.");
    }

    @Override
    public List<StockTrade> getStockTrades(Stock stock) {
        return stockTradeRepository.getStockTrades(stock);
    }


    private void validateTrade(StockTrade trade) throws SimpleStockException {
        if(trade==null){
            T.error("Trade is empty. Please provide valid data");
            throw new SimpleStockException("Trade is empty. Please provide valid data");
        }
        if(trade.getPrice()<=0){
            T.error("Trade is empty. Please provide valid data");
            throw new SimpleStockException("Trade price "+trade.getPrice()+" is not valid");
        }
        if(trade.getQuantity()<=0){
            throw new SimpleStockException("0 shares quantity is not allowded.");
        }
    }


    private Stock getStock(String stockSymbol)throws SimpleStockException{
        Stock stock = stockRepository.findBySymbol(stockSymbol);
        if(stock==null){
            throw new SimpleStockException("Stock [ " + stockSymbol +"] does not exist in the repository");
        }

        return stock;

    }

    public void setStockRepository(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void setStockTradeRepository(StockTradeRepository stockTradeRepository) {
        this.stockTradeRepository = stockTradeRepository;
    }
}
