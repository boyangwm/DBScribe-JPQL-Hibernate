package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 17/11/2015 10:49:36 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;

/**
 * Pagoservicio generated by hbm2java
 */
public class Pagoservicio implements java.io.Serializable {

	private Integer idPagoServicio;
	private Servicio servicio;
	private Paciente paciente;
	private Date fechaHoraPago;
	private Integer cantidad;
	private BigDecimal pagoServicioTotal;

	public Pagoservicio() {
	}

	public Pagoservicio(Servicio servicio, Paciente paciente, Date fechaHoraPago, Integer cantidad,
			BigDecimal pagoServicioTotal) {
		this.servicio = servicio;
		this.paciente = paciente;
		this.fechaHoraPago = fechaHoraPago;
		this.cantidad = cantidad;
		this.pagoServicioTotal = pagoServicioTotal;
	}

	public Integer getIdPagoServicio() {
		return this.idPagoServicio;
	}

	public void setIdPagoServicio(Integer idPagoServicio) {
		this.idPagoServicio = idPagoServicio;
	}

	public Servicio getServicio() {
		return this.servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Date getFechaHoraPago() {
		return this.fechaHoraPago;
	}

	public void setFechaHoraPago(Date fechaHoraPago) {
		this.fechaHoraPago = fechaHoraPago;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPagoServicioTotal() {
		return this.pagoServicioTotal;
	}

	public void setPagoServicioTotal(BigDecimal pagoServicioTotal) {
		this.pagoServicioTotal = pagoServicioTotal;
	}

}