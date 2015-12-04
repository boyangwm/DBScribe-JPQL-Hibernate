package pe.edu.unmsm.veterinaria.clinica.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import pe.edu.unmsm.veterinaria.clinica.beans.ClienteBean;
import pe.edu.unmsm.veterinaria.clinica.entities.Cliente;

@FacesConverter("clienteConverter")
public class ClienteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		Cliente c = null;
		if (value != null && value.trim().length() > 0 && value.length() > 0) {
			ClienteBean service = (ClienteBean) fc.getExternalContext().getApplicationMap().get("clienteBean");
			List<Cliente> clientes = service.getClientes();
			for (int i = 0; i < clientes.size(); i++) {
				if (clientes.get(i).getDni().equals(value)) {
					c = clientes.get(i);
				}
			}

		} else {
			c = new Cliente();
		}
		return c;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return ((Cliente) object).getDni();
		} else {
			return null;
		}
	}

}
