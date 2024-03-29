package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 17/11/2015 10:49:36 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Clinicoveterinario generated by hbm2java
 */
public class Clinicoveterinario implements java.io.Serializable {

	private Integer idClinicoVeterinario;
	private String nombres;
	private String apellidos;
	private Set analisismedicos = new HashSet(0);

	public Clinicoveterinario() {
	}

	public Clinicoveterinario(String nombres, String apellidos, Set analisismedicos) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.analisismedicos = analisismedicos;
	}

	public Integer getIdClinicoVeterinario() {
		return this.idClinicoVeterinario;
	}

	public void setIdClinicoVeterinario(Integer idClinicoVeterinario) {
		this.idClinicoVeterinario = idClinicoVeterinario;
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

	public Set getAnalisismedicos() {
		return this.analisismedicos;
	}

	public void setAnalisismedicos(Set analisismedicos) {
		this.analisismedicos = analisismedicos;
	}

}
