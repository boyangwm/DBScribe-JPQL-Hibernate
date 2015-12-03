package com.jspdev.biyesheji.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import com.jspdev.biyesheji.dao.iface.ScDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseScDAO extends com.jspdev.biyesheji.dao._RootDAO {

	public BaseScDAO () {}
	
	public BaseScDAO (Session session) {
		super(session);
	}

	// query name references


	public static ScDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static ScDAO getInstance () {
		if (null == instance) instance = new com.jspdev.biyesheji.dao.ScDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.jspdev.biyesheji.Sc.class;
	}

    public Order getDefaultOrder () {
		return Order.asc("name");
    }

	/**
	 * Cast the object as a com.jspdev.biyesheji.Sc
	 */
	public com.jspdev.biyesheji.Sc cast (Object object) {
		return (com.jspdev.biyesheji.Sc) object;
	}

	public com.jspdev.biyesheji.Sc get(java.lang.Long key)
	{
		return (com.jspdev.biyesheji.Sc) get(getReferenceClass(), key);
	}

	public com.jspdev.biyesheji.Sc get(java.lang.Long key, Session s)
	{
		return (com.jspdev.biyesheji.Sc) get(getReferenceClass(), key, s);
	}

	public com.jspdev.biyesheji.Sc load(java.lang.Long key)
	{
		return (com.jspdev.biyesheji.Sc) load(getReferenceClass(), key);
	}

	public com.jspdev.biyesheji.Sc load(java.lang.Long key, Session s)
	{
		return (com.jspdev.biyesheji.Sc) load(getReferenceClass(), key, s);
	}

	public com.jspdev.biyesheji.Sc loadInitialize(java.lang.Long key, Session s) 
	{ 
		com.jspdev.biyesheji.Sc obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

/* Generic methods */

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<com.jspdev.biyesheji.Sc> findAll () {
		return super.findAll();
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<com.jspdev.biyesheji.Sc> findAll (Order defaultOrder) {
		return super.findAll(defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List<com.jspdev.biyesheji.Sc> findAll (Session s, Order defaultOrder) {
		return super.findAll(s, defaultOrder);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param sc a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Long save(com.jspdev.biyesheji.Sc sc)
	{
		return (java.lang.Long) super.save(sc);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param sc a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.Long save(com.jspdev.biyesheji.Sc sc, Session s)
	{
		return (java.lang.Long) save((Object) sc, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param sc a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.jspdev.biyesheji.Sc sc)
	{
		saveOrUpdate((Object) sc);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param sc a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(com.jspdev.biyesheji.Sc sc, Session s)
	{
		saveOrUpdate((Object) sc, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param sc a transient instance containing updated state
	 */
	public void update(com.jspdev.biyesheji.Sc sc) 
	{
		update((Object) sc);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param sc a transient instance containing updated state
	 * @param the Session
	 */
	public void update(com.jspdev.biyesheji.Sc sc, Session s)
	{
		update((Object) sc, s);
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
	 * @param sc the instance to be removed
	 */
	public void delete(com.jspdev.biyesheji.Sc sc)
	{
		delete((Object) sc);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param sc the instance to be removed
	 * @param s the Session
	 */
	public void delete(com.jspdev.biyesheji.Sc sc, Session s)
	{
		delete((Object) sc, s);
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
	public void refresh (com.jspdev.biyesheji.Sc sc, Session s)
	{
		refresh((Object) sc, s);
	}


}