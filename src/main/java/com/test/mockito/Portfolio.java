package com.test.mockito;

import java.util.List;

public class Portfolio {
	private List<Stock> stocks;
	private StockService stockService;

	/**
	 * @return the stocks
	 */
	public List<Stock> getStocks() {
		return stocks;
	}

	/**
	 * @param stocks
	 *            the stocks to set
	 */
	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	/**
	 * @return the stockService
	 */
	public StockService getStockService() {
		return stockService;
	}

	/**
	 * @param stockService
	 *            the stockService to set
	 */
	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

	public double getMarketValue() {
		double mktValue = 0.0;
		for (Stock stock : stocks) {
			mktValue += stockService.getPrice(stock) * stock.getQuanitiy();

		}
		return mktValue;
	}
}
