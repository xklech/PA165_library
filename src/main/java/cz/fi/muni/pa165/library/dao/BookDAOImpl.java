package cz.fi.muni.pa165.library.dao;

import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Impression;
import cz.fi.muni.pa165.library.exceptions.BookDaoException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of BookDao interface.
 *
 * @author Jaroslav Klech
 */
public class BookDaoImpl implements BookDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.entityManagerFactory = emf;
    }


    public EntityManagerFactory getEmf() {
        return entityManagerFactory;
    }

    
    
    @Override
    public void addBook(Book book) throws BookDaoException {
        if (book == null) {
            throw new BookDaoException("addBook: given book atribute is null");
        }
        if (book.getId() != null) {
            throw new BookDaoException("addBook: given book has filled id");
        }
        System.err.println("dao:addbook called");
        EntityManager em = entityManagerFactory.createEntityManager();
        em.persist(book);
        em.flush();
        em.close();
    }

    @Override
    public void updateBook(Book book) throws BookDaoException {
        EntityManager em = entityManagerFactory.createEntityManager();
        if (book == null) {
            throw new BookDaoException("updateBook: given book atribute is null");
        }
        if (book.getId() == null) {
            throw new BookDaoException("updateBook: given book has not filled id");
        }
        Book savedBook = em.find(Book.class, book.getId());
        if (savedBook == null) {
            throw new BookDaoException("updateBook: given book doesn't exist in DB");
        }
        savedBook.setAuthor(book.getAuthor());
        savedBook.setDepartment(book.getDepartment());
        savedBook.setISBN(book.getISBN());
        savedBook.setImpressions(book.getImpressions());
        savedBook.setName(book.getName());
        savedBook.setPublishDate(book.getPublishDate());
        em.close();
    }

    @Override
    public void deleteBook(Book book) throws BookDaoException {
        EntityManager em = entityManagerFactory.createEntityManager();
        if (book == null) {
            throw new BookDaoException("deleteBook: given book atribute is null");
        }
        if (book.getId() == null) {
            throw new BookDaoException("deleteBook: given book has not filled id");
        }
        Book savedBook = em.find(Book.class, book.getId());
        if (savedBook == null) {
            throw new BookDaoException("deleteBook: given book doesn't exist in DB");
        }
        em.remove(savedBook);
        em.close();
    }

    @Override
    public Book findBookById(Long id) throws BookDaoException {
        EntityManager em = entityManagerFactory.createEntityManager();
        if (id == null) {
            throw new BookDaoException("findBookById: given attribute id is null");
        }
        Book book = em.find(Book.class, id);
        em.close();
        return book;
    }

    @Override
    public Book findBookByISBN(String isbn) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
        query.setParameter("isbn", isbn);
        List<Book> books = query.getResultList();
        if (books == null || books.isEmpty()) {
            return null;
        }
        em.close();
        return books.get(0);

    }

    @Override
    public Collection<Book> findBooksByAuthor(String author) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery query = em.createQuery("SELECT b FROM Book b WHERE b.author LIKE :author", Book.class);
        query.setParameter("author", "%" + author + "%");
        em.close();
        return query.getResultList();
    }

    @Override
    public Collection<Book> findBooksByPublishDate(Date publishDateFrom, Date publishDateTo) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery query = em.createQuery("SELECT b FROM Book b WHERE b.publishDate > :publishDateFrom AND b.publishDate < :publishDateTo", Book.class);
        query.setParameter("publishDateFrom", publishDateFrom);
        query.setParameter("publishDateTo", publishDateTo);
        em.close();
        return query.getResultList();
    }

    @Override
    public Collection<Book> findBooksByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery query = em.createQuery("SELECT b FROM Book b WHERE b.name LIKE :name", Book.class);
        query.setParameter("name", "%" + name + "%");
        List<Book>list = query.getResultList();
        em.close();
        return list;
    }

    @Override
    public Collection<Book> findBooksByDepartment(String department) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery query = em.createQuery("SELECT b FROM Book b WHERE b.department LIKE :department", Book.class);
        query.setParameter("department", "%" + department + "%");
        em.close();
        return query.getResultList();
    }

    @Override
    public Book findBookByImpression(Impression impression) throws BookDaoException {
        EntityManager em = entityManagerFactory.createEntityManager();
        if (impression == null) {
            throw new BookDaoException("findBookByImpression: given impression atribute is null");
        }
        if (impression.getId() == null) {
            throw new BookDaoException("findBookByImpression: impressions id is null");
        }
        TypedQuery query = em.createQuery("SELECT b FROM Book b LEFT JOIN b.impressions i WHERE i.id = :id", Book.class);
        query.setParameter("id", impression.getId());
        em.close();
        return (Book) query.getSingleResult();
    }

}
