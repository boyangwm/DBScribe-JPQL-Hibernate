package com.jspdev.biyesheji.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import com.jspdev.biyesheji.dao.iface.CourseDAO;
import org.hibernate.criterion.Order;

/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseCourseDAO extends com.jspdev.biyesheji.dao._RootDAO {

	public BaseCourseDAO () {}
	
	public BaseCourseDAO (Session session) {
		super(session);
	}

	// query name references


	public static CourseDAO instance;

	/**
	 * Return a singleton of the DAO
	 */
	public static CourseDAO getInstance () {
		if (null == instance) instance = new com.jspdev.biyesheji.dao.CourseDAO();
		return instance;
	}

	public Class getReferenceClass () {
		return com.jspdev.biyesheji.Course.class;
	}

    public Order getDefaultOrder () {
		return Order.asc("name");
    }

	/**
	 * Cast the object as a com.jspdev.biyesheji.Course
	 */
	public com.jspdev.biyesheji.Course cast (Object object) {
		return (com.jspdev.biyesheji.Course) object;
	}

	public com.jspdev.biyesheji.Course get(java.lang.String key)
	{
		return (com.jspdev.biyesheji.Course) get(getReferenceClass(), key);
	}

	public com.jspdev.biyesheji.Course get(java.lang.String key, Session s)
	{
		return (com.jspdev.biyesheji.Course) get(getReferenceClass(), key, s);
	}

	public com.jspdev.biyesheji.Course load(java.lang.String key)
	{
		return (com.jspdev.biyesheji.Course) load(getReferenceClass(), key);
	}

	public com.jspdev.biyesheji.Course load(java.lang.String key, Session s)
	{
		return (com.jspdev.biyesheji.Course) load(getReferenceClass(), key, s);
	}

	public com.jspdev.biyesheji.Course loadInitialize(java.lang.String key, Session s) 
	{ 
		com.jspdev.biyesheji.Course obj = load(key, s); 
		if (!Hibernate.isInitialized(obj)) {
			Hibernate.initialize(obj);
		} 
		return obj; 
	}

/* Generic methods */

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<com.jspdev.biyesheji.Course> findAll () {
		return super.findAll();
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public java.util.List<com.jspdev.biyesheji.Course> findAll (Order defaultOrder) {
		return super.findAll(defaultOrder);
	}

	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 * Use the session given.
	 * @param s the Session
	 */
	public java.util.List<com.jspdev.biyesheji.Course> findAll (Session s, Order defaultOrder) {
		return super.findAll(s, defaultOrder);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param course a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(com.jspdev.biyesheji.Course course)
	{
		return (java.lang.String) super.save(course);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * Use the Session given.
	 * @param course a transient instance of a persistent class
	 * @param s the Session
	 * @return the class identifier
	 */
	public java.lang.String save(com.jspdev.biyesheji.Course course, Session s)
	{
		return (java.lang.String) save((Object) course, s);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param course a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.jspdev.biyesheji.Course course)
	{
		saveOrUpdate((Object) course);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default the
	 * instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the identifier
	 * property mapping. 
	 * Use the Session given.
	 * @param course a transient instance containing new or updated state.
	 * @param s the Session.
	 */
	public void saveOrUpdate(com.jspdev.biyesheji.Course course, Session s)
	{
		saveOrUpdate((Object) course, s);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param course a transient instance containing updated state
	 */
	public void update(com.jspdev.biyesheji.Course course) 
	{
		update((Object) course);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * Use the Session given.
	 * @param course a transient instance containing updated state
	 * @param the Session
	 */
	public void update(com.jspdev.biyesheji.Course course, Session s)
	{
		update((Object) course, s);
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
	 * @param course the instance to be removed
	 */
	public void delete(com.jspdev.biyesheji.Course course)
	{
		delete((Object) course);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * Use the Session given.
	 * @param course the instance to be removed
	 * @param s the Session
	 */
	public void delete(com.jspdev.biyesheji.Course course, Session s)
	{
		delete((Object) course, s);
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
	public void refresh (com.jspdev.biyesheji.Course course, Session s)
	{
		refresh((Object) course, s);
	}


}