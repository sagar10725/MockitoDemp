package com.info.mock.Application;

import java.util.Arrays;

import com.info.mock.Book.Book;
import com.info.mock.Book.BookDAL;

public class BookApp {

	public static void main(String[] args) {

		Book book1 = new Book("123456", "Shree Hanuman Chalise", Arrays.asList("sagar"), "hira publication", 2016, 22,
				"Book image");
		Book book2 = new Book("8131721019", "Compilers Principles",
				Arrays.asList("D. Jeffrey Ulman", "Ravi Sethi", "Alfred V. Aho", "Monica S. Lam"),
				"Pearson Education Singapore Pte Ltd", 2008, 1009, "BOOK_IMAGE");

		System.out.println(book2.getIsbn());
		BookDAL dal = new BookDAL();
		dal.addBook(book1);
		dal.addBook(book2);

		for (Book b : dal.getAllBook()) {
			System.out.println(b.toString());
		}

	}

}
