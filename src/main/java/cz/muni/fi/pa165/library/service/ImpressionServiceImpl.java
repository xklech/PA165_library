package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.ImpressionDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.exceptions.BookDaoException;
import cz.muni.fi.pa165.library.exceptions.ImpressionDaoException;
import cz.muni.fi.pa165.library.exceptions.ServiceDataAccessException;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.ImpressionTO;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mask
 */
@Service
public class ImpressionServiceImpl implements ImpressionService {

    @Autowired
    private ImpressionDao impDao;
    
    @Autowired
    private BookDao bookDao;
    
    @Transactional
    @Override
    public ImpressionTO addImpression(ImpressionTO impressionTO) throws ServiceDataAccessException {        
        Impression entity = EntityConvertor.convertFromImpressionTo(impressionTO);
        try {
            entity = impDao.addImpression(entity);
            System.err.println(entity);
            impressionTO.setId(entity.getId());
        } catch (ImpressionDaoException ex) {
            Logger.getLogger(ImpressionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccessException("ImpressionService: addImpression", ex);
        }
        return EntityConvertor.convertFromImpression(entity);
    }

    @Transactional
    @Override
    public ImpressionTO updateImpression(ImpressionTO impressionTO) throws ServiceDataAccessException {
        Impression entity = EntityConvertor.convertFromImpressionTo(impressionTO);
        try {
            impDao.updateImpression(entity);
        } catch (ImpressionDaoException ex) {
            Logger.getLogger(ImpressionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccessException("ImpressionService: updateImpression", ex);
        }
        return impressionTO;
    }

    @Transactional
    @Override
    public void deleteImpression(ImpressionTO impressionTO) throws ServiceDataAccessException {
        Impression entity = EntityConvertor.convertFromImpressionTo(impressionTO);
        try {
            impDao.deleteImpression(entity);
        } catch (ImpressionDaoException ex) {
            Logger.getLogger(ImpressionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccessException("ImpressionService: deleteImpression", ex);
        }
    }

    @Override
    public ImpressionTO findImpressionById(Long id) throws ServiceDataAccessException {
        Impression result;
        try {
            result = impDao.findImpressionById(id);
        } catch (ImpressionDaoException ex) {
            Logger.getLogger(ImpressionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccessException("ImpressionService: findImpressionById", ex);
        }
        return EntityConvertor.convertFromImpression(result);
    }

    @Override
    public List<ImpressionTO> findImpressionsByDamage(DamageType damage) {
        List<Impression> imprassions = impDao.findImpressionsByDamage(damage);
         if(imprassions == null){
            return null;
        }
        List<ImpressionTO> result = new ArrayList<>();      
        for(Impression imp : imprassions) {
            result.add(EntityConvertor.convertFromImpression(imp));
        }
        return result;
    }

    @Override
    public List<ImpressionTO> findImpressionsByStatus(StatusType status) {
        List<Impression> imprassions = impDao.findImpressionsByStatus(status);
        if(imprassions == null){
            return null;
        }
        List<ImpressionTO> result = new ArrayList<>();
        for(Impression imp : imprassions) {
            result.add(EntityConvertor.convertFromImpression(imp));
        }
        return result;
    }

    @Override
    public List<ImpressionTO> findImpressionsByBook(BookTo bookTo) throws ServiceDataAccessException {
        List<Impression> impressions = null;        
        try {
            Book book = bookDao.findBookById(bookTo.getId());
            impressions = impDao.findImpressionsByBook(book);
        } catch (ImpressionDaoException | BookDaoException ex) {
            Logger.getLogger(ImpressionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccessException("ImpressionService: findImpressionsByBook", ex);            
        }
        if(impressions == null){
            return null;
        }
        List<ImpressionTO> result = new ArrayList<>();
        for(Impression imp : impressions) {
            result.add(EntityConvertor.convertFromImpression(imp));
        }
        return result;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }


    public void setImpDao(ImpressionDao impDao) {
        this.impDao = impDao;
    }
    
}
