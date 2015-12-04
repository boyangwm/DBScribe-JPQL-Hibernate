package pe.edu.unmsm.veterinaria.clinica.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pe.edu.unmsm.veterinaria.clinica.entities.Atencionmedica;
import pe.edu.unmsm.veterinaria.clinica.entities.Historial;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IAtencionMedicaDao;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IHistorialDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

public class AtencionMedicaDao implements IAtencionMedicaDao {

    @Override
    public void insertarAtencionMedica(Session session, Atencionmedica atencionmedica) throws Exception {
        session.save(atencionmedica);
    }

    @Override
    public List<Atencionmedica> getPendientes(Session session) throws Exception {

        System.out.println("INICIO DE getPendientes -- ATENCION MEDICA");
        
        List<Atencionmedica> atenciones = null;
        Historial historial = null;
        IHistorialDao enlaceHistorial = new HistorialDao();

        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Atencionmedica as a where a.estadoAtencionMedica='Sin atender'");
            atenciones = (List<Atencionmedica>) query.list();

            System.out.println("SE OBTUVIERON ATENCIONES MEDICAS: "+atenciones.size());

            for (Atencionmedica a : atenciones) {
                System.out.println("SE OBTIENE EL HISTORIAL: "+a.getHistorial().getIdHistorial());
                System.out.println("HISTORIAL CON ESTADO: "+a.getEstadoAtencionMedica());
                historial = (Historial) enlaceHistorial.getHistorialById(session, a.getHistorial().getIdHistorial());
                a.setHistorial(historial);
                System.out.println("SE SETEO EL HISTORIAL: "+historial.getIdHistorial());
//                atenciones.add(a);
            }
            System.out.println("ATENCIONES ? "+ atenciones.get(0).getHistorial().getPaciente().getNombre());
            System.out.println("ATENCIONES ULTIMO? "+ atenciones.get(atenciones.size()-1).getHistorial().getPaciente().getNombre());
            return atenciones;
        } catch (Exception ex) {
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    @Override
    public void modificarAtencionMedica(Session session, Atencionmedica atencionmedica) {
        Transaction transaction = null;
        try {
            session = NewHibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            System.out.println("SE HARA LA ACTUALIZACION");
            session.update(atencionmedica);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                session.getTransaction().rollback();
                System.out.println("ALGO SALIO MAL");
                System.out.println(e.toString());
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
