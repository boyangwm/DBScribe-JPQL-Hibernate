package pe.edu.unmsm.veterinaria.clinica.interfaces;

import java.util.List;
import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Atencionmedica;

public interface IAtencionMedicaDao {

    public void insertarAtencionMedica(Session session, Atencionmedica atencionmedica) throws Exception;
    public List<Atencionmedica> getPendientes(Session session) throws Exception;
    public void modificarAtencionMedica(Session session, Atencionmedica atencionmedica) throws Exception;
}
