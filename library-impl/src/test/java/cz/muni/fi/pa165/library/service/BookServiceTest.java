/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.AbstractIntegrationTest;
import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.ImpressionDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.exceptions.BookDaoException;
import cz.muni.fi.pa165.library.exceptions.ServiceDataAccessException;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.ImpressionTo;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author Jaroslav Klech
 */
public class BookServiceTest extends AbstractIntegrationTest{

    private BookServiceImpl bookService;
    
    private BookDao mockedBookDao;
    
    private ImpressionDao mockedImpressionDao;
    
    
    @Before
    public void initTest(){
        bookService = new BookServiceImpl();
        mockedBookDao = mock(BookDao.class);
        mockedImpressionDao = mock(ImpressionDao.class);
        
        bookService.setBookDao(mockedBookDao);
        bookService.setImpressionDao(mockedImpressionDao);
    }
    
    @Test(expected=ServiceDataAccessException.class)
    public void testSaveBook() throws Exception
    {
        BookTo bookTo= new BookTo("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        Book book = EntityConvertor.convertFromBookTo(bookTo);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Book bookDao = (Book)args[0];
                bookDao.setId(1l);
                return null;
            }})
        .when(mockedBookDao).addBook(book);
        bookService.save(bookTo);
        assertNotNull(bookTo.getId());
        book.setId(1l);
        bookTo.setId(1l);
        doThrow(BookDaoException.class).when(mockedBookDao).addBook(book);
        bookService.save(bookTo);
    }
    
    @Test(expected=ServiceDataAccessException.class)
    public void testFindBookById() throws Exception
    {
        BookTo bookTo= new BookTo("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        bookTo.setId(1l);
        Book book = EntityConvertor.convertFromBookTo(bookTo);
        when(mockedBookDao.findBookById(1l)).thenReturn(book);
        BookTo bookTo1 = bookService.findBookById(1l);
        assertEquals(bookTo, bookTo1);
        when(mockedBookDao.findBookById(null)).thenThrow(BookDaoException.class);
        bookService.findBookById(null);
    }
   
    @Test(expected=ServiceDataAccessException.class)
    public void testFindBooksByName() throws Exception
    {
        Book book1= new Book("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        book1.setId(1l);
        Book book2= new Book("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        book2.setId(2l);
        

        when(mockedBookDao.findBooksByName("Mobil")).thenReturn((Collection)Arrays.asList(book1,book2));
        Collection<BookTo> books= bookService.findBooksByName("Mobil");
        assertEquals(books.size(), 2);
        
        when(mockedBookDao.findBooksByName(null)).thenThrow(BookDaoException.class);
        bookService.findBooksByName(null);
    }
 
    @Test(expected=ServiceDataAccessException.class)
    public void testUpdateBook() throws Exception
    {
        BookTo bookTo= new BookTo("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        Book book1 = EntityConvertor.convertFromBookTo(bookTo);
        doThrow(BookDaoException.class).when(mockedBookDao).updateBook(book1);        
        bookService.updateBook(bookTo);

        
        doThrow(BookDaoException.class).when(mockedBookDao).updateBook(null);
        bookService.updateBook(null);
    }
    
    @Test(expected=ServiceDataAccessException.class)
    public void testDeleteBook() throws Exception
    {
        BookTo bookTo= new BookTo("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        Book book1 = EntityConvertor.convertFromBookTo(bookTo);
        doThrow(BookDaoException.class).when(mockedBookDao).deleteBook(book1);        
        bookService.deleteBook(bookTo);

        
        doThrow(BookDaoException.class).when(mockedBookDao).deleteBook(null);
        bookService.deleteBook(null);
    }
    
    @Test(expected=ServiceDataAccessException.class)
    public void testFindBookByISBN() throws Exception
    {
        BookTo bookTo= new BookTo("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        bookTo.setId(1l);
        Book book1 = EntityConvertor.convertFromBookTo(bookTo);

        when(mockedBookDao.findBookByISBN("1234-4569-874")).thenReturn(book1);
        BookTo bookTo2 = bookService.findBookByISBN("1234-4569-874");
        assertEquals(bookTo, bookTo2);

        when(mockedBookDao.findBookByISBN("1234")).thenReturn(null);

        assertNull(bookService.findBookByISBN("1234"));
        
        doThrow(BookDaoException.class).when(mockedBookDao).findBookByISBN(null);
        bookService.findBookByISBN(null);
    }
    
    @Test(expected=ServiceDataAccessException.class)
    public void testFindBooksByAuthor() throws Exception
    {
        BookTo bookTo= new BookTo("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        bookTo.setId(1l);
        Book book1 = EntityConvertor.convertFromBookTo(bookTo);

        when(mockedBookDao.findBooksByAuthor("Jaryn")).thenReturn(Arrays.asList(book1));
        List<BookTo> bookTo2 = bookService.findBooksByAuthor("Jaryn");
        assertEquals(Arrays.asList(bookTo), bookTo2);

        when(mockedBookDao.findBooksByAuthor("1234")).thenReturn(null);

        assertNull(bookService.findBookByISBN("1234"));
        
        doThrow(BookDaoException.class).when(mockedBookDao).findBooksByAuthor(null);
        bookService.findBooksByAuthor(null);
    }
   
    @Test
    public void testFindBooksByPublishDate() throws Exception
    {
        BookTo bookTo= new BookTo("Android", "1234-4569-874", "Mobil", new Date(1234567l), "Jaryn");
        bookTo.setId(1l);
        Book book1 = EntityConvertor.convertFromBookTo(bookTo);

        when(mockedBookDao.findBooksByPublishDate(new Date(12345670),new Date(12345672))).thenReturn(Arrays.asList(book1));
        List<BookTo> bookTo2 = bookService.findBooksByPublishDate(new Date(12345670),new Date(12345672));
        assertEquals(Arrays.asList(bookTo), bookTo2);

        when(mockedBookDao.findBooksByPublishDate(new Date(12345670),new Date(12345670))).thenReturn(null);

        assertNull(bookService.findBooksByPublishDate(new Date(12345670),new Date(12345670)));

    }
    
    @Test(expected=ServiceDataAccessException.class)
    public void testFindBooksByDepartment() throws Exception
    {
        BookTo bookTo= new BookTo("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        bookTo.setId(1l);
        Book book1 = EntityConvertor.convertFromBookTo(bookTo);

        when(mockedBookDao.findBooksByDepartment("Mobil")).thenReturn(Arrays.asList(book1));
        List<BookTo> bookTo2 = bookService.findBooksByDepartment("Mobil");
        assertEquals(Arrays.asList(bookTo), bookTo2);


        when(mockedBookDao.findBooksByDepartment("1234")).thenReturn(null);
        assertNull(bookService.findBooksByDepartment("1234"));
        
        doThrow(BookDaoException.class).when(mockedBookDao).findBooksByDepartment(null);
        bookService.findBooksByDepartment(null);

    } 
   
    @Test(expected=ServiceDataAccessException.class)
    public void testFindBookByImpression() throws Exception
    {
        BookTo bookTo= new BookTo("Android", "1234-4569-874", "Mobil", null, "Jaryn");
        bookTo.setId(1l);
        Book book1 = EntityConvertor.convertFromBookTo(bookTo);

        ImpressionTo impressionTo = new ImpressionTo();
        impressionTo.setBookTo(bookTo);
        impressionTo.setId(1l);
        Impression impression = EntityConvertor.convertFromImpressionTo(impressionTo);
        when(mockedBookDao.findBookByImpression(impression)).thenReturn(book1);
        when(mockedImpressionDao.findImpressionById(impression.getId())).thenReturn(impression);
        BookTo bookTo2 = bookService.findBookByImpression(impressionTo);
        assertEquals(bookTo, bookTo2);

        ImpressionTo impressionTo2 = new ImpressionTo();
        Impression impression2 = EntityConvertor.convertFromImpressionTo(impressionTo2);
        
        when(mockedBookDao.findBookByImpression(impression2)).thenReturn(null);
        assertNull(bookService.findBookByImpression(impressionTo2));
        
        doThrow(BookDaoException.class).when(mockedBookDao).findBookByImpression(null);
        bookService.findBookByImpression(null);

    } 
    
}
