package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.AbstractIntegrationTest;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Impression;
import java.util.Collection;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit test for BookDao implementation
 * 
 * @author Jaroslav Klech
 */
public class BookTest extends AbstractIntegrationTest{   
    
    @Autowired
    private BookDao bookDao;
    
    @Autowired
    private ImpressionDao impressionDao;

    @Test
    public void testAddBook() throws Exception
    {
        Book book = new Book("Klokani", "1475-222-3333-741", "Zvířata", new Date(), "Pepa");
        Book bookSaved;
        bookDao.addBook(book);
        bookSaved = bookDao.findBookById(book.getId());
        System.err.println(book);
        Assert.assertEquals(book, bookSaved);
    }
    
    
    @Test
    public void testUpdateBook() throws Exception
    {
        Book book = new Book("Android", "1111-222-3333-44", "Mobil", new Date(), "Jaryn"); 
        bookDao.addBook(book);
        Assert.assertNotNull(book.getId());
        
        Book book2 = new Book("Android", "1111-222-3333-44", "Mobil", new Date(), "Jaryn"); 
        book2.setId(book.getId());
        book2.setName("TestName007");
        bookDao.updateBook(book2);
        Book book3 = bookDao.findBookById(book.getId());
        Assert.assertEquals("TestName007",book3.getName());
    }
    
    @Test
    public void testDeleteBook() throws Exception
    {
        Book book = new Book("Android", "1111-222-3333-44", "Mobil", new Date(), "Jaryn"); 

        bookDao.addBook(book);
        Assert.assertNotNull(book.getId());
        bookDao.deleteBook(book);
        Book book2 = bookDao.findBookById(book.getId());
        Assert.assertNull(book2);

    }
    
    @Test
    public void testFindBookById() throws Exception
    {
        Book book = new Book("Android", "1111-222-3333-44", "Mobil", new Date(), "Jaryn"); 
        bookDao.addBook(book);
        Assert.assertNotNull(book.getId());
                
        Book book2 = bookDao.findBookById(book.getId());
        Assert.assertEquals(book, book2);
        
        Book book3 = bookDao.findBookById(Long.MAX_VALUE);
        Assert.assertNull(book3);
    }
    
    @Test
    public void testFindBookByAuthor() throws Exception
    {
        Book book = new Book("Android", "1111-222-3333-44", "Mobil", new Date(), "Jaryn"); 
        bookDao.addBook(book);
        Assert.assertNotNull(book.getId());
        
        Collection<Book> books = bookDao.findBooksByAuthor("Jaryn");
        Assert.assertEquals(1, books.size());

        books = bookDao.findBooksByAuthor("asdf");
        Assert.assertEquals(0, books.size());

    }
   
    @Test
    public void testFindBookByDepartment() throws Exception
    {
        Book book = new Book("Android", "1111-222-3333-44", "Mobil", new Date(), "Jaryn"); 
        bookDao.addBook(book);
        Assert.assertNotNull(book.getId());
        Book book2 = new Book("Android Expert", "1111-222-334", "Mobil", new Date(), "Dezo");    
        bookDao.addBook(book2);
        Assert.assertNotNull(book2.getId());
        
        Collection<Book> books = bookDao.findBooksByDepartment("Mobil");
        Assert.assertEquals(2, books.size());
        
        Assert.assertTrue(books.contains(book));
        books = bookDao.findBooksByDepartment("asdf");
        Assert.assertEquals(0, books.size());

    }
    
    
    @Test
    public void testFindBookByImpression() throws Exception
    {
        Book bookI = new Book("Klokani", "1475-222-3333-7414", "Zvířata", new Date(), "Pepa");
        Impression impression1 = new Impression();
        impression1.setBook(bookI);
        impressionDao.addImpression(impression1);
        Impression impression2 = new Impression();
        impression2.setBook(bookI);       
        impressionDao.addImpression(impression2);
        
        
        bookDao.addBook(bookI);
        
        Impression impr = new Impression();
        impr.setId(impression2.getId());
        Book book = bookDao.findBookByImpression(impr);
        
        Assert.assertEquals(book, bookI);
        


    }

}
