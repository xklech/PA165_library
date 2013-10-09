package cz.fi.muni.pa165.library;

import cz.fi.muni.pa165.library.dao.BookDAO;
import cz.fi.muni.pa165.library.dao.BookDAOImpl;
import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Impression;
import cz.fi.muni.pa165.library.exceptions.BookDAOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws BookDAOException
    {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryPU");
        EntityManager manager = emf.createEntityManager();
        Book book = new Book("Android", "111-222-444-555", null, "Zbraně", new Date(), "Jarek");
        BookDAO bookDAO = new BookDAOImpl(manager);
        Impression impression1 = new Impression();
        impression1.setBook(book);
        Impression impression2 = new Impression();
        impression2.setBook(book);
        List<Impression> impressions = new ArrayList<>();
        impressions.add(impression1);
        impressions.add(impression2);
        book.setImpressions(impressions);
        manager.getTransaction().begin();
            bookDAO.addBook(book);
        manager.getTransaction().commit();
    }
}
