package pe.edu.unmsm.veterinaria.clinica.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

import pe.edu.unmsm.veterinaria.clinica.dao.ClienteDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Cliente;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IClienteDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
*
* @author Cristian1312
*/
@ManagedBean(name="clienteBean", eager = true)
@ApplicationScoped
public class ClienteBean implements Serializable {
	Session session;
    Transaction transaction;
	
    private Cliente cliente;
    private List<Cliente> clientes;
	
	public ClienteBean() {
		this.cliente = new Cliente();
	}

	public void registrarOperario() {
		this.session = null;
        this.transaction = null;
        
        try {
        	this.session = NewHibernateUtil.getSessionFactory().openSession();
        	IClienteDao clienteDao = new ClienteDao();
        	this.transaction = this.session.beginTransaction();
        	clienteDao.insertarCliente(this.session, this.cliente);
        	this.transaction.commit();
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Correcto", "Cliente registrado correctamente"));
            RequestContext.getCurrentInstance().update("clienteForm:mensajeGeneral");
            this.cliente = new Cliente();
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
	}
	
	public void modificarCliente() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            IClienteDao clienteDao = new ClienteDao();
            this.transaction = this.session.beginTransaction();
            clienteDao.modificarCliente(this.session, this.cliente);
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Correcto", "Registro del cliente modificado correctamente"));
            RequestContext.getCurrentInstance().update("formModificar:msgModificar");
            this.cliente = new Cliente();
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
    }
	
	public List<Cliente> getClientes() {
		this.session = null;
        this.transaction = null;

        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            IClienteDao clienteDao = new ClienteDao();
            this.transaction = this.session.beginTransaction();
            clientes = clienteDao.getAll(this.session);
            this.transaction.commit();
            
            return clientes;
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

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}	
}
