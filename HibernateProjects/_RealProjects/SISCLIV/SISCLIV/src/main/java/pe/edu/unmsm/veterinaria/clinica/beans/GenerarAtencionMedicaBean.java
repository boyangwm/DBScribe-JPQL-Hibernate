package pe.edu.unmsm.veterinaria.clinica.beans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pe.edu.unmsm.veterinaria.clinica.dao.AtencionMedicaDao;
import pe.edu.unmsm.veterinaria.clinica.dao.ClienteDao;
import pe.edu.unmsm.veterinaria.clinica.entities.Atencionmedica;
import pe.edu.unmsm.veterinaria.clinica.entities.Cliente;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IAtencionMedicaDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
 *
 * @author DENNYZ
 */
@ManagedBean
@SessionScoped
public class GenerarAtencionMedicaBean {

    /**
     * Creates a new instance of GenerarAtencionMedicaBean
     */
    List<Atencionmedica> atenciones;

    Atencionmedica atencion;

    Session session;
    Transaction transaction;

    public GenerarAtencionMedicaBean() {
    }

    public String registrarAtencion() {
        System.out.println("SE INICIA registrarAtencion");
        this.session = null;
        ClienteDao clienteDao = new ClienteDao();
        Cliente cliente = clienteDao.getClientebyId(session, atencion.getHistorial().getPaciente().getCliente().getIdCliente());
        System.out.println("CLIENTE: " + cliente.getNombres());

        Paciente p = atencion.getHistorial().getPaciente();
        p.setCliente(cliente);

        return "registrarAtencionMedica.xhtml";
    }

    public String finalizarAtencion() {
        this.session = null;

        System.out.println("SE INICIA finalizarAtencion");
        atencion.setEstadoAtencionMedica("Atendido");
        atencion.setProximaCita(atencion.getProximaCita().substring(0, 11));;
        System.out.println("ATENCION:");
        System.out.println(atencion.getDiagnostico());
        System.out.println(atencion.getObservacionesReceta());
        System.out.println(atencion.getPeso());
        System.out.println(atencion.getProximaCita());
        AtencionMedicaDao atencionMedicaDao = new AtencionMedicaDao();
        atencionMedicaDao.modificarAtencionMedica(session, atencion);
        System.out.println("FINALIZO UPDATE DE ACTUALIZACION MEDICA");
        atenciones = null;
        atenciones = getAtenciones();
        return "generarAtencionMedica.xhtml";
    }

    public List<Atencionmedica> getAtenciones() {
        System.out.println("INICIO DE getAtenciones");
        this.session = null;
        this.transaction = null;
        try {
            this.session = NewHibernateUtil.getSessionFactory().openSession();
            IAtencionMedicaDao atencionmedicaDao = new AtencionMedicaDao();
            this.transaction = this.session.beginTransaction();
            atenciones = atencionmedicaDao.getPendientes(session);
            this.transaction.commit();
            System.out.println("cantidad de atenciones pendientes: " + atenciones.size());
            return atenciones;
        } catch (Exception ex) {
            if (this.transaction != null) {
                transaction.rollback();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Error", ex.getMessage()));
            System.out.println("retorna null");
            return null;
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    public void setAtenciones(List<Atencionmedica> atenciones) {
        this.atenciones = atenciones;
    }

    public Atencionmedica getAtencion() {
        return atencion;
    }

    public void setAtencion(Atencionmedica atencion) {
        this.atencion = atencion;
    }

}
