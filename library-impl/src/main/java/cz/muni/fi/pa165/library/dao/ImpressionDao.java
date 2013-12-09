package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.enums.StatusType;
import java.util.List;

/**
 *
 * @author Mask
 */
public interface ImpressionDao {
    
    /**
     * Saves given impression.
     * 
     * @param impression impression to be saved
     * @return the inputed impression
     */
    public Impression addImpression(Impression impression);
    
    /**
     * Updates given impression
     * 
     * @param impression impression to be updated
     * @return the inputed impression
     */
    public Impression updateImpression(Impression impression);
    
    /**
     * Deletes given impression
     * 
     * @param impression impression to be updated
     */
    public void deleteImpression(Impression impression);
    
    /**
     * Finds impression with given ID.
     * 
     * @param id id (Long) of searched impression
     * @return impression with given id or null if not found
     */    
    public Impression findImpressionById(Long id);
    
    /**
     * Finds all impressions with given damage level
     * 
     * @param damage damage level of impressions to find
     * @return all impressions with given damage level or empty list
     */
    public List<Impression> findImpressionsByDamage(DamageType damage);
    
    /**
     * Finds all impressions with given status
     * 
     * @param status status of impressions to find
     * @return all impressions with given status or empty list
     */
    public List<Impression> findImpressionsByStatus(StatusType status);
    
    /**
     * Finds all impressions of given books
     * 
     * @param book status of impressions to find
     * @return List of all impressions of given book or empty list
     */
    public List<Impression> findImpressionsByBook(Book book);
    
}
