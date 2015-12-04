package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 17/11/2015 10:49:36 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Servicio generated by hbm2java
 */
public class Servicio implements java.io.Serializable {

	private Integer idServicio;
	private String nombre;
	private BigDecimal tarifa;
	private Integer tipo;
	private String descripcionCorta;
	private String detalles;
	private Set pagoservicios = new HashSet(0);

	public Servicio() {
	}

	public Servicio(String nombre, BigDecimal tarifa, Integer tipo, String descripcionCorta, String detalles,
			Set pagoservicios) {
		this.nombre = nombre;
		this.tarifa = tarifa;
		this.tipo = tipo;
		this.descripcionCorta = descripcionCorta;
		this.detalles = detalles;
		this.pagoservicios = pagoservicios;
	}

	public Integer getIdServicio() {
		return this.idServicio;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getTarifa() {
		return this.tarifa;
	}

	public void setTarifa(BigDecimal tarifa) {
		this.tarifa = tarifa;
	}

	public Integer getTipo() {
		return this.tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getDescripcionCorta() {
		return this.descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public String getDetalles() {
		return this.detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public Set getPagoservicios() {
		return this.pagoservicios;
	}

	public void setPagoservicios(Set pagoservicios) {
		this.pagoservicios = pagoservicios;
	}

}
