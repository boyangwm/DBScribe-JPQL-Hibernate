/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.veterinaria.clinica.dao;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pe.edu.unmsm.veterinaria.clinica.entities.Historial;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IHistorialDao;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IPacienteDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

/**
 *
 * @author DENNYZ
 */
public class HistorialDao implements IHistorialDao {

    @Override
    public Historial getHistorialById(Session session, int id) {
        System.out.println("INICIO DE getHistorialById -- HISTORIAL");
        Historial his = null;
        Paciente pac = null;
        IPacienteDao enlacePaciente = new PacienteDao();
        Transaction transaction = null;

        try {
            System.out.println("SE BUSCA EL HISTORIAL: "+id);
            session = NewHibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Historial as h where h.idHistorial='" + id + "'");
            his = (Historial) query.uniqueResult();
            System.out.println("OBTENGO EL HISTORIAL: "+his.getIdHistorial());
            System.out.println("OBTENGO EL PACIENTE: "+his.getPaciente().getNombre());

//            pac = (Paciente) enlacePaciente.getPacienteById(session, his.getPaciente().getIdPaciente());
//            his.setPaciente(pac);

            transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (session != null) {
                session.close();
            }
        }
        System.out.println("SE RETORNA EL HISTORIAL CON EL PACIENTE DENTRO");
        return his;
    }

}
