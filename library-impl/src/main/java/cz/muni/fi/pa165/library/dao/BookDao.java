package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.exceptions.BookDaoException;
import java.util.Collection;
import java.util.Date;

/**
 * Data Access Object for managing entity Book.
 * 
 * @author Jaroslav Klech
 */
public interface BookDao {
        
    /**
     * Saves given book.
     * 
     * @param book book to be saved
     */
    public void addBook(Book book);
    
    /**
     * Updates given book
     * 
     * @param book book to be updated
     */
    public void updateBook(Book book);
    
    /**
     * Deletes book
     * 
     * @param book book to be deleted
     */
    public void deleteBook(Book book);
    
    /**
     * Finds all books in database
     * 
     * @return list of all books
     */
    public Collection<Book> findAllBooks();
    
    /**
     * Finds book with given ID.
     * 
     * @param id id of searched book
     * @return book with given id
     */    
    public Book findBookById(Long id);
    
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
     * @throws BookDaoException
     * @return Book for given impression
     */
    public Book findBookByImpression(Impression impression);
    
}
