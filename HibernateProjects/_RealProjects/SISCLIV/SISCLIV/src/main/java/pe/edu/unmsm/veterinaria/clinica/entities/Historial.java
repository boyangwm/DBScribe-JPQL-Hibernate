package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 17/11/2015 10:49:36 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Historial generated by hbm2java
 */
public class Historial implements java.io.Serializable {

	private Integer idHistorial;
	private Paciente paciente;
	private Set atencionmedicas = new HashSet(0);

	public Historial() {
	}

	public Historial(Paciente paciente) {
		this.paciente = paciente;
	}

	public Historial(Paciente paciente, Set atencionmedicas) {
		this.paciente = paciente;
		this.atencionmedicas = atencionmedicas;
	}

	public Integer getIdHistorial() {
		return this.idHistorial;
	}

	public void setIdHistorial(Integer idHistorial) {
		this.idHistorial = idHistorial;
	}

	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Set getAtencionmedicas() {
		return this.atencionmedicas;
	}

	public void setAtencionmedicas(Set atencionmedicas) {
		this.atencionmedicas = atencionmedicas;
	}

}
