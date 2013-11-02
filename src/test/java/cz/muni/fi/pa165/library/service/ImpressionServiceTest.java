package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.ImpressionDao;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.exceptions.BookDaoException;
import cz.muni.fi.pa165.library.exceptions.ImpressionDaoException;
import cz.muni.fi.pa165.library.exceptions.ServiceDataAccessException;
import cz.muni.fi.pa165.library.to.ImpressionTO;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


/**
 *
 * @author Jarek
 */
public class ImpressionServiceTest {
    
    private ImpressionServiceImpl impressionService;
    
    private ImpressionDao mockedImpressionDao;
    
    private BookDao mockedBookDao;
    
    @Before
    public void initTest(){
        impressionService = new ImpressionServiceImpl();
        mockedBookDao = mock(BookDao.class);
        mockedImpressionDao = mock(ImpressionDao.class);
        
        impressionService.setBookDao(mockedBookDao);
        impressionService.setImpDao(mockedImpressionDao);
    }
    
    @Test
    public void testAddImpression() throws Exception{
        ImpressionTO impressionTo = new ImpressionTO(null,null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression impression = EntityConvertor.convertFromImpressionTo(impressionTo); 
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Impression impressionDao = (Impression)args[0];
                impressionDao.setId(1l);
                return impressionDao;
            }})
        .when(mockedImpressionDao).addImpression(impression);
        impressionService.addImpression(impressionTo);
        assertNotNull(impressionTo.getId());
    }
    
    @Test(expected=ServiceDataAccessException.class)
    public void testUpdateImpression() throws Exception{
        ImpressionTO impressionTo = new ImpressionTO(null,null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression impression = EntityConvertor.convertFromImpressionTo(impressionTo); 
        doThrow(ImpressionDaoException.class).when(mockedImpressionDao).updateImpression(impression);        
        impressionService.updateImpression(impressionTo);

        
        doThrow(ImpressionDaoException.class).when(mockedImpressionDao).updateImpression(null);
        impressionService.updateImpression(null);
    }
    
    @Test(expected=ServiceDataAccessException.class)
    public void testDeleteImpression() throws Exception{
        ImpressionTO impressionTo = new ImpressionTO(null,null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression impression = EntityConvertor.convertFromImpressionTo(impressionTo); 
        doThrow(ImpressionDaoException.class).when(mockedImpressionDao).deleteImpression(impression);        
        impressionService.deleteImpression(impressionTo);

        
        doThrow(ImpressionDaoException.class).when(mockedImpressionDao).deleteImpression(null);
        impressionService.deleteImpression(null);
    } 
    
    @Test(expected=ServiceDataAccessException.class)
    public void testFindImpressionById() throws Exception{
        ImpressionTO impressionTo = new ImpressionTO(2l,null, DamageType.USED, DamageType.NEW, StatusType.LOANED);
        Impression impression = EntityConvertor.convertFromImpressionTo(impressionTo); 
        when(mockedImpressionDao.findImpressionById(2l)).thenReturn(impression);
        assertEquals(impressionTo, impressionService.findImpressionById(2l));
        when(mockedImpressionDao.findImpressionById(null)).thenThrow(ImpressionDaoException.class);
        impressionService.findImpressionById(null);
    } 
    
    
}
