package cz.fi.muni.pa165.library.dao;

import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Impression;
import cz.fi.muni.pa165.library.exceptions.BookDaoException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of BookDao interface.
 *
 * @author Jaroslav Klech
 */
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    
    
    @Override
    public void addBook(Book book) throws BookDaoException {
        if (book == null) {
            throw new BookDaoException("addBook: given book atribute is null");
        }
        if (book.getId() != null) {
            throw new BookDaoException("addBook: given book has filled id");
        }
        System.err.println("dao:addbook called");
        entityManager.persist(book);

    }

    @Override
    public void updateBook(Book book) throws BookDaoException {
        if (book == null) {
            throw new BookDaoException("updateBook: given book atribute is null");
        }
        if (book.getId() == null) {
            throw new BookDaoException("updateBook: given book has not filled id");
        }
        Book savedBook = entityManager.find(Book.class, book.getId());
        if (savedBook == null) {
            throw new BookDaoException("updateBook: given book doesn't exist in DB");
        }
        savedBook.setAuthor(book.getAuthor());
        savedBook.setDepartment(book.getDepartment());
        savedBook.setISBN(book.getISBN());
        savedBook.setImpressions(book.getImpressions());
        savedBook.setName(book.getName());
        savedBook.setPublishDate(book.getPublishDate());
    }

    @Override
    public void deleteBook(Book book) throws BookDaoException {

        if (book == null) {
            throw new BookDaoException("deleteBook: given book atribute is null");
        }
        if (book.getId() == null) {
            throw new BookDaoException("deleteBook: given book has not filled id");
        }
        Book savedBook = entityManager.find(Book.class, book.getId());
        if (savedBook == null) {
            throw new BookDaoException("deleteBook: given book doesn't exist in DB");
        }
        entityManager.remove(savedBook);

    }

    @Override
    public Book findBookById(Long id) throws BookDaoException {

        if (id == null) {
            throw new BookDaoException("findBookById: given attribute id is null");
        }
        Book book = entityManager.find(Book.class, id);

        return book;
    }

    @Override
    public Book findBookByISBN(String isbn) {

        TypedQuery query = entityManager.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
        query.setParameter("isbn", isbn);
        List<Book> books = query.getResultList();
        if (books == null || books.isEmpty()) {
            return null;
        }

        return books.get(0);

    }

    @Override
    public Collection<Book> findBooksByAuthor(String author) {

        TypedQuery query = entityManager.createQuery("SELECT b FROM Book b WHERE b.author LIKE :author", Book.class);
        query.setParameter("author", "%" + author + "%");

        return query.getResultList();
    }

    @Override
    public Collection<Book> findBooksByPublishDate(Date publishDateFrom, Date publishDateTo) {

        TypedQuery query = entityManager.createQuery("SELECT b FROM Book b WHERE b.publishDate > :publishDateFrom AND b.publishDate < :publishDateTo", Book.class);
        query.setParameter("publishDateFrom", publishDateFrom);
        query.setParameter("publishDateTo", publishDateTo);

        return query.getResultList();
    }

    @Override
    public Collection<Book> findBooksByName(String name) {

        TypedQuery query = entityManager.createQuery("SELECT b FROM Book b WHERE b.name LIKE :name", Book.class);
        query.setParameter("name", "%" + name + "%");
        List<Book>list = query.getResultList();

        return list;
    }

    @Override
    public Collection<Book> findBooksByDepartment(String department) {

        TypedQuery query = entityManager.createQuery("SELECT b FROM Book b WHERE b.department LIKE :department", Book.class);
        query.setParameter("department", "%" + department + "%");

        return query.getResultList();
    }

    @Override
    public Book findBookByImpression(Impression impression) throws BookDaoException {
 
        if (impression == null) {
            throw new BookDaoException("findBookByImpression: given impression atribute is null");
        }
        if (impression.getId() == null) {
            throw new BookDaoException("findBookByImpression: impressions id is null");
        }
        TypedQuery query = entityManager.createQuery("SELECT b FROM Book b LEFT JOIN b.impressions i WHERE i.id = :id", Book.class);
        query.setParameter("id", impression.getId());

        return (Book) query.getSingleResult();
    }

}
