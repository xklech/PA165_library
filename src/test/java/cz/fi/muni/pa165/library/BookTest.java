package cz.fi.muni.pa165.library;

import cz.fi.muni.pa165.library.dao.BookDAO;
import cz.fi.muni.pa165.library.dao.BookDAOImpl;
import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.entity.Impression;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for BookDAO implementation
 * 
 * @author Jaroslav Klech
 */
public class BookTest 

{

    private static EntityManagerFactory mEmf;

    private static EntityManager mEntityManager;

    private static BookDAO mBookDAO;

    @Test
    public void testAddBook() throws Exception
    {
        Book book = new Book("Klokani", "1475-222-3333-741", null, "Zvířata", new Date(), "Pepa");
        Book bookSaved;
        mEntityManager.getTransaction().begin();
        mBookDAO.addBook(book);
        mEntityManager.getTransaction().commit();
        bookSaved = mEntityManager.find(Book.class, book.getId());
        Assert.assertEquals(book, bookSaved);
    }
    
    
    @Test
    public void testUpdateBook() throws Exception
    {
        Book book = mEntityManager.find(Book.class,new Long(1));
        book.setName("TestName007");
        mEntityManager.getTransaction().begin();
            mBookDAO.updateBook(book);
        mEntityManager.getTransaction().commit();
        mEntityManager.clear();
        Book book2 = mEntityManager.find(Book.class,new Long(1));
        Assert.assertEquals("TestName007",book2.getName());
    }
    
    @Test
    public void testDeleteBook() throws Exception
    {
        Book book = mEntityManager.find(Book.class,new Long(1));

        mEntityManager.getTransaction().begin();
            mBookDAO.deleteBook(book);
        mEntityManager.getTransaction().commit();
        mEntityManager.clear();
        Book book2 = mEntityManager.find(Book.class,new Long(1));
        Assert.assertNull(book2);
        
        Book check = mEntityManager.find(Book.class,new Long(2));
        Assert.assertNotNull(check);
    }
    
    @Test
    public void testFindBookById() throws Exception
    {
        Book book = mEntityManager.find(Book.class,new Long(2));
        Book book2 = mBookDAO.findBookById(new Long(2));
        Assert.assertEquals(book, book2);
        
        Book book3 = mBookDAO.findBookById(new Long(754));
        Assert.assertNull(book3);

    }
    
    @Test
    public void testFindBookByAuthor() throws Exception
    {
        Book bookDezo = mEntityManager.find(Book.class, new Long(1));
        Collection<Book> books = mBookDAO.findBooksByAuthor("Dezo");
        Assert.assertEquals(books.size(), 1);

        books = mBookDAO.findBooksByAuthor("asdf");
        Assert.assertEquals(books.size(), 0);

    }
   
    @Test
    public void testFindBookByDepartment() throws Exception
    {
        Book bookMobil = mEntityManager.find(Book.class, new Long(1));
        Collection<Book> books = mBookDAO.findBooksByDepartment("Mobil");
        Assert.assertEquals(2, books.size());
        
        Assert.assertTrue(books.contains(bookMobil));
        books = mBookDAO.findBooksByDepartment("asdf");
        Assert.assertEquals(books.size(), 0);

    }
    
    
    @Test
    public void testFindBookByImpression() throws Exception
    {
        Book book4 = mEntityManager.find(Book.class, new Long(4));
        Impression impr = new Impression();
        impr.setId(new Long(5));
        Book bookI = mBookDAO.findBookByImpression(impr);
        
        Assert.assertEquals(book4, bookI);
        


    }
    @Before
    public void initTest() throws Exception {
        // Get the entity manager for the tests.
        mEmf = Persistence.createEntityManagerFactory("LibraryPU");
        System.err.println("asdasd");
        mEntityManager = mEmf.createEntityManager();
        mEntityManager.getTransaction().begin();
        prepareData();
        mEntityManager.getTransaction().commit();
        mBookDAO = new BookDAOImpl(mEntityManager);
    }

     /**
     * Cleans up the session.
     */
    @After
    public void closeTestFixture() {
        mEntityManager.close();
        mEmf.close();
    }

    
    
    private static void prepareData(){
        Book book1 = new Book("Android", "1111-222-3333-44", null, "Mobil", new Date(), "Jaryn"); 
        Book book2 = new Book("Android Expert", "1111-222-334", null, "Mobil", new Date(), "Dezo");     
        Book book3 = new Book("Karkulka", "1475-222-3333-444", null, "Pohádky", new Date(), "B. Němcová");
        
        Book bookI = new Book("Klokani", "1475-222-3333-7414", null, "Zvířata", new Date(), "Pepa");
        Impression impression1 = new Impression();
        impression1.setBook(bookI);
        Impression impression2 = new Impression();
        impression2.setBook(bookI);
        List<Impression> impressions = new ArrayList<>();
        impressions.add(impression1);
        impressions.add(impression2);
       
        bookI.setImpressions(impressions);
        
        mEntityManager.persist(book1);
        mEntityManager.persist(book2);
        mEntityManager.persist(book3);
        mEntityManager.persist(bookI);
    }


}
