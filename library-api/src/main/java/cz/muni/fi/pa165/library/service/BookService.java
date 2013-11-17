package cz.muni.fi.pa165.library.service;


import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.ImpressionTO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jaroslav Klech
 */
public interface BookService {
    /**
     * Saves given book.
     * 
     * @param bookTo book to be saved
     */
    public void save(BookTo bookTo);
    
    /**
     * Updates given book
     * 
     * @param bookTo book to be updated
     */
    public void updateBook(BookTo bookTo);
    
    /**
     * Deletes book
     * 
     * @param bookTo book to be deleted
     */
    public void deleteBook(BookTo bookTo);
    
    /**
     * Finds book with given ISBN.
     * 
     * @param isbn ISBN of searched book
     * @return book with given ISBN
     */
    public BookTo findBookByISBN(String isbn); 
    
    /**
     * Finds books with given author
     * 
     * @param author author of the books
     * @return books with given author
     */
    public List<BookTo> findBooksByAuthor(String author);
    /**
     * Finds books by publish date.
     * 
     * @param publishDateFrom publish date to find books from
     * @param publishDateTo publish date to find books to
     * @return books with publish date between publishDateFrom and publishDateTo
     */
    public List<BookTo> findBooksByPublishDate(Date publishDateFrom, Date publishDateTo);
        
    /**
     * Finds book with given ID.
     * 
     * @param id id of searched book
     * @return book with given id
     */    
    public BookTo findBookById(Long id);
     
    /**
     * Finds books with given name
     * 
     * @param name name to find books with
     * @return books with given name
     */  
    public List<BookTo> findBooksByName(String name);    
    
    /**
     * Finds books by department
     * 
     * @param department department to find books by
     * @return books with given department
     */
    public List<BookTo> findBooksByDepartment(String department);
    
    /**
     * Finds books for given Impression
     * 
     * @param impressionTo impression to find book for
     * @return Book for given impression
     */
    public BookTo findBookByImpression(ImpressionTO impressionTo);
    
}
