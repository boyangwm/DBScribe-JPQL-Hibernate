/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.veterinaria.clinica.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pe.edu.unmsm.veterinaria.clinica.dao.UsuarioDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Usuario;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IUsuarioDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
 *
 * @author Cristian1312
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {
	
	Session session;
    Transaction transaction;
	
    private Usuario usuario;
    
    @PostConstruct
    public void init() {
        this.usuario = new Usuario();
    }

	public String login() {
		this.session = null;
        this.transaction = null;
        Usuario user;
        FacesMessage facesMessage = null;
        int perfil;
        String view = null;
        
        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            IUsuarioDao usuarioDao = new UsuarioDao();
            this.transaction = this.session.beginTransaction();
            user = usuarioDao.verificarUsuario(this.session, this.usuario);
            if (user != null) {
            	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", user);
            	perfil = user.getPerfil().getIdPerfil();
                facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", user.getUsuario());
                switch (perfil) {
					case 1:
						view = "tareasMedicoVeterinario?faces-redirect=true";
						break;
					case 3:
						view = "tareasRecepcionista?faces-redirect=true";
						break;
				}
            } else {
            	facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Error",
                        "Usuario y/o contraseña incorrectos.");
            }
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
        
        return view;
    }
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
