package pe.edu.unmsm.veterinaria.clinica.services;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pe.edu.unmsm.veterinaria.clinica.entities.Cliente;

/**
*
* @author Dennyz
*/
@ManagedBean
@SessionScoped
public class ClienteService {
	
	private List<Cliente> clientes;

    /**
     * Default constructor. 
     */
    public ClienteService() {
        
    }

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
    
    
  
}
