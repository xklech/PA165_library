package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.dao.BookDao;
import cz.muni.fi.pa165.library.dao.ImpressionDao;
import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.ImpressionTo;
import cz.muni.fi.pa165.library.utils.EntityConvertor;
import java.util.ArrayList;
import java.util.List;
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
    
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setImpDao(ImpressionDao impDao) {
        this.impDao = impDao;
    }
    
    @Override
    public ImpressionTo addImpression(ImpressionTo impressionTo) {  
        Impression entity = EntityConvertor.convertFromImpressionTo(impressionTo);
	entity = impDao.addImpression(entity);
	impressionTo.setId(entity.getId());
        return EntityConvertor.convertFromImpression(entity);
    }

    @Override
    public ImpressionTo updateImpression(ImpressionTo impressionTo) {
        Impression entity = EntityConvertor.convertFromImpressionTo(impressionTo);
	impDao.updateImpression(entity);
        return impressionTo;
    }

    @Override
    public boolean deleteImpression(ImpressionTo impressionTo) {
        Impression entity = EntityConvertor.convertFromImpressionTo(impressionTo);
	impDao.deleteImpression(entity);
	return true;
    }

    @Override
    public ImpressionTo findImpressionById(Long id) {
        Impression result;
	result = impDao.findImpressionById(id);
        return EntityConvertor.convertFromImpression(result);
    }

    @Override
    public List<ImpressionTo> findImpressionsByDamage(DamageType damage) {
        List<ImpressionTo> result = new ArrayList();
        List<Impression> supp = impDao.findImpressionsByDamage(damage);
        for(Impression imp : supp) {
            result.add(EntityConvertor.convertFromImpression(imp));
        }
        return result;
    }

    @Override
    public List<ImpressionTo> findImpressionsByStatus(StatusType status) {
        List<ImpressionTo> result = new ArrayList();
        List<Impression> supp = impDao.findImpressionsByStatus(status);
        for(Impression imp : supp) {
            result.add(EntityConvertor.convertFromImpression(imp));
        }
        return result;
    }

    @Override
    public List<ImpressionTo> findImpressionsByBook(BookTo bookTo) {
	if (bookTo == null) {
	    throw new IllegalArgumentException("bookTo");
	}
        List<Impression> impressions;
        List<ImpressionTo> result = new ArrayList();
	Book book = bookDao.findBookById(bookTo.getId());
	impressions = impDao.findImpressionsByBook(book);
        for(Impression imp : impressions) {
            result.add(EntityConvertor.convertFromImpression(imp));
        }
        return result;
    }
    
}
