package com.info.mock;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.test.mockito.Portfolio;
import com.test.mockito.Stock;
import com.test.mockito.StockService;



public class PortfolioTester {
	StockService stockService;
	Portfolio portfolio;

	@Before
	public void setUp() {
		// creating portfolio obj
		portfolio = new Portfolio();
		// creating mock object
		stockService = mock(StockService.class);
		portfolio.setStockService(stockService);
	}

	@Test
	public void testMarketValue() {
		List<Stock> stocks = new ArrayList<Stock>();
//		Stock google = new Stock("1", "Google", 10);
//		Stock apple = new Stock("1", "apple", 150);
//		Stock microsoft = new Stock("1", "Microsoft", 100);

		Stock google = mock(Stock.class);
		Stock apple = mock(Stock.class);
		Stock microsoft = mock(Stock.class);
		
		stocks.add(apple);
		stocks.add(google);
		stocks.add(microsoft);

		portfolio.setStocks(stocks);

		// mock the behaviour of stock service to return the value
		when(google.getQuanitiy()).thenReturn(10);
		when(apple.getQuanitiy()).thenReturn(150);
		when(microsoft.getQuanitiy()).thenReturn(100);
		
		when(stockService.getPrice(google)).thenReturn(50.0);
		when(stockService.getPrice(apple)).thenReturn(1000.0);
		when(stockService.getPrice(microsoft)).thenReturn(500.0);

		double marketValue = portfolio.getMarketValue();

		System.out.println("total Market value : " + marketValue);

		assertEquals("calculation error", 200500.0, marketValue, 0.01);
		//return marketValue == 115000.0;
	}
}
