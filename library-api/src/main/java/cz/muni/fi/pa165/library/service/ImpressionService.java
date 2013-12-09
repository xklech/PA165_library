package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.ImpressionTo;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import java.util.List;

/**
 *
 * @author Mask
 */
public interface ImpressionService {
    
    /**
     * Saves given impression.
     * 
     * @param impressionTO ImpressionTo to be saved
     * @return ImpressionTo if save was successful, null otherwise
     */
    public ImpressionTo addImpression(ImpressionTo impressionTO);
    
    /**
     * Updates given impression
     * 
     * @param impressionTO ImpressionTo to be updated
     * @return ImpressionTo if update was successful, null otherwise
     */
    public ImpressionTo updateImpression(ImpressionTo impressionTO);
    
    /**
     * Deletes given impression
     * 
     * @param impressionTO ImpressionTo to be updated
     * @return true if impression was deleted, false otherwise
     */
    public boolean deleteImpression(ImpressionTo impressionTO);
    
    /**
     * Finds impression with given ID.
     * 
     * @param id id (Long) of searched 
     * @return ImpressionTo with given id or null if not found
     */    
    public ImpressionTo findImpressionById(Long id);
    
    /**
     * Finds all impressions with given damage level
     * 
     * @param damage damage level of impressions to find
     * @return all impressionTOs with given damage level or empty list
     */
    public List<ImpressionTo> findImpressionsByDamage(DamageType damage);
    
    /**
     * Finds all impressions with given status
     * 
     * @param status status of impressions to find
     * @return all impressionTOs with given status or empty list
     */
    public List<ImpressionTo> findImpressionsByStatus(StatusType status);
    
    /**
     * Finds all impressions of given books
     * 
     * @param bookTo status of impressions to find
     * @return List of all impressionTOs of given BookTO or empty list
     */
    public List<ImpressionTo> findImpressionsByBook(BookTo bookTo);

}
