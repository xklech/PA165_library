package cz.fi.muni.pa165.library.dao;

import cz.fi.muni.pa165.library.entity.Book;
import cz.fi.muni.pa165.library.enums.DamageType;
import cz.fi.muni.pa165.library.entity.Impression;
import cz.fi.muni.pa165.library.enums.StatusType;
import cz.fi.muni.pa165.library.exceptions.ImpressionDAOException;
import java.util.List;

/**
 *
 * @author Mask
 */
public interface ImpressionDAO {
    
    /**
     * Saves given impression.
     * 
     * @param impression impression to be saved
     * @throws ImpressionDAOException is thrown when impression is null or has ID
     * @return the inputed impression
     */
    public Impression addImpression(Impression impression) throws ImpressionDAOException;
    
    /**
     * Updates given impression
     * 
     * @param impression impression to be updated
     * @throws ImpressionDAOException is thrown when impression is null, has no ID or doesn't exist in DB
     * @return the inputed impression
     */
    public Impression updateImpression(Impression impression) throws ImpressionDAOException;
    
    /**
     * Deletes given impression
     * 
     * @param impression impression to be updated
     * @throws ImpressionDAOException is thrown when impression is null, has no ID or doesn't exist in DB
     */
    public void deleteImpression(Impression impression) throws ImpressionDAOException;
    
    /**
     * Finds impression with given ID.
     * 
     * @param id id (Long) of searched impression
     * @throws ImpressionDAOException is thrown when inputed ID is null
     * @return impression with given id or null if not found
     */    
    public Impression findImpressionById(Long id) throws ImpressionDAOException;
    
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
     * @param status status of impressions to find
     * @throws ImpressionDAOException is thrown, when inputed Book is null
     * @return List of all impressions of given book or empty list
     */
    public List<Impression> findImpressionsByBook(Book book) throws ImpressionDAOException;
    
}
