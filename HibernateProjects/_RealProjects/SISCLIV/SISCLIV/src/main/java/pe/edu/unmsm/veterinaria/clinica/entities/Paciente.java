package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 17/11/2015 10:49:36 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Paciente generated by hbm2java
 */
public class Paciente implements java.io.Serializable {

	private Integer idPaciente;
	private Cliente cliente;
	private String nombre;
	private String especie;
	private String raza;
	private Date fechaNac;
	private Integer sexo;
	private Integer intacto;
	private Integer castrado;
	private Date cuandoCastrado;
	private String colorSennas;
	private Set pagoservicios = new HashSet(0);
	private Set historials = new HashSet(0);

	public Paciente() {
	}

	public Paciente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Paciente(Cliente cliente, String nombre, String especie, String raza, Date fechaNac, Integer sexo,
			Integer intacto, Integer castrado, Date cuandoCastrado, String colorSennas, Set pagoservicios,
			Set historials) {
		this.cliente = cliente;
		this.nombre = nombre;
		this.especie = especie;
		this.raza = raza;
		this.fechaNac = fechaNac;
		this.sexo = sexo;
		this.intacto = intacto;
		this.castrado = castrado;
		this.cuandoCastrado = cuandoCastrado;
		this.colorSennas = colorSennas;
		this.pagoservicios = pagoservicios;
		this.historials = historials;
	}

	public Integer getIdPaciente() {
		return this.idPaciente;
	}

	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEspecie() {
		return this.especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public String getRaza() {
		return this.raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public Date getFechaNac() {
		return this.fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public Integer getSexo() {
		return this.sexo;
	}

	public void setSexo(Integer sexo) {
		this.sexo = sexo;
	}

	public Integer getIntacto() {
		return this.intacto;
	}

	public void setIntacto(Integer intacto) {
		this.intacto = intacto;
	}

	public Integer getCastrado() {
		return this.castrado;
	}

	public void setCastrado(Integer castrado) {
		this.castrado = castrado;
	}

	public Date getCuandoCastrado() {
		return this.cuandoCastrado;
	}

	public void setCuandoCastrado(Date cuandoCastrado) {
		this.cuandoCastrado = cuandoCastrado;
	}

	public String getColorSennas() {
		return this.colorSennas;
	}

	public void setColorSennas(String colorSennas) {
		this.colorSennas = colorSennas;
	}

	public Set getPagoservicios() {
		return this.pagoservicios;
	}

	public void setPagoservicios(Set pagoservicios) {
		this.pagoservicios = pagoservicios;
	}

	public Set getHistorials() {
		return this.historials;
	}

	public void setHistorials(Set historials) {
		this.historials = historials;
	}

}