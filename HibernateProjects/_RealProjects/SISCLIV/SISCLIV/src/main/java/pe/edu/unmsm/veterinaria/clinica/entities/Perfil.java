package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 17/11/2015 10:49:36 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Perfil generated by hbm2java
 */
public class Perfil implements java.io.Serializable {

	private Integer idPerfil;
	private String descripcion;
	private Set usuarios = new HashSet(0);
	private Set opcions = new HashSet(0);

	public Perfil() {
	}

	public Perfil(String descripcion, Set usuarios, Set opcions) {
		this.descripcion = descripcion;
		this.usuarios = usuarios;
		this.opcions = opcions;
	}

	public Integer getIdPerfil() {
		return this.idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set usuarios) {
		this.usuarios = usuarios;
	}

	public Set getOpcions() {
		return this.opcions;
	}

	public void setOpcions(Set opcions) {
		this.opcions = opcions;
	}

}
