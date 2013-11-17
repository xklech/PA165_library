package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.ImpressionDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.exceptions.ImpressionDaoException;
import cz.muni.fi.pa165.library.exceptions.ServiceDataAccessException;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.ImpressionTo;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import static org.mockito.Mockito.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mask
 */
public class ImpressionServiceTest {
    
    private ImpressionServiceImpl impService;
    private ImpressionDao mockedImpDao;
    private ImpressionTo impTO;
    private BookDao mockedBookDao;
    private List<Impression> emptyImp = new ArrayList<Impression>();
    private List<ImpressionTo> emptyImpTO = new ArrayList<ImpressionTo>();
    
    public ImpressionServiceTest() {
    }
    
    @Before
    public void initTest() {
        impService = new ImpressionServiceImpl();
        mockedImpDao = mock(ImpressionDao.class);
        mockedBookDao = mock(BookDao.class);
        impService.setImpDao(mockedImpDao);
        impService.setBookDao(mockedBookDao);
    }
    
    @Test
    public void addImpressionTest() throws Exception {
        //try null
        when(mockedImpDao.addImpression(null)).thenThrow(new ImpressionDaoException());        
        try {
            impService.addImpression(null);
            fail("Null impressionTO passed. Should throw ServiceDataAccessException. ");
        }
        catch (ServiceDataAccessException ex) {}
        
        //try something with ID
        impTO = new ImpressionTo(666l, null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression imp = EntityConvertor.convertFromImpressionTo(impTO);
        when(mockedImpDao.addImpression(imp)).thenThrow(new ImpressionDaoException());
        try {
            impService.addImpression(impTO);
            fail("impressionTO with preset ID passed. Should throw ServiceDataAccessException. ");
        }
        catch (ServiceDataAccessException ex) {}
        
        //try some valid input
        ImpressionTo impTO2pre = new ImpressionTo(Long.MIN_VALUE, null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression imp2pre = EntityConvertor.convertFromImpressionTo(impTO2pre);
        ImpressionTo impTO2post = new ImpressionTo(666l, null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression imp2post = EntityConvertor.convertFromImpressionTo(impTO2post);
        when(mockedImpDao.addImpression(imp2pre)).thenReturn(imp2post);
        ImpressionTo result = impService.addImpression(impTO2pre);
        assertEquals(impTO2post, result);
    }
    
    @Test
    public void updateImpressionTest() throws Exception {
        //try null
        when(mockedImpDao.updateImpression(null)).thenThrow(new ImpressionDaoException());        
        try {
            impService.updateImpression(null);
            fail("Null impressionTO passed. Should throw ServiceDataAccessException. ");
        }
        catch (ServiceDataAccessException ex) {}
        
        //try something with null ID
        impTO = new ImpressionTo(null, null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression imp = EntityConvertor.convertFromImpressionTo(impTO);
        when(mockedImpDao.updateImpression(imp)).thenThrow(new ImpressionDaoException());
        try {
            impService.updateImpression(impTO);
            fail("impressionTO with null ID passed. Should throw ServiceDataAccessException. ");
        }
        catch (ServiceDataAccessException ex) {}
        
        //simulate object not present in the DB
        ImpressionTo impTO2 = new ImpressionTo(1010l, null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression imp2 = EntityConvertor.convertFromImpressionTo(impTO2);
        when(mockedImpDao.updateImpression(imp2)).thenThrow(new ImpressionDaoException());
        try {
            impService.updateImpression(impTO);
            fail("impressionTO with null ID passed. Should throw ServiceDataAccessException. ");
        }
        catch (ServiceDataAccessException ex) {}        
        
        //try some valid input
        ImpressionTo impTO3 = new ImpressionTo(666l, null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression imp3 = EntityConvertor.convertFromImpressionTo(impTO3);        
        when(mockedImpDao.updateImpression(imp3)).thenReturn(imp3);
        ImpressionTo result = impService.updateImpression(impTO3);
        assertEquals(impTO3, result);
    }
    
    @Test
    public void deleteImpressionTest() throws Exception {
        //try null
        doThrow(new ImpressionDaoException()).when(mockedImpDao).deleteImpression(null);       
        try {
            impService.deleteImpression(null);
            fail("Null impressionTO passed. Should throw ServiceDataAccessException. ");
        }
        catch (ServiceDataAccessException ex) {}
        
        //try something with null ID
        impTO = new ImpressionTo(null, null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression imp = EntityConvertor.convertFromImpressionTo(impTO);
        doThrow(new ImpressionDaoException()).when(mockedImpDao).deleteImpression(imp);
        try {
            impService.deleteImpression(impTO);
            fail("impressionTO with null ID passed. Should throw ServiceDataAccessException. ");
        }
        catch (ServiceDataAccessException ex) {}
        
        //simulate something not in the DB.
        ImpressionTo impTO2 = new ImpressionTo(2020l, null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression imp2 = EntityConvertor.convertFromImpressionTo(impTO2);
        doThrow(new ImpressionDaoException()).when(mockedImpDao).deleteImpression(imp2);
        try {
            impService.deleteImpression(impTO);
            fail("impressionTO with null ID passed. Should throw ServiceDataAccessException. ");
        }
        catch (ServiceDataAccessException ex) {}        
    }
    
    @Test
    public void findImpressionByIdTest() throws Exception {
        //Try null
        when(mockedImpDao.findImpressionById(null)).thenThrow(new ImpressionDaoException());        
        try {
            impService.findImpressionById(null);
            fail("Null id passed. Should throw ServiceDataAccessException. ");
        }
        catch (ServiceDataAccessException ex) {}
        
        //try some real stuff
        Long id = 50l;
        Impression imp = new Impression(null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        imp.setId(id);
        impTO = EntityConvertor.convertFromImpression(imp);
        when(mockedImpDao.findImpressionById(id)).thenReturn(imp);
        ImpressionTo result = impService.findImpressionById(id);
        assertEquals(impTO, result);
        
    }
    
    @Test
    public void findImpressionsByDamageTest() {
        //simulate no impressions with inputed damage level
        when(mockedImpDao.findImpressionsByDamage(DamageType.NEW)).thenReturn(new ArrayList<Impression>());
        List<ImpressionTo> result1 = impService.findImpressionsByDamage(DamageType.NEW);
        assertEquals(emptyImpTO, result1);
        
        //simulate two impressions with inputed damage level
        List<ImpressionTo> expectedTO = new ArrayList<ImpressionTo>();
        ImpressionTo imp1 = new ImpressionTo(11l, null, DamageType.NEW, DamageType.USED, StatusType.LOANED);
        ImpressionTo imp2 = new ImpressionTo(22l, null, DamageType.NEW, DamageType.USED, StatusType.LOANED);
        expectedTO.add(imp1);
        expectedTO.add(imp2);
        List<Impression> result = new ArrayList<Impression>();
        for (ImpressionTo supp: expectedTO) {
            result.add(EntityConvertor.convertFromImpressionTo(supp));
        }
        when(mockedImpDao.findImpressionsByDamage(DamageType.USED)).thenReturn(result);
        List<ImpressionTo> resultTO = impService.findImpressionsByDamage(DamageType.USED);
        
        assertEquals(expectedTO, resultTO);
    }
    
    @Test
    public void findImpressionsByStatusTest() {
        //simulate no impressions with inputed status
        when(mockedImpDao.findImpressionsByStatus(StatusType.AVAILIBLE)).thenReturn(new ArrayList<Impression>());
        List<ImpressionTo> result1 = impService.findImpressionsByStatus(StatusType.AVAILIBLE);
        assertEquals(emptyImpTO, result1);
        
        //simulate two impressions with inputed status
        List<ImpressionTo> expectedTO = new ArrayList<ImpressionTo>();
        ImpressionTo imp1 = new ImpressionTo(11l, null, DamageType.NEW, DamageType.USED, StatusType.LOANED);
        ImpressionTo imp2 = new ImpressionTo(22l, null, DamageType.NEW, DamageType.USED, StatusType.LOANED);
        expectedTO.add(imp1);
        expectedTO.add(imp2);
        List<Impression> result = new ArrayList<Impression>();
        for (ImpressionTo supp: expectedTO) {
            result.add(EntityConvertor.convertFromImpressionTo(supp));
        }
        when(mockedImpDao.findImpressionsByStatus(StatusType.LOANED)).thenReturn(result);
        List<ImpressionTo> resultTO = impService.findImpressionsByStatus(StatusType.LOANED);
        
        assertEquals(expectedTO, resultTO);
    }
    
    /**
     * Finds all impressions of given books
     * 
     * @param bookTo status of impressions to find
     * @throws ServiceDataAccessException is thrown, when inputed BookTO is null
     * @return List of all impressionTOs of given BookTO or empty list
     */
    @Test
    public void findImpressionsByBookTest() throws Exception {        
        //try null book
        when(mockedImpDao.findImpressionsByBook(null)).thenThrow(new ImpressionDaoException());
        try {
            List<ImpressionTo> result1 = impService.findImpressionsByBook(null);
            fail("Null bookTO passed. Should throw ServiceDataAccessException");
        }
        catch (ServiceDataAccessException ex) {}
        
        //simulate no impressions with inputed bookTO
        BookTo bookTO1 = new BookTo("Babička", "978-80-242-2872-3", "próza", null, "Božena Němcová");
        bookTO1.setId(11l);
        Book book1 = EntityConvertor.convertFromBookTo(bookTO1);
        when(mockedImpDao.findImpressionsByBook(book1)).thenReturn(new ArrayList<Impression>());
        List<ImpressionTo> result2 = impService.findImpressionsByStatus(StatusType.AVAILIBLE);
        assertEquals(emptyImpTO, result2);
        
        //simulate two impressions with inputed status
        List<ImpressionTo> expectedTO = new ArrayList<ImpressionTo>();
        BookTo bookTO2 = new BookTo("Dědeček", "80-204-1634-X", "próza", null, "Vítězslav Jareš");
        bookTO2.setId(22l);
        Book book2 = EntityConvertor.convertFromBookTo(bookTO2);
        ImpressionTo imp1 = new ImpressionTo(11l, bookTO2, DamageType.NEW, DamageType.USED, StatusType.LOANED);
        ImpressionTo imp2 = new ImpressionTo(22l, bookTO2, DamageType.NEW, DamageType.USED, StatusType.LOANED);
        expectedTO.add(imp1);
        expectedTO.add(imp2);
        List<Impression> result = new ArrayList<Impression>();
        for (ImpressionTo supp: expectedTO) {
            result.add(EntityConvertor.convertFromImpressionTo(supp));
        }
        when(mockedImpDao.findImpressionsByBook(book2)).thenReturn(result);
        when(mockedBookDao.findBookById(bookTO2.getId())).thenReturn(book2);
        List<ImpressionTo> resultTO = impService.findImpressionsByBook(bookTO2);
        
        assertEquals(expectedTO, resultTO);
    }
        
}