package pe.edu.unmsm.veterinaria.clinica.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import pe.edu.unmsm.veterinaria.clinica.entities.Usuario;

@ManagedBean
@ViewScoped
public class TareasBean {
	public void verificarSesion() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Usuario user = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
            if (user == null) {
                context.getExternalContext().redirect("accesoDenegado.xhtml");
            }
        } catch (Exception e) {
            // log
        }
    }
    
    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
