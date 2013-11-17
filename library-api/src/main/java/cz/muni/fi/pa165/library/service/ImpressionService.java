package cz.muni.fi.pa165.library.service;

import cz.muni.fi.pa165.library.to.BookTo;
import cz.muni.fi.pa165.library.to.ImpressionTo;
import cz.muni.fi.pa165.library.enums.DamageType;
import cz.muni.fi.pa165.library.enums.StatusType;
import cz.muni.fi.pa165.library.exceptions.ServiceDataAccessException;
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
     * @throws ServiceDataAccessException is thrown when impressionTO is null or has ID
     * @return the inputed ImpressionTo
     */
    public ImpressionTo addImpression(ImpressionTo impressionTO) throws ServiceDataAccessException;
    
    /**
     * Updates given impression
     * 
     * @param impressionTO ImpressionTo to be updated
     * @throws ServiceDataAccessException is thrown when impressionTO is null, has no ID or doesn't exist in DB
     * @return the inputed impressionTO
     */
    public ImpressionTo updateImpression(ImpressionTo impressionTO) throws ServiceDataAccessException;
    
    /**
     * Deletes given impression
     * 
     * @param impressionTO ImpressionTo to be updated
     * @throws ServiceDataAccessException is thrown when impressionTO is null, has no ID or doesn't exist in DB
     */
    public void deleteImpression(ImpressionTo impressionTO) throws ServiceDataAccessException;
    
    /**
     * Finds impression with given ID.
     * 
     * @param id id (Long) of searched impression
     * @throws ServiceDataAccessException is thrown when inputed ID is null
     * @return ImpressionTo with given id or null if not found
     */    
    public ImpressionTo findImpressionById(Long id) throws ServiceDataAccessException;
    
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
     * @throws ServiceDataAccessException is thrown, when inputed BookTO is null
     * @return List of all impressionTOs of given BookTO or empty list
     */
    public List<ImpressionTo> findImpressionsByBook(BookTo bookTo) throws ServiceDataAccessException;

}
