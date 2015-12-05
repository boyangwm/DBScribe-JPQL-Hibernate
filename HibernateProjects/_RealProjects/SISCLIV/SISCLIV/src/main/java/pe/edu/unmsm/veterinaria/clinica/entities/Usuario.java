package pe.edu.unmsm.veterinaria.clinica.entities;
// Generated 17/11/2015 10:49:36 PM by Hibernate Tools 4.0.0

/**
 * Usuario generated by hbm2java
 */
public class Usuario implements java.io.Serializable {

	private Integer idUsuario;
	private Perfil perfil;
	private String usuario;
	private String password;
	private Integer estado;
	private String nombrecompleto;

	public Usuario() {
	}

	public Usuario(Perfil perfil) {
		this.perfil = perfil;
	}

	public Usuario(Perfil perfil, String usuario, String password, Integer estado, String nombrecompleto) {
		this.perfil = perfil;
		this.usuario = usuario;
		this.password = password;
		this.estado = estado;
		this.nombrecompleto = nombrecompleto;
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getNombrecompleto() {
		return this.nombrecompleto;
	}

	public void setNombrecompleto(String nombrecompleto) {
		this.nombrecompleto = nombrecompleto;
	}

}