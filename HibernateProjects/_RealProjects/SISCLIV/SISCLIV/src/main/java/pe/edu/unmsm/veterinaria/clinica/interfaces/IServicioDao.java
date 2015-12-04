package pe.edu.unmsm.veterinaria.clinica.interfaces;

import java.util.List;

import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Servicio;

public interface IServicioDao {
	public List<Servicio> getAll(Session session) throws Exception;
}
