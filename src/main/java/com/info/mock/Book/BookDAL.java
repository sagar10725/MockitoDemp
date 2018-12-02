package com.info.mock.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookDAL {

	private static BookDAL bookDAL = new BookDAL();
	List<Book> bookList = new ArrayList<Book>();

	public List<Book> getAllBooks() {
		return Collections.EMPTY_LIST;
	}

	public Book getBook(String isbn) {
		return null;
	}

	public String addBook(Book book) {
		bookList.add(book);
		return book.getIsbn();
	}

	public String updateBook(Book book) {
		return book.getIsbn();
	}

	public static BookDAL getInstance() {
		return bookDAL;
	}

	public List<Book> getAllBook() {
		return bookList;
	}
}
