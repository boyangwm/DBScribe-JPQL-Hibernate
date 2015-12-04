package pe.edu.unmsm.veterinaria.clinica.services;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;

/**
*
* @author Gian
*/
@ManagedBean
@SessionScoped
public class PacienteService {
	
	private List<Paciente> pacientes;

    /**
     * Default constructor. 
     */
    public PacienteService() {
        
    }

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}
    
    
  
}
