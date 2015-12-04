package pe.edu.unmsm.veterinaria.clinica.beans;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pe.edu.unmsm.veterinaria.clinica.dao.PacienteDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPacienteDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
*
* @author Cristian1312
*/
@ManagedBean
@SessionScoped
public class GenerarOrdenMedicaBean implements Serializable {
	Session session;
    Transaction transaction;
    
    private Paciente paciente;
    private List<Paciente> pacientes;
    private String[] selectedServices;
    private List<String> servicios;

    @PostConstruct
    public void init() {
    	this.servicios = new ArrayList<String>();
    	servicios.add("Derivacion");servicios.add("Internamiento");
    	servicios.add("Rayos X");servicios.add("Ecografia Abdominal");
    	servicios.add("Ecografia Cardiaca");servicios.add("Traumatologia");
    	servicios.add("Electrocardiografia");servicios.add("Cirugia Tejidos Blandos");
    	servicios.add("Cirugia Ortopedica");servicios.add("Oftalmologia");
    	servicios.add("Gastroenterologia");servicios.add("Emergencias");
    }
    
    public GenerarOrdenMedicaBean() {
		this.paciente = new Paciente();
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
    
    public void generarOrdenMedicaPaciente(ActionEvent actionEvent, int idPaciente) throws JRException, IOException {
        Map<String, Object> parametros = new HashMap<String, Object>();
        String sexo = "";
        String fechaNac = "";
        String serviciosElegidos = "";
        parametros.put("txtNombreFacultad", "Facultad de Medicina Veterinaria");
        parametros.put("txtUniversidad", "UNMSM");
        parametros.put("txtNombreClinica", "Clinica de Animales Menores");
        
        Paciente paciente = this.getPacienteParaCarnet(idPaciente).get(0);
        if (paciente.getSexo().equals(1)) sexo = "Macho";
        else sexo = "Hembra";
        
        DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		fechaNac = fecha.format(paciente.getFechaNac());
		
		parametros.put("txtSexo", sexo);
        parametros.put("txtFechaNac", fechaNac);
        
        for (int i = 0;i < this.selectedServices.length;i++) {
        	serviciosElegidos = this.selectedServices[i] + ", " + serviciosElegidos;
        }
        
        parametros.put("txtServicios", serviciosElegidos);

        File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/ordenMedicaPaciente.jasper"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(this.getPacienteParaCarnet(idPaciente)));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=ordenMedicaPaciente" + paciente.getNombre() + ".pdf");
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
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

	public String[] getSelectedServices() {
		return selectedServices;
	}

	public void setSelectedServices(String[] selectedServices) {
		this.selectedServices = selectedServices;
	}

	public List<String> getServicios() {
		return servicios;
	}

	public void setServicios(List<String> servicios) {
		this.servicios = servicios;
	}
}
