package pe.edu.unmsm.veterinaria.clinica.interfaces;

import java.util.List;

import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Cliente;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;

public interface IPacienteDao {
	public List<Paciente> getAll(Session session) throws Exception;
	public void insertarPaciente(Session session, Paciente paciente) throws Exception;
	public void modificarPaciente(Session session, Paciente paciente) throws Exception;
	public List<Paciente> getPacienteById(Session session, int id);
}