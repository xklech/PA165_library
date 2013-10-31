package cz.muni.fi.pa165.library.service;


import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.ImpressionDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.exceptions.BookDaoException;
import cz.muni.fi.pa165.library.exceptions.ImpressionDaoException;
import cz.muni.fi.pa165.library.exceptions.ServiceDataAccesException;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.ImpressionTO;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jaroslav Klech
 */
@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;
    
    @Autowired
    private ImpressionDao impressionDao;
    
    @Transactional
    @Override
    public void save(BookTo bookTo){  
        try {
            Book book = EntityConvertor.convertFromBookTo(bookTo);
            bookDao.addBook(book);
            bookTo.setId(book.getId());
        } catch (BookDaoException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("Save book",ex);
        }
    }
    

    @Override
    public BookTo findBookById(Long id){
        Book book = null;
        try{
            book = bookDao.findBookById(id);
        } catch (BookDaoException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("findBookById",ex);
        }
        return EntityConvertor.convertFromBook(book);
    }
    
    @Override
    public List<BookTo> findBooksByName(String name){
       Collection<Book> books;
        try {
            books = bookDao.findBooksByName(name);
        } catch (BookDaoException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("findBooksByName",ex);
        }
       if(books == null){
           return null;
       }
       List<BookTo> booksTo = new ArrayList<>();
       for(Book book:books){
           booksTo.add(EntityConvertor.convertFromBook(book));
       }
       return booksTo;
    }

    @Override
    public void updateBook(BookTo bookTo) {
        Book book = EntityConvertor.convertFromBookTo(bookTo);
        try {
            bookDao.updateBook(book);
        } catch (BookDaoException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("updateBook",ex);
        }
    }

    @Override
    public void deleteBook(BookTo bookTo) {
        Book book = EntityConvertor.convertFromBookTo(bookTo);
        try {
            bookDao.deleteBook(book);
        } catch (BookDaoException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("updateBook",ex);
        }
    }

    @Override
    public BookTo findBookByISBN(String isbn) {
        Book book;
        try {
            book = bookDao.findBookByISBN(isbn);
        } catch (BookDaoException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("findBookByISBN",ex);
        }
        return EntityConvertor.convertFromBook(book);
    }

    @Override
    public List<BookTo> findBooksByAuthor(String author) {
        Collection<Book> books;
        try {
            books = bookDao.findBooksByAuthor(author);
        } catch (BookDaoException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("findBooksByAuthor",ex);
        }
        List<BookTo> booksTo = new ArrayList<>();
        for(Book book : books){
            booksTo.add(EntityConvertor.convertFromBook(book));
        }
        return booksTo;
    }

    @Override
    public List<BookTo> findBooksByPublishDate(Date publishDateFrom, Date publishDateTo) {
        Collection<Book> books = bookDao.findBooksByPublishDate(publishDateFrom, publishDateTo);
        List<BookTo> booksTo = new ArrayList<>();
        for(Book book : books){
            booksTo.add(EntityConvertor.convertFromBook(book));
        }
        return booksTo;
    }


    @Override
    public List<BookTo> findBooksByDepartment(String department) {
        Collection<Book> books;
        try {
            books = bookDao.findBooksByDepartment(department);
        } catch (BookDaoException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("findBooksByDepartment",ex);
        }
        List<BookTo> booksTo = new ArrayList<>();
        for(Book book : books){
            booksTo.add(EntityConvertor.convertFromBook(book));
        }
        return booksTo;
    }

    @Override
    public BookTo findBookByImpression(ImpressionTO impressionTo) {

        Book book = null;
        try {
            Impression impression = impressionDao.findImpressionById(impressionTo.getId());
            book = bookDao.findBookByImpression(impression);
        } catch (BookDaoException | ImpressionDaoException ex) {
            Logger.getLogger(BookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccesException("findBookByImpression",ex);
        }
        return EntityConvertor.convertFromBook(book);
    }
    
    
    
}
