package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 17/11/2015 10:49:36 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Atencionmedica generated by hbm2java
 */
public class Atencionmedica implements java.io.Serializable {

	private Integer idAtencionMedica;
	private Historial historial;
	private Medicoveterinario medicoveterinario;
	private String estadoAtencionMedica;
	private String temperatura;
	private String vacunas;
	private String peso;
	private String diagnostico;
	private String tratamiento;
	private String proximaCita;
	private String observacionesReceta;
	private Date fechaHoraRegistro;
	private Set recetas = new HashSet(0);
	private Set analisismedicos = new HashSet(0);

	public Atencionmedica() {
	}

	public Atencionmedica(Historial historial, Medicoveterinario medicoveterinario, String estadoAtencionMedica,
			String temperatura, String vacunas, String peso, String diagnostico, String tratamiento, String proximaCita,
			String observacionesReceta, Date fechaHoraRegistro, Set recetas, Set analisismedicos) {
		this.historial = historial;
		this.medicoveterinario = medicoveterinario;
		this.estadoAtencionMedica = estadoAtencionMedica;
		this.temperatura = temperatura;
		this.vacunas = vacunas;
		this.peso = peso;
		this.diagnostico = diagnostico;
		this.tratamiento = tratamiento;
		this.proximaCita = proximaCita;
		this.observacionesReceta = observacionesReceta;
		this.fechaHoraRegistro = fechaHoraRegistro;
		this.recetas = recetas;
		this.analisismedicos = analisismedicos;
	}

	public Integer getIdAtencionMedica() {
		return this.idAtencionMedica;
	}

	public void setIdAtencionMedica(Integer idAtencionMedica) {
		this.idAtencionMedica = idAtencionMedica;
	}

	public Historial getHistorial() {
		return this.historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}

	public Medicoveterinario getMedicoveterinario() {
		return this.medicoveterinario;
	}

	public void setMedicoveterinario(Medicoveterinario medicoveterinario) {
		this.medicoveterinario = medicoveterinario;
	}

	public String getEstadoAtencionMedica() {
		return this.estadoAtencionMedica;
	}

	public void setEstadoAtencionMedica(String estadoAtencionMedica) {
		this.estadoAtencionMedica = estadoAtencionMedica;
	}

	public String getTemperatura() {
		return this.temperatura;
	}

	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}

	public String getVacunas() {
		return this.vacunas;
	}

	public void setVacunas(String vacunas) {
		this.vacunas = vacunas;
	}

	public String getPeso() {
		return this.peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getDiagnostico() {
		return this.diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getTratamiento() {
		return this.tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getProximaCita() {
		return this.proximaCita;
	}

	public void setProximaCita(String proximaCita) {
		this.proximaCita = proximaCita;
	}

	public String getObservacionesReceta() {
		return this.observacionesReceta;
	}

	public void setObservacionesReceta(String observacionesReceta) {
		this.observacionesReceta = observacionesReceta;
	}

	public Date getFechaHoraRegistro() {
		return this.fechaHoraRegistro;
	}

	public void setFechaHoraRegistro(Date fechaHoraRegistro) {
		this.fechaHoraRegistro = fechaHoraRegistro;
	}

	public Set getRecetas() {
		return this.recetas;
	}

	public void setRecetas(Set recetas) {
		this.recetas = recetas;
	}

	public Set getAnalisismedicos() {
		return this.analisismedicos;
	}

	public void setAnalisismedicos(Set analisismedicos) {
		this.analisismedicos = analisismedicos;
	}

}