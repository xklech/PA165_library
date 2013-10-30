package cz.fi.muni.pa165.library;

import cz.fi.muni.pa165.library.dao.BookDao;
import cz.fi.muni.pa165.library.dao.BookDaoImpl;
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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Unit test for BookDao implementation
 * 
 * @author Jaroslav Klech
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class BookTest 

{   @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager mEntityManager;
    
    @Autowired
    private BookDao bookDao;

    @Test
    @Transactional
    public void testAddBook() throws Exception
    {
        Book book = new Book("Klokani", "1475-222-3333-741", null, "Zvířata", new Date(), "Pepa");
        Book bookSaved;
        bookDao.addBook(book);
        bookSaved = mEntityManager.find(Book.class, book.getId());
        System.err.println(book);
        Assert.assertEquals(book, bookSaved);
    }
    
    
    @Test
    public void testUpdateBook() throws Exception
    {
        Book book = mEntityManager.find(Book.class,new Long(1));
        mEntityManager.detach(book);
        book.setName("TestName007");
        bookDao.updateBook(book);
        mEntityManager.clear();
        Book book2 = bookDao.findBookById(new Long(1));
        Assert.assertEquals("TestName007",book2.getName());
    }
    
    @Test
    public void testDeleteBook() throws Exception
    {
        Book book = mEntityManager.find(Book.class,new Long(1));

        bookDao.deleteBook(book);
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
        Book book2 = bookDao.findBookById(new Long(2));
        Assert.assertEquals(book, book2);
        
        Book book3 = bookDao.findBookById(new Long(754));
        Assert.assertNull(book3);

    }
    
    @Test
    public void testFindBookByAuthor() throws Exception
    {
        Book bookDezo = mEntityManager.find(Book.class, new Long(1));
        Collection<Book> books = bookDao.findBooksByAuthor("Dezo");
        Assert.assertEquals(books.size(), 1);

        books = bookDao.findBooksByAuthor("asdf");
        Assert.assertEquals(books.size(), 0);

    }
   
    @Test
    public void testFindBookByDepartment() throws Exception
    {
        Book bookMobil = mEntityManager.find(Book.class, new Long(1));
        Collection<Book> books = bookDao.findBooksByDepartment("Mobil");
        Assert.assertEquals(2, books.size());
        
        Assert.assertTrue(books.contains(bookMobil));
        books = bookDao.findBooksByDepartment("asdf");
        Assert.assertEquals(books.size(), 0);

    }
    
    
    @Test
    public void testFindBookByImpression() throws Exception
    {
        Book book4 = mEntityManager.find(Book.class, new Long(4));
        Impression impr = new Impression();
        impr.setId(new Long(5));
        Book bookI = bookDao.findBookByImpression(impr);
        
        Assert.assertEquals(book4, bookI);
        


    }
    @Before
    public void initTest() throws Exception {
        // Get the entity manager for the tests.

        mEntityManager = entityManagerFactory.createEntityManager();
        prepareData();
        
    }

     /**
     * Cleans up the session.
     */
    @After
    public void closeTestFixture() {
        
        mEntityManager.close();
    }

    
    
    private void prepareData(){
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
