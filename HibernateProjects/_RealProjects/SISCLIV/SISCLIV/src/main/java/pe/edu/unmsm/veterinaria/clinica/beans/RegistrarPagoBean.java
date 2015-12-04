package pe.edu.unmsm.veterinaria.clinica.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;

import pe.edu.unmsm.veterinaria.clinica.dao.AtencionMedicaDao;
import pe.edu.unmsm.veterinaria.clinica.dao.PagoServicioDao;
import pe.edu.unmsm.veterinaria.clinica.dao.ServicioDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Atencionmedica;
import pe.edu.unmsm.veterinaria.clinica.entities.Cliente;
import pe.edu.unmsm.veterinaria.clinica.entities.Historial;
import pe.edu.unmsm.veterinaria.clinica.entities.Medicoveterinario;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;
import pe.edu.unmsm.veterinaria.clinica.entities.Pagoservicio;
import pe.edu.unmsm.veterinaria.clinica.entities.Servicio;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IAtencionMedicaDao;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPagoServicioDao;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IServicioDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;
import pe.edu.unmsm.veterinaria.clinica.services.RegistrarPagoService;

@ManagedBean
@SessionScoped
public class RegistrarPagoBean implements Serializable {
	Session session;
    Transaction transaction;
    
    private Pagoservicio pagoservicio;
    private Paciente paciente;
    private Servicio servicio;
    private List<SelectItem> selectItemsOneServicio;
    
    @ManagedProperty("#{registrarPagoService}")
    private RegistrarPagoService registrarPagoService;
    
    public RegistrarPagoBean() {
		this.paciente = new Paciente();
		this.servicio = new Servicio();
	}

	public List<Paciente> completePaciente(String query) {
        List<Paciente> pacientes = this.registrarPagoService.getPacientes();
        List<Paciente> pacientesFiltrados = new ArrayList<Paciente>();

        for (int i = 0; i < pacientes.size(); i++) {
        	Paciente pa = pacientes.get(i);
            if (String.valueOf(pa.getIdPaciente()).startsWith(query)) {
                pacientesFiltrados.add(pa);
            }
        }

        return pacientesFiltrados;
    }
	
	public void registrarPagoServicio() {
		this.session = null;
        this.transaction = null;
        
        try {
        	this.session = NewHibernateUtil.getSessionFactory().openSession();
        	IPagoServicioDao pagoServicioDao = new PagoServicioDao();
        	IAtencionMedicaDao atencionMedicaDao = new AtencionMedicaDao();
        	this.transaction = this.session.beginTransaction();
        	Pagoservicio pagoServicio = new Pagoservicio(this.servicio, this.paciente, new Date(), null, null);
        	Historial historial = new Historial();historial.setIdHistorial(this.paciente.getIdPaciente());
        	Medicoveterinario mv = new Medicoveterinario();mv.setIdMedicoVeterinario(1);
        	Atencionmedica atencionMedica = new Atencionmedica(historial, mv, "Sin atender", null, null,
        			null, null, null, null, null, null, null, null);
        	pagoServicioDao.insertarPagoServicio(this.session, pagoServicio);
        	atencionMedicaDao.insertarAtencionMedica(this.session, atencionMedica);
        	this.transaction.commit();
        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Correcto", "Pago registrado correctamente"));
            RequestContext.getCurrentInstance().update("formPaciente:msgPagar");
            pagoServicio = new Pagoservicio();
            atencionMedica = new Atencionmedica();
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

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public List<SelectItem> getSelectItemsOneServicio() {
		this.session = null;
        this.transaction = null;
        
        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            this.selectItemsOneServicio = new ArrayList<>();
            IServicioDao servicioDao = new ServicioDao();
            this.transaction = this.session.beginTransaction();
            List<Servicio> servicios = servicioDao.getAll(this.session);
            selectItemsOneServicio.clear();
            for (Servicio servicio : servicios) {
                SelectItem selectItem = new SelectItem(servicio.getIdServicio(), servicio.getNombre());
                this.selectItemsOneServicio.add(selectItem);
            }
            this.transaction.commit();
            
            return selectItemsOneServicio;
        } catch (Exception ex) {
            if(this.transaction != null) {
                transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
            return null;
        } finally {
            if(this.session != null) {
                this.session.close();
            }
        }
	}

	public void setSelectItemsOneServicio(List<SelectItem> selectItemsOneServicio) {
		this.selectItemsOneServicio = selectItemsOneServicio;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public RegistrarPagoService getRegistrarPagoService() {
		return registrarPagoService;
	}

	public void setRegistrarPagoService(RegistrarPagoService registrarPagoService) {
		this.registrarPagoService = registrarPagoService;
	}

	public Pagoservicio getPagoservicio() {
		return pagoservicio;
	}

	public void setPagoservicio(Pagoservicio pagoservicio) {
		this.pagoservicio = pagoservicio;
	}
}
