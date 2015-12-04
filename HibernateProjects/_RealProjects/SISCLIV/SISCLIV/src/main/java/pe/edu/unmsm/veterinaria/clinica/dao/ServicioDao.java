package pe.edu.unmsm.veterinaria.clinica.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Servicio;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IServicioDao;

public class ServicioDao implements IServicioDao {

	@Override
	public List<Servicio> getAll(Session session) throws Exception {
		return session.createCriteria(Servicio.class).list();
	}
}
