package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.ImpressionDao;
import cz.muni.fi.pa165.library.AbstractIntegrationTest;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.exceptions.ImpressionDaoException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit test for ImpressionDAO implementation
 */
public class ImpressionDAOImplTest extends AbstractIntegrationTest{
    @Autowired
    private ImpressionDao impressionDao;
    
    @Autowired
    private BookDao bookDao;
    
    private Impression imp;
    
    
    @Before
    public void initTest() throws Exception {
        // Get the entity manager for the tests.

        imp = new Impression(null, DamageType.NEW, DamageType.NEW, StatusType.AVAILIBLE);

    }

    @Test
    public void testAddFindImpression() throws Exception{
        Impression storedImp;

        storedImp = impressionDao.addImpression(imp);

        assertNotNull("Method addImpression returned impression with NULL id", storedImp.getId());
        assertEquals(imp.getBook(), storedImp.getBook());
        assertEquals(imp.getInitialDamage(), storedImp.getInitialDamage());
        assertEquals(imp.getDamage(), storedImp.getDamage());
        assertEquals(imp.getStatus(), storedImp.getStatus());
        Impression result;
        try {
            result = impressionDao.findImpressionById(storedImp.getId());
        } catch (ImpressionDaoException ex) {
            fail(buildErrMsg(ex));
            return;
        }
        assertEquals(storedImp, result);
    }

    @Test
    public void testUpdateImpression() throws Exception{
        Impression imp2 = new Impression();
        Impression imp3 = new Impression();
        imp2.setBook(null);
        imp3.setBook(null);
        imp2.setInitialDamage(DamageType.USED);
        imp3.setInitialDamage(DamageType.USED);
        imp2.setDamage(DamageType.SLIGHTLY_DAMAGED);
        imp3.setDamage(DamageType.SLIGHTLY_DAMAGED);
        imp2.setStatus(StatusType.LOANED);
        imp3.setStatus(StatusType.LOANED);


        impressionDao.addImpression(imp);
        imp2.setId(imp.getId());
        imp3.setId(imp.getId());
        try {
            imp2 = impressionDao.updateImpression(imp2);
        }
        catch(ImpressionDaoException ex) {
            fail(buildErrMsg(ex));
            return;
        }
        assertTrue(imp == imp2);
        assertEquals(imp.getId(),imp3.getId());
        assertEquals(imp.getBook(),imp3.getBook());
        assertEquals(imp.getInitialDamage(),imp3.getInitialDamage());
        assertEquals(imp.getDamage(),imp3.getDamage());
        assertEquals(imp.getStatus(),imp3.getStatus());
        
    }
    
    @Test
    public void testDeleteImpression() throws Exception{        

        impressionDao.addImpression(imp);

        assertNotNull(imp.getId());

        try {
            impressionDao.deleteImpression(imp);
        } catch (ImpressionDaoException ex) {
            fail(buildErrMsg(ex));
            return;            
        }
        Impression impr = impressionDao.findImpressionById(imp.getId());
        assertNull(impr);
    }

    @Test
    public void testFindImpressionsByDamage() throws Exception{
        List<Impression> beforeList;
        List<Impression> afterList;
        DamageType damage = DamageType.HEAVILY_DAMAGED;
        imp.setDamage(damage);
        
        beforeList = impressionDao.findImpressionsByDamage(damage);

        impressionDao.addImpression(imp);

        beforeList.add(imp);
        afterList = impressionDao.findImpressionsByDamage(damage);
        assertEquals(beforeList, afterList);
    }
    
    @Test
    public void testFindImpressionsByStatus() throws Exception{
        List<Impression> beforeList;
        List<Impression> afterList;
        StatusType status = StatusType.DISABLED;
        imp.setStatus(status);
        
        beforeList = impressionDao.findImpressionsByStatus(status);

        impressionDao.addImpression(imp);

        beforeList.add(imp);
        afterList = impressionDao.findImpressionsByStatus(status);
        assertEquals(beforeList, afterList);
    }
    
    @Test
    public void testFindImpressionsByBook() throws Exception{
        List<Impression> beforeList;
        List<Impression> afterList;
        Book book = new Book();
        bookDao.addBook(book);
        imp.setBook(book);
        
        try {
            beforeList = impressionDao.findImpressionsByBook(book);
        }
        catch (ImpressionDaoException ex) {
            fail(buildErrMsg(ex));
            return;
        }
        impressionDao.addImpression(imp);
        beforeList.add(imp);
        try {
            afterList = impressionDao.findImpressionsByBook(book);
        }
        catch (ImpressionDaoException ex) {
            fail(buildErrMsg(ex));
            return;
        }
        assertEquals(beforeList, afterList);
    }
    
    private String buildErrMsg(Throwable exception) {
        Throwable supp = exception;            
        StringBuilder msg = new StringBuilder("An Exception has been thrown during addImpression() :");
        do {
            msg.append('\n');
            msg.append(exception.toString());
            supp = supp.getCause();
        }
        while (supp != null);
        return msg.toString();
    }
    
}