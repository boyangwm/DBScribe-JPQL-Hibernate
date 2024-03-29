package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 17/11/2015 10:49:36 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Medicoveterinario generated by hbm2java
 */
public class Medicoveterinario implements java.io.Serializable {

	private Integer idMedicoVeterinario;
	private String nombres;
	private String apellidos;
	private Set atencionmedicas = new HashSet(0);

	public Medicoveterinario() {
	}

	public Medicoveterinario(String nombres, String apellidos, Set atencionmedicas) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.atencionmedicas = atencionmedicas;
	}

	public Integer getIdMedicoVeterinario() {
		return this.idMedicoVeterinario;
	}

	public void setIdMedicoVeterinario(Integer idMedicoVeterinario) {
		this.idMedicoVeterinario = idMedicoVeterinario;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Set getAtencionmedicas() {
		return this.atencionmedicas;
	}

	public void setAtencionmedicas(Set atencionmedicas) {
		this.atencionmedicas = atencionmedicas;
	}

}
