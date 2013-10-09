package cz.fi.muni.pa165.library.dao;

import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Impression;
import cz.fi.muni.pa165.library.exceptions.BookDAOException;
import java.util.Collection;
import java.util.Date;

/**
 * Implementation of BookDAO interface.
 * 
 * @author Jaroslav Klech
 */
public class BookDAOImpl implements BookDAO{

    @Override
    public void addBook(Book book) throws BookDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateBook(Book book) throws BookDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteBook(Book book) throws BookDAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Book findBookById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Book findBookByISBN(String ISBN) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Book> findBookByAuthor(String author) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Book> findBooksByPublishDate(Date publishDateFrom, Date publishDateTo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Book> findBooksByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Book> findBooksByDepartment(String department) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Book findBookByImpression(Impression impression) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
