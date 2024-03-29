package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 17/11/2015 10:49:36 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Cliente generated by hbm2java
 */
public class Cliente implements java.io.Serializable {

	private Integer idCliente;
	private String nombres;
	private String apellidos;
	private String direccion;
	private String distrito;
	private String telefono;
	private String email;
	private String dni;
	private Set pacientes = new HashSet(0);

	public Cliente() {
	}

	public Cliente(String nombres, String apellidos, String direccion, String distrito, String telefono, String email,
			String dni, Set pacientes) {
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.distrito = distrito;
		this.telefono = telefono;
		this.email = email;
		this.dni = dni;
		this.pacientes = pacientes;
	}

	public Integer getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
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

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDistrito() {
		return this.distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Set getPacientes() {
		return this.pacientes;
	}

	public void setPacientes(Set pacientes) {
		this.pacientes = pacientes;
	}

}
