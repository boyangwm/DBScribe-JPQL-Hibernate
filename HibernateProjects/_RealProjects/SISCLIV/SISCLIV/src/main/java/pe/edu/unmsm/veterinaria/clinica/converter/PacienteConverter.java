package pe.edu.unmsm.veterinaria.clinica.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import pe.edu.unmsm.veterinaria.clinica.beans.PacienteBean;
import pe.edu.unmsm.veterinaria.clinica.beans.RegistrarPagoBean;
import pe.edu.unmsm.veterinaria.clinica.entities.Paciente;
import pe.edu.unmsm.veterinaria.clinica.services.RegistrarPagoService;

@FacesConverter("pacienteConverter")
public class PacienteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		Paciente p = null;
		if (value != null && value.trim().length() > 0 && value.length() > 0) {
			RegistrarPagoService service = (RegistrarPagoService) fc.getExternalContext().getApplicationMap().get("registrarPagoService");
			List<Paciente> pacientes = service.getPacientes();
			for (int i = 0; i < pacientes.size(); i++) {
				if (String.valueOf(pacientes.get(i).getIdPaciente()).equals(value)) {
					p = pacientes.get(i);
				}
			}	

		} else {
			p = new Paciente();
		}
		return p;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(((Paciente) object).getIdPaciente());
		} else {
			return null;
		}
	}

}
