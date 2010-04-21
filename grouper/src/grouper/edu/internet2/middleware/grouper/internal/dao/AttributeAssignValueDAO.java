/*
 * @author mchyzer
 * $Id: AttributeAssignValueDAO.java,v 1.3 2009-10-26 02:26:07 mchyzer Exp $
 */
package edu.internet2.middleware.grouper.internal.dao;

import java.util.Collection;
import java.util.Set;

import edu.internet2.middleware.grouper.attr.value.AttributeAssignValue;
import edu.internet2.middleware.grouper.exception.AttributeAssignValueNotFoundException;

/**
 * attribute assign value data access methods
 */
public interface AttributeAssignValueDAO extends GrouperDAO {
  
  /** 
   * insert or update an attribute assign value object 
   * @param attributeAssignValue 
   */
  public void saveOrUpdate(AttributeAssignValue attributeAssignValue);
  
  /** 
   * delete an attribute assign value object 
   * @param attributeAssignValue 
   */
  public void delete(AttributeAssignValue attributeAssignValue);
  
  /**
   * @param id
   * @param exceptionIfNotFound 
   * @return the attribute assign value or null if not there
   * @throws AttributeAssignValueNotFoundException 
   */
  public AttributeAssignValue findById(String id, boolean exceptionIfNotFound)
    throws AttributeAssignValueNotFoundException;

  /**
   * find values of assignment
   * @param attributeAssignId
   * @return the attribute assign values or empty if not there
   */
  public Set<AttributeAssignValue> findByAttributeAssignId(String attributeAssignId);

  /**
   * find values of assignment
   * @param attributeAssignId
   * @param queryOptions 
   * @return the attribute assign values or empty if not there
   */
  public Set<AttributeAssignValue> findByAttributeAssignId(String attributeAssignId, QueryOptions queryOptions);

  /**
   * save the update properties which are auto saved when business method is called
   * @param attributeAssignValue
   */
  public void saveUpdateProperties(AttributeAssignValue attributeAssignValue);

  /**
   * @param id if find by id, that is it
   * @param idsToIgnore dont return anything in this list, already used or will be used
   * @param attributeAssignId to get values from
   * @param exceptionIfNull 
   * @param valueInteger try to match this if possible
   * @param valueMemberId 
   * @param valueString 
   * @return the attribute assign value or null
   * @throws GrouperDAOException 
   * @since   1.6.0
   */
  AttributeAssignValue findByUuidOrKey(Collection<String> idsToIgnore,
      String id, String attributeAssignId, boolean exceptionIfNull, 
      Long valueInteger, String valueMemberId, String valueString) throws GrouperDAOException;

  
}