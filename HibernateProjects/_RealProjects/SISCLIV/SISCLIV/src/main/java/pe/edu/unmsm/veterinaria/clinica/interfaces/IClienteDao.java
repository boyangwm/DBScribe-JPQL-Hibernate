package pe.edu.unmsm.veterinaria.clinica.interfaces;

import java.util.List;

import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Cliente;

public interface IClienteDao {
	public List<Cliente> getAll(Session session) throws Exception;
	public void insertarCliente(Session session, Cliente cliente) throws Exception;
	public void modificarCliente(Session session, Cliente cliente) throws Exception;
        public Cliente getClientebyId(Session session, Integer idCliente);
}