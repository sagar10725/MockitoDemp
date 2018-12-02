package com.test.mockito;

public class Stock {

	private String stockId;
	private String name;
	private int quanitiy;

	/**
	 * @param stockId
	 * @param name
	 * @param quanitiy
	 */
	public Stock(String stockId, String name, int quanitiy) {
		this.stockId = stockId;
		this.name = name;
		this.quanitiy = quanitiy;
	}

	/**
	 * @return the stockId
	 */
	public String getStockId() {
		return stockId;
	}

	/**
	 * @param stockId
	 *            the stockId to set
	 */
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the quanitiy
	 */
	public int getQuanitiy() {
		return quanitiy;
	}

	/**
	 * @param quanitiy
	 *            the quanitiy to set
	 */
	public void setQuanitiy(int quanitiy) {
		this.quanitiy = quanitiy;
	}

}
