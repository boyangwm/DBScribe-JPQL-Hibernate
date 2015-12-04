package pe.edu.unmsm.veterinaria.clinica.beans;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pe.edu.unmsm.veterinaria.clinica.dao.PacienteDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Cliente;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPacienteDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
 *
 * @author Dennyz
 */
@ManagedBean
@SessionScoped
public class PacienteBean implements Serializable {

    Session session;
    Transaction transaction;

    private Cliente cliente;
    private Paciente paciente;
    private List<Paciente> pacientes;

    @ManagedProperty("#{clienteBean}")
    private ClienteBean clienteService;

    public PacienteBean() {
        this.paciente = new Paciente();
        this.cliente = new Cliente();
    }

    public List<Cliente> completeCliente(String query) {
        List<Cliente> clientes = clienteService.getClientes();
        List<Cliente> clientesFiltrados = new ArrayList<Cliente>();

        for (int i = 0; i < clientes.size(); i++) {
            Cliente cl = clientes.get(i);
            if (cl.getDni().startsWith(query)) {
                clientesFiltrados.add(cl);
            }
        }

        return clientesFiltrados;
    }

    public void registrarPaciente() {
        this.session = null;
        this.transaction = null;

        try {
            this.paciente.setCliente(cliente);
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            IPacienteDao pacienteDao = new PacienteDao();
            this.transaction = this.session.beginTransaction();
            pacienteDao.insertarPaciente(this.session, this.paciente);
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Correcto", "Paciente registrado correctamente"));
            RequestContext.getCurrentInstance().update("pacienteForm:mensajeGeneral");
            this.paciente = new Paciente();
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
    
    public void actualizarPaciente() {
        this.session = null;
        this.transaction = null;

        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            IPacienteDao pacienteDao = new PacienteDao();
            this.transaction = this.session.beginTransaction();
            pacienteDao.modificarPaciente(this.session, this.paciente);
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Correcto", "Paciente modificado correctamente"));
            RequestContext.getCurrentInstance().update("pacienteForm:mensajeGeneral");
            this.paciente = new Paciente();
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
    
    public List<Paciente> getPacienteParaCarnet(int idPaciente) {
        this.session = null;
        this.transaction = null;
        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            IPacienteDao pacienteDao = new PacienteDao();
            this.transaction = this.session.beginTransaction();
            List<Paciente> pacienteParaCarnet = pacienteDao.getPacienteById(session, idPaciente);
            this.transaction.commit();
            return pacienteParaCarnet;
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
    
    public void generarCarnetPaciente(ActionEvent actionEvent, int idPaciente) throws JRException, IOException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("txtNombreFacultad", "Facultad de Medicina Veterinaria");
        parametros.put("txtUniversidad", "UNMSM");
        parametros.put("txtNombreClinica", "Clinica de Animales Menores");

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/carnetPaciente.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(this.getPacienteParaCarnet(idPaciente)));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=carnetPaciente.pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ClienteBean getClienteService() {
        return clienteService;
    }

    public void setClienteService(ClienteBean clienteService) {
        this.clienteService = clienteService;
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

}
