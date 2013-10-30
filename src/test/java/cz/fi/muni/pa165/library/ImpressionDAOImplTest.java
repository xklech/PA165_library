package cz.fi.muni.pa165.library;

import cz.fi.muni.pa165.library.dao.ImpressionDaoImpl;
import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.enums.DamageType;
import cz.fi.muni.pa165.library.entity.Impression;
import cz.fi.muni.pa165.library.enums.StatusType;
import cz.fi.muni.pa165.library.exceptions.ImpressionDaoException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for ImpressionDAO implementation
 */
public class ImpressionDAOImplTest {
    
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static ImpressionDaoImpl idao;

    private Impression imp;

    
    public ImpressionDAOImplTest() {
    }
    
    @Before
    public void initTest() throws Exception {
        // Get the entity manager for the tests.
        emf = Persistence.createEntityManagerFactory("LibraryPU");
        em = emf.createEntityManager();
        idao = new ImpressionDaoImpl(em);
        imp = new Impression(null, DamageType.NEW, 
                                DamageType.NEW, StatusType.AVAILIBLE);
    }

     /**
     * Cleans up the session.
     */
    @After
    public void beforeExit() {
        em.close();
        emf.close();
    }

    @Test
    public void testAddFindImpression() {
        Impression storedImp;
        em.getTransaction().begin();
        try {
            storedImp = idao.addImpression(imp);
        } catch (ImpressionDaoException ex) {
            fail(buildErrMsg(ex));
            return;
        }
        em.getTransaction().commit();
        assertNotNull("Method addImpression returned impression with NULL id", storedImp.getId());
        assertEquals(imp.getBook(), storedImp.getBook());
        assertEquals(imp.getInitialDamage(), storedImp.getInitialDamage());
        assertEquals(imp.getDamage(), storedImp.getDamage());
        assertEquals(imp.getStatus(), storedImp.getStatus());
        Impression result;
        try {
            result = idao.findImpressionById(storedImp.getId());
        } catch (ImpressionDaoException ex) {
            fail(buildErrMsg(ex));
            return;
        }
        assertEquals(storedImp, result);
    }

    @Test
    public void testUpdateImpression() {
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

        em.getTransaction().begin();
        em.persist(imp);
        imp2.setId(imp.getId());
        imp3.setId(imp.getId());
        try {
            imp2 = idao.updateImpression(imp2);
        }
        catch(ImpressionDaoException ex) {
            em.getTransaction().rollback();
            fail(buildErrMsg(ex));
            return;
        }
        em.getTransaction().commit();
        assertTrue(imp == imp2);
        assertEquals(imp.getId(),imp3.getId());
        assertEquals(imp.getBook(),imp3.getBook());
        assertEquals(imp.getInitialDamage(),imp3.getInitialDamage());
        assertEquals(imp.getDamage(),imp3.getDamage());
        assertEquals(imp.getStatus(),imp3.getStatus());
        
    }
    
    @Test
    public void testDeleteImpression() {        
        em.getTransaction().begin();
        em.persist(imp);
        em.getTransaction().commit();
        assertTrue(em.contains(imp));
        em.getTransaction().begin();
        try {
            idao.deleteImpression(imp);
        } catch (ImpressionDaoException ex) {
            em.getTransaction().rollback();
            fail(buildErrMsg(ex));
            return;            
        }
        em.getTransaction().commit();
        assertFalse(em.contains(imp));
    }

    @Test
    public void testFindImpressionsByDamage() {
        List<Impression> beforeList;
        List<Impression> afterList;
        DamageType damage = DamageType.HEAVILY_DAMAGED;
        imp.setDamage(damage);
        
        beforeList = idao.findImpressionsByDamage(damage);
        em.getTransaction().begin();
            em.persist(imp);
        em.getTransaction().commit();
        beforeList.add(imp);
        afterList = idao.findImpressionsByDamage(damage);
        assertEquals(beforeList, afterList);
    }
    
    @Test
    public void testFindImpressionsByStatus() {
        List<Impression> beforeList;
        List<Impression> afterList;
        StatusType status = StatusType.DISABLED;
        imp.setStatus(status);
        
        beforeList = idao.findImpressionsByStatus(status);
        em.getTransaction().begin();
            em.persist(imp);
        em.getTransaction().commit();
        beforeList.add(imp);
        afterList = idao.findImpressionsByStatus(status);
        assertEquals(beforeList, afterList);
    }
    
    @Test
    public void testFindImpressionsByBook() {
        List<Impression> beforeList;
        List<Impression> afterList;
        Book book = new Book();
        em.getTransaction().begin();
            em.persist(book);
        em.getTransaction().commit();
        imp.setBook(book);
        
        try {
            beforeList = idao.findImpressionsByBook(book);
        }
        catch (ImpressionDaoException ex) {
            fail(buildErrMsg(ex));
            return;
        }
        em.getTransaction().begin();
            em.persist(imp);
        em.getTransaction().commit();
        beforeList.add(imp);
        try {
            afterList = idao.findImpressionsByBook(book);
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