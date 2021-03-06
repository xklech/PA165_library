package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Impression;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

/**
 * Implementation of BookDao interface.
 *
 * @author Jaroslav Klech
 */
@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("addBook: given book atribute is null");
        }
        if (book.getId() != null) {
            throw new IllegalArgumentException("addBook: given book has filled id");
        }
        entityManager.persist(book);
    }

    @Override
    public void updateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("updateBook: given book atribute is null");
        }
        if (book.getId() == null) {
            throw new IllegalArgumentException("updateBook: given book has not filled id");
        }
        Book savedBook = entityManager.find(Book.class, book.getId());
        if (savedBook == null) {
            throw new IllegalArgumentException("updateBook: given book doesn't exist in DB");
        }
        savedBook.setAuthor(book.getAuthor());
        savedBook.setDepartment(book.getDepartment());
        savedBook.setISBN(book.getISBN());
        savedBook.setName(book.getName());
        savedBook.setPublishDate(book.getPublishDate());
    }

    @Override
    public void deleteBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("deleteBook: given book atribute is null");
        }
        if (book.getId() == null) {
            throw new IllegalArgumentException("deleteBook: given book has not filled id");
        }
        Book savedBook = entityManager.find(Book.class, book.getId());
        if (savedBook == null) {
            throw new IllegalArgumentException("deleteBook: given book doesn't exist in DB");
        }
        entityManager.remove(savedBook);
    }

    @Override
    public List<Book> findAllBooks() {
        TypedQuery query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }
    
    @Override
    public Book findBookById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("findBookById: given attribute id is null");
        }
        Book book = entityManager.find(Book.class, id);
        return book;
    }

    @Override
    public Book findBookByISBN(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("findBookByISBN: given attribute isbn is null");
        }
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
        if (author == null) {
            throw new IllegalArgumentException("findBooksByAuthor: given attribute author is null");
        }
        TypedQuery query = entityManager.createQuery("SELECT b FROM Book b WHERE b.author LIKE :author", Book.class);
        query.setParameter("author", "%" + author + "%");
        return query.getResultList();
    }

    @Override
    public Collection<Book> findBooksByPublishDate(Date publishDateFrom, Date publishDateTo) {
	CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
	CriteriaQuery<Book> cq = cb.createQuery(Book.class);
	Root r = cq.from(Book.class);
	if (publishDateFrom == null && publishDateTo == null) {
	    cq.select(r);
	} else {
	    if (publishDateFrom == null) {
		cq.where(cb.lessThanOrEqualTo(r.get("publishDate"),publishDateTo));
	    } else if (publishDateTo == null) {
		cq.where(cb.greaterThanOrEqualTo(r.get("publishDate"),publishDateFrom));
	    } else {
		cq.where(cb.greaterThanOrEqualTo(r.get("publishDate"),publishDateFrom),cb.lessThanOrEqualTo(r.get("publishDate"),publishDateTo));
	    }
	}
	TypedQuery q = this.entityManager.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public Collection<Book> findBooksByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("findBooksByName: given attribute name is null");
        }
        TypedQuery query = entityManager.createQuery("SELECT b FROM Book b WHERE b.name LIKE :name", Book.class);
        query.setParameter("name", "%" + name + "%");
        List<Book>list = query.getResultList();

        return list;
    }

    @Override
    public Collection<Book> findBooksByDepartment(String department) {
        if (department == null) {
            throw new IllegalArgumentException("findBooksByDepartment: given attribute department is null");
        }
        TypedQuery query = entityManager.createQuery("SELECT b FROM Book b WHERE b.department LIKE :department", Book.class);
        query.setParameter("department", "%" + department + "%");

        return query.getResultList();
    }

    @Override
    public Book findBookByImpression(Impression impression) {
        if (impression == null) {
            throw new IllegalArgumentException("Impression: given attribute impression is null");
        }
	if (impression.getId() == null) {
	    throw new IllegalArgumentException("Impression.id: given attribute impression is null");
	}
        TypedQuery query = entityManager.createQuery("SELECT b FROM Impression i LEFT JOIN i.book b WHERE i.id = :id", Book.class);
        query.setParameter("id", impression.getId());
        List<Book> books = query.getResultList();
        if (books == null || books.isEmpty()) {
            return null;
        }
        return books.get(0);
    }

}
