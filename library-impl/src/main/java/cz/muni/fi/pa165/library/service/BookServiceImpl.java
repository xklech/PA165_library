package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.ImpressionDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.ImpressionTo;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jaroslav Klech
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private ImpressionDao impressionDao;

    public void setBookDao(BookDao bookDao) {
	this.bookDao = bookDao;
    }

    public void setImpressionDao(ImpressionDao impressionDao) {
	this.impressionDao = impressionDao;
    }

    @Override
    public BookTo save(BookTo bookTo) {
	Book book = EntityConvertor.convertFromBookTo(bookTo);
	bookDao.addBook(book);
	bookTo.setId(book.getId());
	return bookTo;
    }

    @Override
    public BookTo updateBook(BookTo bookTo) {
	Book book = EntityConvertor.convertFromBookTo(bookTo);
	bookDao.updateBook(book);
	return bookTo;
    }

    @Override
    public boolean deleteBook(BookTo bookTo) {
	Book book = EntityConvertor.convertFromBookTo(bookTo);
	bookDao.deleteBook(book);
	return true;
    }

    @Override
    public List<BookTo> findAllBooks() {
	Collection<Book> books = this.bookDao.findAllBooks();
	if (books == null) {
	    return null;
	}
	List<BookTo> bookTos = new ArrayList();
	for (Book book : books) {
	    bookTos.add(EntityConvertor.convertFromBook(book));
	}
	return bookTos;
    }

    @Override
    public BookTo findBookById(Long id) {
	Book book = bookDao.findBookById(id);
	return EntityConvertor.convertFromBook(book);
    }

    @Override
    public List<BookTo> findBooksByName(String name) {
	Collection<Book> books;
	books = bookDao.findBooksByName(name);
	if (books == null) {
	    return null;
	}
	List<BookTo> booksTo = new ArrayList<>();
	for (Book book : books) {
	    booksTo.add(EntityConvertor.convertFromBook(book));
	}
	return booksTo;
    }

    @Override
    public BookTo findBookByISBN(String isbn) {
	Book book;
	book = bookDao.findBookByISBN(isbn);
	return EntityConvertor.convertFromBook(book);
    }

    @Override
    public List<BookTo> findBooksByAuthor(String author) {
	Collection<Book> books;
	books = bookDao.findBooksByAuthor(author);
	if (books == null) {
	    return null;
	}
	List<BookTo> booksTo = new ArrayList<>();
	for (Book book : books) {
	    booksTo.add(EntityConvertor.convertFromBook(book));
	}
	return booksTo;
    }

    @Override
    public List<BookTo> findBooksByPublishDate(Date publishDateFrom, Date publishDateTo) {
	Collection<Book> books = bookDao.findBooksByPublishDate(publishDateFrom, publishDateTo);
	if (books == null) {
	    return null;
	}
	List<BookTo> booksTo = new ArrayList<>();
	for (Book book : books) {
	    booksTo.add(EntityConvertor.convertFromBook(book));
	}
	return booksTo;
    }

    @Override
    public List<BookTo> findBooksByDepartment(String department) {
	Collection<Book> books;
	books = bookDao.findBooksByDepartment(department);
	if (books == null) {
	    return null;
	}
	List<BookTo> booksTo = new ArrayList<>();
	for (Book book : books) {
	    booksTo.add(EntityConvertor.convertFromBook(book));
	}
	return booksTo;
    }

    @Override
    public BookTo findBookByImpression(ImpressionTo impressionTo) {
	if (impressionTo == null) {
	    throw new IllegalArgumentException("impressionTo");
	}
	Book book;
	Impression impression = impressionDao.findImpressionById(impressionTo.getId());
	book = bookDao.findBookByImpression(impression);
	return EntityConvertor.convertFromBook(book);
    }

}
