package cz.fi.muni.pa165.library.dao;

import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Impression;
import cz.fi.muni.pa165.library.exceptions.BookDAOException;
import java.util.Collection;
import java.util.Date;

/**
 * Data Access Object for managing entity Book.
 * 
 * @author Jaroslav Klech
 */
public interface BookDAO {
    
    /**
     * Saves given book.
     * 
     * @param book book to be saved
     * @throws BookDAOException is thrown when book is null or has ID
     */
    public void addBook(Book book) throws BookDAOException;
    
    /**
     * Updates given book
     * 
     * @param book book to be updated
     * @throws BookDAOException is thrown when book is null or doesn't exist in DB
     */
    public void updateBook(Book book) throws BookDAOException;
    
    /**
     * Deletes book
     * 
     * @param book book to be deleted
     * @throws BookDAOException is thrown when book is null, book has null id or book doesn't exist in DB
     */
    public void deleteBook(Book book) throws BookDAOException;
    
    /**
     * Finds book with given ID.
     * 
     * @param id id of searched book
     * @return book with given id
     */    
    public Book findBookById(Long id)throws BookDAOException;
    
    /**
     * Finds book with given ISBN.
     * 
     * @param isbn ISBN of searched book
     * @return book with given ISBN
     */
    public Book findBookByISBN(String isbn);
    
    /**
     * Finds books with given author
     * 
     * @param author author of the books
     * @return books with given author
     */
    public Collection<Book> findBooksByAuthor(String author);
    
    /**
     * Finds books by publish date.
     * 
     * @param publishDateFrom publish date to find books from
     * @param publishDateTo publish date to find books to
     * @return books with publish date between publishDateFrom and publishDateTo
     */
    public Collection<Book> findBooksByPublishDate(Date publishDateFrom, Date publishDateTo);
    
    /**
     * Finds books with given name
     * 
     * @param name name to find books with
     * @return books with given name
     */
    public Collection<Book> findBooksByName(String name);
    
    /**
     * Finds books by department
     * 
     * @param department department to find books by
     * @return books with given department
     */
    public Collection<Book> findBooksByDepartment(String department);
    
    /**
     * Finds books for given Impression
     * 
     * @param impression impression to find book for
     * @return Book for given impression
     */
    public Book findBookByImpression(Impression impression) throws BookDAOException;
}
