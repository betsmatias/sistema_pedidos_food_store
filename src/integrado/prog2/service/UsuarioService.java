/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ValidacionException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matia
 */
public class UsuarioService {
  private List<Usuario> usuarios;
    private Long contadorId;

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
        this.contadorId = 1L;
    }

    // HU-USR-01 - Listar usuarios
    public List<Usuario> listarUsuarios() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                activos.add(u);
            }
        }
        return activos;
    }

    // HU-USR-02 - Crear usuario
    public Usuario crearUsuario(String nombre, String apellido, String mail, 
                                String celular, String contraseña, Rol rol) throws ValidacionException {
        
        if (mail == null || mail.trim().isEmpty()) {
            throw new ValidacionException("Error: El mail no puede estar vacio.");
        }
        
        // Validar que el mail sea único recorriendo la colección
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(mail)) {
                throw new ValidacionException("Error: Ya existe un usuario registrado con el mail " + mail);
            }
        }

        Usuario nuevo = new Usuario(contadorId++, nombre, apellido, mail, celular, contraseña, rol);
        usuarios.add(nuevo);
        return nuevo;
    }

    // Método auxiliar
    public Usuario buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
        throw new EntidadNoEncontradaException("Error: No se encontro ningun usuario con el ID " + id);
    }

    // HU-USR-03 - Editar usuario
    public void editarUsuario(Long id, String nNombre, String nApellido, String nMail, 
                              String nCelular, String nContraseña, Rol nRol) 
                              throws EntidadNoEncontradaException, ValidacionException {
        
        Usuario u = buscarPorId(id);

        if (nMail != null && !nMail.trim().isEmpty()) {
            // Validar que el nuevo mail no lo esté usando OTRA persona
            for (Usuario otro : usuarios) {
                if (!otro.isEliminado() && !otro.getId().equals(id) && otro.getMail().equalsIgnoreCase(nMail)) {
                    throw new ValidacionException("Error: El mail " + nMail + " ya esta en uso por otro usuario.");
                }
            }
            u.setMail(nMail);
        }

        if (nNombre != null && !nNombre.trim().isEmpty()) u.setNombre(nNombre);
        if (nApellido != null && !nApellido.trim().isEmpty()) u.setApellido(nApellido);
        if (nCelular != null && !nCelular.trim().isEmpty()) u.setCelular(nCelular);
        if (nContraseña != null && !nContraseña.trim().isEmpty()) u.setContraseña(nContraseña);
        if (nRol != null) u.setRol(nRol);
    }

    // HU-USR-04 - Eliminar usuario
    public void eliminarUsuario(Long id) throws EntidadNoEncontradaException {
        Usuario u = buscarPorId(id);
        u.setEliminado(true); // Baja lógica
    }
}  

