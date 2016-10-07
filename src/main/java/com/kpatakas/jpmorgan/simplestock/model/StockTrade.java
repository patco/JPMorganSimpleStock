package com.kpatakas.jpmorgan.simplestock.model;


import java.util.Date;

/**
 * Created by Kostas Patakas on 2/10/2016.
 */
public class StockTrade implements Comparable<StockTrade>{
    private final Stock stock;
    private final StockTradeType tradeType;
    private final Date tradeTimestamp;
    private final Integer quantity;
    private final Double price;

    public StockTrade(Stock stock,
                      StockTradeType tradeType,
                      Date tradeTimestamp,
                      Integer quantity,
                      Double price) {
        this.stock = stock;
        this.tradeType = tradeType;
        this.tradeTimestamp = tradeTimestamp;
        this.quantity = quantity;
        this.price = price;
    }

    public Stock getStock() {
        return stock;
    }

    public StockTradeType getTradeType() {
        return tradeType;
    }

    public Date getTradeTimestamp() {
        return tradeTimestamp;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockTrade that = (StockTrade) o;

        if (stock != null ? !stock.equals(that.stock) : that.stock != null) return false;
        if (tradeType != that.tradeType) return false;
        if (tradeTimestamp != null ? !tradeTimestamp.equals(that.tradeTimestamp) : that.tradeTimestamp != null)
            return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        return price != null ? price.equals(that.price) : that.price == null;

    }

    @Override
    public int hashCode() {
        int result = stock != null ? stock.hashCode() : 0;
        result = 31 * result + (tradeType != null ? tradeType.hashCode() : 0);
        result = 31 * result + (tradeTimestamp != null ? tradeTimestamp.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(StockTrade o) {
        return o.getTradeTimestamp().compareTo(this.getTradeTimestamp());
    }

    @Override
    public String toString() {
        return "StockTrade{" +
                "stock=" + stock +
                ", tradeType=" + tradeType +
                ", tradeTimestamp=" + tradeTimestamp +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
