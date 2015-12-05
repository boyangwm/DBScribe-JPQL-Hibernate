package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 17/11/2015 10:49:36 PM by Hibernate Tools 4.0.0

/**
 * Receta generated by hbm2java
 */
public class Receta implements java.io.Serializable {

	private Integer idReceta;
	private Atencionmedica atencionmedica;
	private String medicina;
	private String frecuencia;
	private String tiempoDias;

	public Receta() {
	}

	public Receta(Atencionmedica atencionmedica, String medicina, String frecuencia, String tiempoDias) {
		this.atencionmedica = atencionmedica;
		this.medicina = medicina;
		this.frecuencia = frecuencia;
		this.tiempoDias = tiempoDias;
	}

	public Integer getIdReceta() {
		return this.idReceta;
	}

	public void setIdReceta(Integer idReceta) {
		this.idReceta = idReceta;
	}

	public Atencionmedica getAtencionmedica() {
		return this.atencionmedica;
	}

	public void setAtencionmedica(Atencionmedica atencionmedica) {
		this.atencionmedica = atencionmedica;
	}

	public String getMedicina() {
		return this.medicina;
	}

	public void setMedicina(String medicina) {
		this.medicina = medicina;
	}

	public String getFrecuencia() {
		return this.frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getTiempoDias() {
		return this.tiempoDias;
	}

	public void setTiempoDias(String tiempoDias) {
		this.tiempoDias = tiempoDias;
	}

}