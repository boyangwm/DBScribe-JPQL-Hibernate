package pe.edu.unmsm.veterinaria.clinica.interfaces;

import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Pagoservicio;

public interface IPagoServicioDao {
	public void insertarPagoServicio(Session session, Pagoservicio pagoservicio) throws Exception;
}
