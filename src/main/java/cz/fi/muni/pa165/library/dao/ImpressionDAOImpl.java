package cz.fi.muni.pa165.library.dao;

import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.enums.DamageType;
import cz.fi.muni.pa165.library.entity.Impression;
import cz.fi.muni.pa165.library.enums.StatusType;
import cz.fi.muni.pa165.library.exceptions.ImpressionDAOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Mask
 */
public class ImpressionDAOImpl implements ImpressionDAO {

    private EntityManager em;
    
    public ImpressionDAOImpl(EntityManager em) {
        this.em = em;
    } 
    
    @Override
    public Impression addImpression(Impression impression) throws ImpressionDAOException {
        if (impression == null)
                throw new ImpressionDAOException("Trying to store NULL object");
        if (impression.getId() != null)
                throw new ImpressionDAOException("Trying to store object that is already stored");        
        em.persist(impression);
        return impression;
    }

    @Override
    public Impression updateImpression(Impression impression) throws ImpressionDAOException {
        if (impression == null)
            throw new ImpressionDAOException("Trying to update NULL");
        if (impression.getId() == null)
            throw new ImpressionDAOException("Trying to update impression with null id");
        Impression storedImpression = em.find(Impression.class, impression.getId());
        if (storedImpression == null)
            throw new ImpressionDAOException("Given impression not found in the database");
        storedImpression.setBook(impression.getBook());
        storedImpression.setInitialDamage(impression.getInitialDamage());
        storedImpression.setDamage(impression.getDamage());
        storedImpression.setStatus(impression.getStatus());
        return storedImpression;
    }

    @Override
    public void deleteImpression(Impression impression) throws ImpressionDAOException  {
        if (impression == null)
            throw new ImpressionDAOException("Trying to delete NULL object");
        if (impression.getId() == null)
            throw new ImpressionDAOException ("Trying to delete unstored impression");
        Impression storedImpression = em.find(Impression.class, impression.getId());
        if (storedImpression == null)
            throw new ImpressionDAOException("Given impression not found in the database");
        em.remove(impression);
    }
    
    @Override
    public Impression findImpressionById(Long id) throws ImpressionDAOException {
        if (id == null)
            throw new ImpressionDAOException("Trying to find NULL");
        return em.find(Impression.class, id);
    }

    @Override
    public List<Impression> findImpressionsByDamage(DamageType damage) {
        TypedQuery<Impression> query 
                = em.createNamedQuery("Impression.findImpressionsByDamage",
                                        Impression.class);
        query.setParameter("damage", damage);
        return query.getResultList();
    }

    @Override
    public List<Impression> findImpressionsByStatus(StatusType status) {
        TypedQuery<Impression> query 
                = em.createNamedQuery("Impression.findImpressionsByStatus",
                                        Impression.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public List<Impression> findImpressionsByBook(Book book) throws ImpressionDAOException {
        if (book == null) {
            throw new ImpressionDAOException("Inputed book is NULL");
        }
        Long bookId = book.getId();
        if (bookId == null) {
            throw new ImpressionDAOException("Inputed book has null ID");
        }
        TypedQuery<Impression> query 
                = em.createNamedQuery("Impression.findImpressionsByBook",
                                        Impression.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

}
