package cz.muni.fi.pa165.library.dao;

import cz.muni.fi.pa165.library.entity.Book;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.entity.Impression;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.exceptions.ImpressionDaoException;
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
     * @throws ImpressionDaoException is thrown when impression is null or has ID
     * @return the inputed impression
     */
    public Impression addImpression(Impression impression) throws ImpressionDaoException;
    
    /**
     * Updates given impression
     * 
     * @param impression impression to be updated
     * @throws ImpressionDaoException is thrown when impression is null, has no ID or doesn't exist in DB
     * @return the inputed impression
     */
    public Impression updateImpression(Impression impression) throws ImpressionDaoException;
    
    /**
     * Deletes given impression
     * 
     * @param impression impression to be updated
     * @throws ImpressionDaoException is thrown when impression is null, has no ID or doesn't exist in DB
     */
    public void deleteImpression(Impression impression) throws ImpressionDaoException;
    
    /**
     * Finds impression with given ID.
     * 
     * @param id id (Long) of searched impression
     * @throws ImpressionDaoException is thrown when inputed ID is null
     * @return impression with given id or null if not found
     */    
    public Impression findImpressionById(Long id) throws ImpressionDaoException;
    
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
     * @throws ImpressionDaoException is thrown, when inputed Book is null
     * @return List of all impressions of given book or empty list
     */
    public List<Impression> findImpressionsByBook(Book book) throws ImpressionDaoException;
    
}
