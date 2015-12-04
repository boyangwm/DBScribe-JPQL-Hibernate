/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.veterinaria.clinica.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import pe.edu.unmsm.veterinaria.clinica.entities.Usuario;
import pe.edu.unmsm.veterinaria.clinica.interfaces.IUsuarioDao;

/**
 *
 * @author Cristian1312
 */
public class UsuarioDao implements IUsuarioDao {

    @Override
    public Usuario verificarUsuario(Session session, Usuario usuario) throws Exception {
        Usuario user = null;
        String hql = "from Usuario where usuario = '" + usuario.getUsuario()
                + "' and password = '" + usuario.getPassword() + "'";
        Query query = session.createQuery(hql);
        if (!query.list().isEmpty()) {
            user = (Usuario) query.uniqueResult();
        }

        return user;
    }

    @Override
    public Usuario verificarCorreo(Session session, Usuario usuario) throws Exception {
        Usuario operario = null;
        String hql = "from Usuario where email = '" + usuario.getUsuario() + "'";
        Query querys = session.createQuery(hql);
        if (!querys.list().isEmpty()) {
            operario = (Usuario) querys.uniqueResult();
            
        }
        return operario;
    }
    
    @Override
        public void modificarUsuario(Session session, Usuario usuario) throws Exception {
        session.update(usuario);
        System.out.println("usuario modificado");
    }
}
