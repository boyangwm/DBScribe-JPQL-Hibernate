package pe.edu.unmsm.veterinaria.clinica.dao;

import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Pagoservicio;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPagoServicioDao;

public class PagoServicioDao implements IPagoServicioDao {

	@Override
	public void insertarPagoServicio(Session session, Pagoservicio pagoservicio) throws Exception {
		session.save(pagoservicio);
	}
}
