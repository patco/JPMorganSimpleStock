package com.kpatakas.jpmorgan.simplestock;

import com.kpatakas.jpmorgan.simplestock.repository.StockTradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class JpMorganSimpleStockApplication implements ApplicationRunner{

	@Autowired
	SimpleStockMarket simpleStockMarket;
	public static void main(String[] args) {
		SpringApplication.run(JpMorganSimpleStockApplication.class, args);
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
		simpleStockMarket.runTransactions();
	}
}
