package com.kpatakas.jpmorgan.simplestock.service;

import com.kpatakas.jpmorgan.simplestock.exception.SimpleStockException;
import com.kpatakas.jpmorgan.simplestock.model.Stock;
import com.kpatakas.jpmorgan.simplestock.model.StockTrade;
import com.kpatakas.jpmorgan.simplestock.model.StockType;
import com.kpatakas.jpmorgan.simplestock.repository.StockRepository;
import com.kpatakas.jpmorgan.simplestock.repository.StockTradeRepository;
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
    StockRepository stockRepository;

    @Autowired
    StockTradeRepository stockTradeRepository;

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
            return round((stock.getFixedDividend() * stock.getParValue()) / stock.getTickerPrice());
        }
        return round(stock.getLastDividend() / stock.getTickerPrice());

    }

    @Override
    public Double calculatePERatio(String stockSymbol) throws SimpleStockException {
        Stock stock = getStock(stockSymbol);
        if(stock.getTickerPrice()==null){
            throw new SimpleStockException("No Ticker Price for " + stock.getSymbol());
        }
        Double dy = calculateDividendYield(stockSymbol);
        if(dy==null || dy.equals(0d)){
            throw new SimpleStockException("Unable to calculate valid value for Dividend Yield");

        }
        return round(stock.getTickerPrice()/calculateDividendYield(stockSymbol));
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
        return round(tradePriceQuantitySum/quantitySum);
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
        return round(geometricMean(stockPrices));
    }

    @Override
    public void recordTrade(StockTrade trade) throws SimpleStockException {
        validateTrade(trade);
        T.debug("Trading stock : " + trade.getStock().getSymbol());
        T.debug("------------------------------------------------\n");
        T.debug("------------------------------------------------\n");
        T.debug("Transaction details: \n");
        T.debug(trade.toString());
        stockTradeRepository.addStockTrade(trade);
        trade.getStock().setTickerPrice(trade.getPrice());
        T.debug("Trade for  stock : " + trade.getStock().getSymbol() +" completed.\n");
        T.debug("------------------------------------------------\n");
        T.debug("------------------------------------------------\n");
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

    private Double round(Double value){
        return new BigDecimal(String.valueOf(value)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private Double geometricMean(ArrayList<Double> data)
    {
        double sum = data.get(0);

        for (int i = 1; i < data.size(); i++) {
            sum *= data.get(i);
        }
        return Math.pow(sum, 1.0 / data.size());
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
