/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.veterinaria.clinica.interfaces;

import java.util.List;
import org.hibernate.Session;
import pe.edu.unmsm.veterinaria.clinica.entities.Historial;

/**
 *
 * @author DENNYZ
 */
public interface IHistorialDao {
    public Historial getHistorialById(Session session, int id);
}
