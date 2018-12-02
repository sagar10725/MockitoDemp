package com.info.mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.info.mock.Book.Book;
import com.info.mock.Book.BookDAL;

public class BookDALTest {
	private static BookDAL mockedBookDAL;
	private static Book book1;
	private static Book book2;

	@BeforeClass
	public static void setUp() {
		// Create mock object of BookDAL
		mockedBookDAL = mock(BookDAL.class);

		// Create few instances of Book class.
		book1 = new Book("8131721019", "Compilers Principles",
				Arrays.asList("D. Jeffrey Ulman", "Ravi Sethi", "Alfred V. Aho", "Monica S. Lam"),
				"Pearson Education Singapore Pte Ltd", 2008, 1009, "BOOK_IMAGE");

		book2 = new Book("9788183331630", "Let Us C 13th Edition", Arrays.asList("Yamraj Kumar",""), "BPB PUBLICATIONS",
				2012, 675, "BOOK_IMAGE");

		// Stubbing the methods of mocked BookDAL with mocked data.
		when(mockedBookDAL.getAllBooks()).thenReturn(Arrays.asList(book1, book2));
		when(mockedBookDAL.getBook("8131721019")).thenReturn(book1);
		when(mockedBookDAL.addBook(book1)).thenReturn(book1.getIsbn());
		when(mockedBookDAL.updateBook(book1)).thenReturn(book1.getIsbn());
	}

	@Test
	public void testGetAllBooks() throws Exception {

		List<Book> bookList = mockedBookDAL.getAllBooks();
		assertEquals(2, bookList.size());
		Book myBook = bookList.get(0);
		assertEquals("8131721019", myBook.getIsbn());
		assertEquals("Compilers Principles", myBook.getTitle());
		assertEquals(4, myBook.getAuthors().size());
		assertEquals((Integer) 2008, myBook.getYearOfPublication());
		assertEquals((Integer) 1009, myBook.getNumberOfPages());
		assertEquals("Pearson Education Singapore Pte Ltd", myBook.getPublication());
		assertEquals("BOOK_IMAGE", myBook.getImage());
	}

	@Test
	public void testGetBook() throws Exception {
		String isbn = "8131721019";

		Book myBook = mockedBookDAL.getBook(isbn);

		assertNotNull(myBook);
		assertEquals(isbn, myBook.getIsbn());
		assertEquals("Compilers Principles", myBook.getTitle());
		assertEquals(4, myBook.getAuthors().size());
		assertEquals("Pearson Education Singapore Pte Ltd", myBook.getPublication());
		assertEquals((Integer) 2008, myBook.getYearOfPublication());
		assertEquals((Integer) 1009, myBook.getNumberOfPages());
	}

	@Test
	public void testAddBook() throws Exception {
		String isbn = mockedBookDAL.addBook(book1);
		assertNotNull(isbn);
		assertEquals(book1.getIsbn(), isbn);

	}

	@Test
	public void testUpdateBook() throws Exception {
		String isbn = mockedBookDAL.updateBook(book1);
		assertNotNull(isbn);
		assertEquals(book1.getIsbn(), isbn);

	}

}
