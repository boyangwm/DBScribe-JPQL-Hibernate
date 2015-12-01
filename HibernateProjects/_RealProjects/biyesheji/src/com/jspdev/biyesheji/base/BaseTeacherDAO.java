package com.jspdev.biyesheji.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import com.jspdev.biyesheji.dao.iface.TeacherDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTeacherDAO extends com.jspdev.biyesheji.dao._RootDAO {

	public BaseTeacherDAO () {}
	
	public BaseTeacherDAO (Session session) {
		super(session);
	}

	// query name references


	public static TeacherDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static TeacherDAO getInstance () {
		if (null == instance) instance = new com.jspdev.biyesheji.dao.TeacherDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.jspdev.biyesheji.Teacher.class;
	}

    public Order getDefaultOrder () {
		return Order.asc("name");
    }

	/**
	 * Cast the object as a com.jspdev.biyesheji.Teacher
	 */
	public com.jspdev.biyesheji.Teacher cast (Object object) {
		return (com.jspdev.biyesheji.Teacher) object;
	}

	public com.jspdev.biyesheji.Teacher get(java.lang.String key)
	{
		return (com.jspdev.biyesheji.Teacher) get(getReferenceClass(), key);
	}

	public com.jspdev.biyesheji.Teacher get(java.lang.String key, Session s)
	{
		return (com.jspdev.biyesheji.Teacher) get(getReferenceClass(), key, s);
	}

	public com.jspdev.biyesheji.Teacher load(java.lang.String key)
	{
		return (com.jspdev.biyesheji.Teacher) load(getReferenceClass(), key);
	}

	public com.jspdev.biyesheji.Teacher load(java.lang.String key, Session s)
	{
		return (com.jspdev.biyesheji.Teacher) load(getReferenceClass(), key, s);
	}

	public com.jspdev.biyesheji.Teacher loadInitialize(java.lang.String key, Session s) 
	{ 
		com.jspdev.biyesheji.Teacher obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

/* Generic methods */

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<com.jspdev.biyesheji.Teacher> findAll () {
		return super.findAll();
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<com.jspdev.biyesheji.Teacher> findAll (Order defaultOrder) {
		return super.findAll(defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List<com.jspdev.biyesheji.Teacher> findAll (Session s, Order defaultOrder) {
		return super.findAll(s, defaultOrder);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param teacher a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(com.jspdev.biyesheji.Teacher teacher)
	{
		return (java.lang.String) super.save(teacher);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param teacher a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.String save(com.jspdev.biyesheji.Teacher teacher, Session s)
	{
		return (java.lang.String) save((Object) teacher, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param teacher a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.jspdev.biyesheji.Teacher teacher)
	{
		saveOrUpdate((Object) teacher);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param teacher a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(com.jspdev.biyesheji.Teacher teacher, Session s)
	{
		saveOrUpdate((Object) teacher, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param teacher a transient instance containing updated state
	 */
	public void update(com.jspdev.biyesheji.Teacher teacher) 
	{
		update((Object) teacher);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param teacher a transient instance containing updated state
	 * @param the Session
	 */
	public void update(com.jspdev.biyesheji.Teacher teacher, Session s)
	{
		update((Object) teacher, s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
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
	public void delete(java.lang.String id, Session s)
	{
		delete((Object) load(id, s), s);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param teacher the instance to be removed
	 */
	public void delete(com.jspdev.biyesheji.Teacher teacher)
	{
		delete((Object) teacher);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param teacher the instance to be removed
	 * @param s the Session
	 */
	public void delete(com.jspdev.biyesheji.Teacher teacher, Session s)
	{
		delete((Object) teacher, s);
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
	public void refresh (com.jspdev.biyesheji.Teacher teacher, Session s)
	{
		refresh((Object) teacher, s);
	}


}