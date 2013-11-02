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
@Transactional
public class ImpressionServiceImpl implements ImpressionService {

    @Autowired
    private ImpressionDao impDao;
    
    @Autowired
    private BookDao bookDao;
    
    @Override
    public ImpressionTO addImpression(ImpressionTO impressionTO) throws ServiceDataAccessException {        
        Impression entity = EntityConvertor.convertFromImpressionTo(impressionTO);
        try {
            entity = impDao.addImpression(entity);
            impressionTO.setId(entity.getId());
        } catch (ImpressionDaoException ex) {
            Logger.getLogger(ImpressionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccessException(null, ex);
        }
        return EntityConvertor.convertFromImpression(entity);
    }

    @Override
    public ImpressionTO updateImpression(ImpressionTO impressionTO) throws ServiceDataAccessException {
        Impression entity = EntityConvertor.convertFromImpressionTo(impressionTO);
        try {
            impDao.updateImpression(entity);
        } catch (ImpressionDaoException ex) {
            Logger.getLogger(ImpressionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccessException(null, ex);
        }
        return impressionTO;
    }

    @Override
    public void deleteImpression(ImpressionTO impressionTO) throws ServiceDataAccessException {
        Impression entity = EntityConvertor.convertFromImpressionTo(impressionTO);
        try {
            impDao.deleteImpression(entity);
        } catch (ImpressionDaoException ex) {
            Logger.getLogger(ImpressionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccessException(null, ex);
        }
    }

    @Override
    public ImpressionTO findImpressionById(Long id) throws ServiceDataAccessException {
        Impression result;
        try {
            result = impDao.findImpressionById(id);
        } catch (ImpressionDaoException ex) {
            Logger.getLogger(ImpressionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccessException(null, ex);
        }
        return EntityConvertor.convertFromImpression(result);
    }

    @Override
    public List<ImpressionTO> findImpressionsByDamage(DamageType damage) {
        List<ImpressionTO> result = new ArrayList<ImpressionTO>();
        List<Impression> supp = impDao.findImpressionsByDamage(damage);
        for(Impression imp : supp) {
            result.add(EntityConvertor.convertFromImpression(imp));
        }
        return result;
    }

    @Override
    public List<ImpressionTO> findImpressionsByStatus(StatusType status) {
        List<ImpressionTO> result = new ArrayList<ImpressionTO>();
        List<Impression> supp = impDao.findImpressionsByStatus(status);
        for(Impression imp : supp) {
            result.add(EntityConvertor.convertFromImpression(imp));
        }
        return result;
    }

    @Override
    public List<ImpressionTO> findImpressionsByBook(BookTo bookTo) throws ServiceDataAccessException {
        List<Impression> supp;
        List<ImpressionTO> result = new ArrayList<ImpressionTO>();
        if (bookTo == null) {
            Logger.getLogger(ImpressionServiceImpl.class.getName()).log(Level.SEVERE, "Inserted bookTo is null.");
            throw new ServiceDataAccessException("Inserted bookTo is null.");                        
        }
        try {
            Book book = bookDao.findBookById(bookTo.getId());
            supp = impDao.findImpressionsByBook(book);
        } catch (ImpressionDaoException | BookDaoException ex) {
            Logger.getLogger(ImpressionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServiceDataAccessException(null, ex);            
        }
        for(Impression imp : supp) {
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
