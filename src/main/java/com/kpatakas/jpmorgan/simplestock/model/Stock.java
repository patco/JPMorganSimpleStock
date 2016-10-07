package com.kpatakas.jpmorgan.simplestock.model;

/**
 * Created by Kostas Patakas on 2/10/2016.
 */
public class Stock {

    private final String symbol;
    private final StockType stockType;
    private final Double lastDividend;
    private final Double fixedDividend;
    private final Double parValue;
    private Double tickerPrice;

    public Stock(String symbol,
                 StockType stockType,
                 Double lastDividend,
                 Double fixedDividend,
                 Double parValue,
                 Double tickerPrice) {
        this.symbol = symbol;
        this.stockType = stockType;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
        this.tickerPrice = tickerPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public StockType getStockType() {
        return stockType;
    }

    public Double getLastDividend() {
        return lastDividend;
    }

    public Double getFixedDividend() {
        return fixedDividend;
    }

    public Double getParValue() {
        return parValue;
    }

    public Double getTickerPrice() {
        return tickerPrice;
    }

    public void setTickerPrice(Double tickerPrice) {
        this.tickerPrice = tickerPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stock stock = (Stock) o;

        if (symbol != null ? !symbol.equals(stock.symbol) : stock.symbol != null) return false;
        if (stockType != stock.stockType) return false;
        if (lastDividend != null ? !lastDividend.equals(stock.lastDividend) : stock.lastDividend != null) return false;
        if (fixedDividend != null ? !fixedDividend.equals(stock.fixedDividend) : stock.fixedDividend != null)
            return false;
        if (parValue != null ? !parValue.equals(stock.parValue) : stock.parValue != null) return false;
        return !(tickerPrice != null ? !tickerPrice.equals(stock.tickerPrice) : stock.tickerPrice != null);

    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (stockType != null ? stockType.hashCode() : 0);
        result = 31 * result + (lastDividend != null ? lastDividend.hashCode() : 0);
        result = 31 * result + (fixedDividend != null ? fixedDividend.hashCode() : 0);
        result = 31 * result + (parValue != null ? parValue.hashCode() : 0);
        result = 31 * result + (tickerPrice != null ? tickerPrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", stockType=" + stockType +
                ", lastDividend=" + lastDividend +
                ", fixedDividend=" + fixedDividend +
                ", parValue=" + parValue +
                ", tickerPrice=" + tickerPrice +
                '}';
    }
}
