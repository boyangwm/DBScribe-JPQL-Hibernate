package com.jspdev.biyesheji.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import com.jspdev.biyesheji.dao.iface.MessageDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseMessageDAO extends com.jspdev.biyesheji.dao._RootDAO {

	public BaseMessageDAO () {}
	
	public BaseMessageDAO (Session session) {
		super(session);
	}

	// query name references


	public static MessageDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static MessageDAO getInstance () {
		if (null == instance) instance = new com.jspdev.biyesheji.dao.MessageDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.jspdev.biyesheji.Message.class;
	}

    public Order getDefaultOrder () {
		return Order.asc("name");
    }

	/**
	 * Cast the object as a com.jspdev.biyesheji.Message
	 */
	public com.jspdev.biyesheji.Message cast (Object object) {
		return (com.jspdev.biyesheji.Message) object;
	}

	public com.jspdev.biyesheji.Message get(java.lang.Long key)
	{
		return (com.jspdev.biyesheji.Message) get(getReferenceClass(), key);
	}

	public com.jspdev.biyesheji.Message get(java.lang.Long key, Session s)
	{
		return (com.jspdev.biyesheji.Message) get(getReferenceClass(), key, s);
	}

	public com.jspdev.biyesheji.Message load(java.lang.Long key)
	{
		return (com.jspdev.biyesheji.Message) load(getReferenceClass(), key);
	}

	public com.jspdev.biyesheji.Message load(java.lang.Long key, Session s)
	{
		return (com.jspdev.biyesheji.Message) load(getReferenceClass(), key, s);
	}

	public com.jspdev.biyesheji.Message loadInitialize(java.lang.Long key, Session s) 
	{ 
		com.jspdev.biyesheji.Message obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

/* Generic methods */

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<com.jspdev.biyesheji.Message> findAll () {
		return super.findAll();
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<com.jspdev.biyesheji.Message> findAll (Order defaultOrder) {
		return super.findAll(defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List<com.jspdev.biyesheji.Message> findAll (Session s, Order defaultOrder) {
		return super.findAll(s, defaultOrder);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param message a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Long save(com.jspdev.biyesheji.Message message)
	{
		return (java.lang.Long) super.save(message);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param message a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.Long save(com.jspdev.biyesheji.Message message, Session s)
	{
		return (java.lang.Long) save((Object) message, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param message a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.jspdev.biyesheji.Message message)
	{
		saveOrUpdate((Object) message);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param message a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(com.jspdev.biyesheji.Message message, Session s)
	{
		saveOrUpdate((Object) message, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param message a transient instance containing updated state
	 */
	public void update(com.jspdev.biyesheji.Message message) 
	{
		update((Object) message);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param message a transient instance containing updated state
	 * @param the Session
	 */
	public void update(com.jspdev.biyesheji.Message message, Session s)
	{
		update((Object) message, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Long id)
	{
		delete((Object) load(id));
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param id the instance ID to be removed
	 * @param s the Session
	 */
	public void delete(java.lang.Long id, Session s)
	{
		delete((Object) load(id, s), s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param message the instance to be removed
	 */
	public void delete(com.jspdev.biyesheji.Message message)
	{
		delete((Object) message);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param message the instance to be removed
	 * @param s the Session
	 */
	public void delete(com.jspdev.biyesheji.Message message, Session s)
	{
		delete((Object) message, s);
	}
	
	/**
	 * Re-read the state of the given instance from the underlying database. It is inadvisable to use this to implement
	 * long-running sessions that span many business tasks. This method is, however, useful in certain special circumstances.
	 * For example 
	 * <ul> 
	 * <li>where a database trigger alters the object state upon insert or update</li>
	 * <li>after executing direct SQL (eg. a mass update) in the same session</li>
	 * <li>after inserting a Blob or Clob</li>
	 * </ul>
	 */
	public void refresh (com.jspdev.biyesheji.Message message, Session s)
	{
		refresh((Object) message, s);
	}


}