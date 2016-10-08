# JPMorganSimpleStock
Simple Stock interview exercise for JP Morgan

## Requirements
Provide working source code that will :

For a given stock, 

1. Calculate the dividend yield
2. Calculate the P/E Ratio
3. Record a trade, with timestamp, quantity of shares, buy or sell indicator and price
4. Calculate Stock Price based on trades recorded in past 15 minutes
5. Calculate the GBCE All Share Index using the geometric mean of prices for all stocks

### Table1. Sample data from the Global Beverage Corporation Exchange

Stock Symbol | Type | Last Dividend | Fixed Dividend | Par Value
------------ | ------------- | ------------ | -------------| ------------ 
TEA | Common| 0 | | 100
POP | Common| 8 | | 100
ALE | Common| 23 | | 60
GIN | Preferred| 8 | 2% | 100
JOE | Common| 13 | | 250

## Requirements

### Usage
1. To build the project:
*gradlew build*

2. To run tests:
*gradlew test*

3. To run the project:
*gradlew bootRun*

4. To add new stocks and trades please edit method in java file **com.kpatakas.jpmorgan.simplestock.SimpleStockMarket#setup**


