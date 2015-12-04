package pe.edu.unmsm.veterinaria.clinica.services;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pe.edu.unmsm.veterinaria.clinica.dao.PacienteDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPacienteDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
*
* @author Gian
*/
@ManagedBean(name="registrarPagoService", eager = true)
@ApplicationScoped
public class RegistrarPagoService implements Serializable {
	Session session;
    Transaction transaction;
	
    private Paciente paciente;
	private List<Paciente> pacientes;

    public RegistrarPagoService() {
        this.paciente = new Paciente();
    }

    public List<Paciente> getPacientes() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            IPacienteDao pacienteDao = new PacienteDao();
            this.transaction = this.session.beginTransaction();
            pacientes = pacienteDao.getAll(this.session);
            this.transaction.commit();
            
            return pacientes;
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
            return null;
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
}
