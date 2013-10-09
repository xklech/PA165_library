package cz.fi.muni.pa165.library.dao;

import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Impression;
import cz.fi.muni.pa165.library.exceptions.BookDAOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Implementation of BookDAO interface.
 * 
 * @author Jaroslav Klech
 */
public class BookDAOImpl implements BookDAO{

    private EntityManager em;
    
    public BookDAOImpl(EntityManager em){
        this.em = em;
    }
    
    @Override
    public void addBook(Book book) throws BookDAOException {
        if(book == null){
            throw new BookDAOException("addBook: given book atribute is null");
        }
        if(book.getId() != null){
            throw new BookDAOException("addBook: given book has filled id");
        }
        em.persist(book);
    }

    @Override
    public void updateBook(Book book) throws BookDAOException {
        if(book == null){
            throw new BookDAOException("updateBook: given book atribute is null");
        }
        if(book.getId() == null){
            throw new BookDAOException("updateBook: given book has not filled id");
        }
        Book savedBook = em.find(Book.class, book.getId());
        if(savedBook == null){
            throw new BookDAOException("updateBook: given book doesn't exist in DB");
        }
        savedBook.setAuthor(book.getAuthor());
        savedBook.setDepartment(book.getDepartment());
        savedBook.setISBN(book.getISBN());
        savedBook.setImpressions(book.getImpressions());
        savedBook.setName(book.getName());
        savedBook.setPublishDate(book.getPublishDate());
    }

    @Override
    public void deleteBook(Book book) throws BookDAOException {
        if(book == null){
            throw new BookDAOException("deleteBook: given book atribute is null");
        }
        if(book.getId() == null){
            throw new BookDAOException("deleteBook: given book has not filled id");
        }
        Book savedBook = em.find(Book.class, book.getId());
        if(savedBook == null){
            throw new BookDAOException("deleteBook: given book doesn't exist in DB");
        } 
        em.remove(savedBook);
    }

    @Override
    public Book findBookById(Long id) throws BookDAOException {
        if(id == null){
            throw new BookDAOException("findBookById: given attribute id is null");
        }
        return em.find(Book.class, id);
    }

    @Override
    public Book findBookByISBN(String isbn) {
        TypedQuery query = em.createQuery("SELECT b FROM Book WHERE b.isbn = :isbn",Book.class);
        query.setParameter("isbn", isbn);
        List<Book> books = query.getResultList();
        if(books == null || books.isEmpty()){
            return null;
        }
        return books.get(0);
        
    }

    @Override
    public Collection<Book> findBooksByAuthor(String author) {
        TypedQuery query = em.createQuery("SELECT b FROM Book b WHERE b.author LIKE :author", Book.class);
        query.setParameter("author", "%"+author+"%");
        return query.getResultList();
    }

    @Override
    public Collection<Book> findBooksByPublishDate(Date publishDateFrom, Date publishDateTo) {
        TypedQuery query = em.createQuery("SELECT b FROM Book b WHERE b.publishDate > :publishDateFrom AND b.publishDate < :publishDateTo",Book.class);
        query.setParameter("publishDateFrom", publishDateFrom);
        query.setParameter("publishDateTo", publishDateTo);
        return query.getResultList();
    }

    @Override
    public Collection<Book> findBooksByName(String name) {
        TypedQuery query = em.createQuery("SELECT b FROM Book b WHERE b.name LIKE '%:name%'",Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public Collection<Book> findBooksByDepartment(String department) {
        TypedQuery query = em.createQuery("SELECT b FROM Book b WHERE b.department LIKE :department",Book.class);
        query.setParameter("department", "%"+department+"%");
        return query.getResultList();
    }

    @Override
    public Book findBookByImpression(Impression impression) throws BookDAOException{
        if(impression == null){
            throw new BookDAOException("findBookByImpression: given impression atribute is null");
        }
        if(impression.getId() == null){
            throw new BookDAOException("findBookByImpression: impressions id is null");
        }
        TypedQuery query = em.createQuery("SELECT b FROM Book b LEFT JOIN b.impressions i WHERE i.id = :id", Book.class);
        query.setParameter("id", impression.getId());
        return (Book)query.getSingleResult();
    }
    
}
