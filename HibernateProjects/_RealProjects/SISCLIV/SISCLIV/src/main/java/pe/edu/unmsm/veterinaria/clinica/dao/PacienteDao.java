package pe.edu.unmsm.veterinaria.clinica.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Cliente;
import pe.edu.unmsm.veterinaria.clinica.entities.Historial;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPacienteDao;

public class PacienteDao implements IPacienteDao {

	@Override
	public List<Paciente> getAll(Session session) throws Exception {
		List<Paciente> pacientes = session.createCriteria(Paciente.class).list();
        for (Paciente pac : pacientes) {
        	Hibernate.initialize(pac.getCliente());
            Hibernate.initialize(pac.getCastrado());
            Hibernate.initialize(pac.getColorSennas());
            Hibernate.initialize(pac.getCuandoCastrado());
            Hibernate.initialize(pac.getEspecie());
            Hibernate.initialize(pac.getFechaNac());
            Hibernate.initialize(pac.getHistorials());
            Hibernate.initialize(pac.getIdPaciente());
            Hibernate.initialize(pac.getIntacto());
            Hibernate.initialize(pac.getNombre());
            Hibernate.initialize(pac.getRaza());
            Hibernate.initialize(pac.getSexo());
        }

        return session.createCriteria(Paciente.class).list();
	}

	@Override
	public void insertarPaciente(Session session, Paciente paciente) throws Exception {
		session.save(paciente);
		Historial hist = new Historial(paciente);
		hist.setIdHistorial(paciente.getIdPaciente());
		session.save(hist);
	}

	@Override
	public void modificarPaciente(Session session, Paciente paciente) throws Exception {
		session.update(paciente);		
	}

	@Override
	public List<Paciente> getPacienteById(Session session, int id) {
		List<Paciente> paciente = session.createQuery("from Paciente where idPaciente = " + id).list();
        for (Paciente pac : paciente) {
        	Hibernate.initialize(pac.getCliente());
        	Hibernate.initialize(pac.getCastrado());
            Hibernate.initialize(pac.getColorSennas());
            Hibernate.initialize(pac.getCuandoCastrado());
            Hibernate.initialize(pac.getEspecie());
            Hibernate.initialize(pac.getFechaNac());
            Hibernate.initialize(pac.getHistorials());
            Hibernate.initialize(pac.getIdPaciente());
            Hibernate.initialize(pac.getIntacto());
            Hibernate.initialize(pac.getNombre());
            Hibernate.initialize(pac.getRaza());
            Hibernate.initialize(pac.getSexo());
        }
        
        return paciente;
	}
}
