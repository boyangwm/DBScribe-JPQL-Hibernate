package com.jspdev.biyesheji.dao.iface;

import java.io.Serializable;

public interface MessageDAO {

	public java.util.List<com.jspdev.biyesheji.Message> findAll ();


	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param message a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.jspdev.biyesheji.Message message);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param message a transient instance containing updated state
	 */
	public void update(com.jspdev.biyesheji.Message message);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param message the instance to be removed
	 */
	public void delete(com.jspdev.biyesheji.Message message);


}