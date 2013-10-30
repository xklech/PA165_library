package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.exceptions.ImpressionDaoException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mask
 */
public class ImpressionDaoImpl implements ImpressionDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Impression addImpression(Impression impression) throws ImpressionDaoException {
        if (impression == null)
                throw new ImpressionDaoException("Trying to store NULL object");
        if (impression.getId() != null)
                throw new ImpressionDaoException("Trying to store object that is already stored");        
        entityManager.persist(impression);
        return impression;
    }

    @Override
    public Impression updateImpression(Impression impression) throws ImpressionDaoException {
        if (impression == null)
            throw new ImpressionDaoException("Trying to update NULL");
        if (impression.getId() == null)
            throw new ImpressionDaoException("Trying to update impression with null id");
        Impression storedImpression = entityManager.find(Impression.class, impression.getId());
        if (storedImpression == null)
            throw new ImpressionDaoException("Given impression not found in the database");
        storedImpression.setBook(impression.getBook());
        storedImpression.setInitialDamage(impression.getInitialDamage());
        storedImpression.setDamage(impression.getDamage());
        storedImpression.setStatus(impression.getStatus());
        return storedImpression;
    }

    @Override
    public void deleteImpression(Impression impression) throws ImpressionDaoException  {
        if (impression == null)
            throw new ImpressionDaoException("Trying to delete NULL object");
        if (impression.getId() == null)
            throw new ImpressionDaoException ("Trying to delete unstored impression");
        Impression storedImpression = entityManager.find(Impression.class, impression.getId());
        if (storedImpression == null)
            throw new ImpressionDaoException("Given impression not found in the database");
        entityManager.remove(impression);
    }
    
    @Override
    public Impression findImpressionById(Long id) throws ImpressionDaoException {
        if (id == null)
            throw new ImpressionDaoException("Trying to find NULL");
        return entityManager.find(Impression.class, id);
    }

    @Override
    public List<Impression> findImpressionsByDamage(DamageType damage) {
        TypedQuery<Impression> query 
                = entityManager.createNamedQuery("Impression.findImpressionsByDamage",
                                        Impression.class);
        query.setParameter("damage", damage);
        return query.getResultList();
    }

    @Override
    public List<Impression> findImpressionsByStatus(StatusType status) {
        TypedQuery<Impression> query 
                = entityManager.createNamedQuery("Impression.findImpressionsByStatus",
                                        Impression.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public List<Impression> findImpressionsByBook(Book book) throws ImpressionDaoException {
        if (book == null) {
            throw new ImpressionDaoException("Inputed book is NULL");
        }
        Long bookId = book.getId();
        if (bookId == null) {
            throw new ImpressionDaoException("Inputed book has null ID");
        }
        TypedQuery<Impression> query 
                = entityManager.createNamedQuery("Impression.findImpressionsByBook",
                                        Impression.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

}
