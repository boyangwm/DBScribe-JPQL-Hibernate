package pe.edu.unmsm.veterinaria.clinica.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import pe.edu.unmsm.veterinaria.clinica.entities.Cliente;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IClienteDao;
import pe.edu.unmsm.veterinaria.clinica.persistencia.NewHibernateUtil;

public class ClienteDao implements IClienteDao {

    @Override
    public Cliente getClientebyId(Session session, Integer idCliente) {
        System.out.println("INICIO DE getClientebyIdPaciente -- CLIENTE");
        Cliente cli = null;

        Transaction transaction = null;

        try {
            System.out.println("SE BUSCA EL CLIENTE POR EL idCliente: "+idCliente);
            session = NewHibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Cliente as c where c.idCliente='" + idCliente + "'");
            cli = (Cliente) query.uniqueResult();
            System.out.println("OBTENGO EL CLIENTE: "+cli.getNombres());

            transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (session != null) {
                session.close();
            }
        }
        System.out.println("SE RETORNA EL CLIENTE");
        return cli;
    }

    @Override
    public List<Cliente> getAll(Session session) throws Exception {
        List<Cliente> clientes = session.createCriteria(Cliente.class).list();
        for (Cliente cli : clientes) {
            Hibernate.initialize(cli.getIdCliente());
            Hibernate.initialize(cli.getNombres());
            Hibernate.initialize(cli.getApellidos());
            Hibernate.initialize(cli.getDireccion());
            Hibernate.initialize(cli.getDistrito());
            Hibernate.initialize(cli.getTelefono());
            Hibernate.initialize(cli.getEmail());
            Hibernate.initialize(cli.getDni());
        }

        return session.createCriteria(Cliente.class).list();
    }

    @Override
    public void insertarCliente(Session session, Cliente cliente) throws Exception {
        session.save(cliente);
    }

    @Override
    public void modificarCliente(Session session, Cliente cliente) throws Exception {
        session.update(cliente);
    }
}
