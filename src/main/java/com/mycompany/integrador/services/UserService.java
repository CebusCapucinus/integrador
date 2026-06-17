package com.mycompany.integrador.services;

import com.mycompany.integrador.entities.Usuario;
import com.mycompany.integrador.enums.Rol;
import com.mycompany.integrador.exceptions.DuplicateEmailException;
import com.mycompany.integrador.exceptions.EntityNotFoundException;
import com.mycompany.integrador.exceptions.ValidateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {

    private Map<Long, Usuario> usuarios = new HashMap<>();
    private long nextId = 1;

    public List<Usuario> listar() {
        return usuarios.values().stream()
                .filter(u -> !u.isEliminado())
                .collect(Collectors.toList());
    }

    public Usuario crear(String nombre, String apellido, String mail, String celular, String contrasena, Rol rol)
            throws ValidateException, DuplicateEmailException {
        if (mail == null || mail.trim().isEmpty()) {
            throw new ValidateException("El email no puede estar vacio");
        }
        boolean mailExiste = usuarios.values().stream()
                .anyMatch(u -> !u.isEliminado() && u.getEmail().equalsIgnoreCase(mail));
        if (mailExiste) {
            throw new DuplicateEmailException("Ya existe un usuario con ese mail");
        }
        Usuario usuario = new Usuario(nombre, apellido, mail, celular, contrasena, rol);
        usuario.setId(nextId++);
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    public void editar(Long id, String nombre, String apellido, String email, String celular, String contrasena, Rol rol)
            throws EntityNotFoundException, DuplicateEmailException, ValidateException {
        Usuario usuario = usuarios.get(id);
        if (usuario == null || usuario.isEliminado()) {
            throw new EntityNotFoundException("Usuario no encontrado o eliminado");
        }
        if (email != null && !email.trim().isEmpty()) {
            boolean duplicado = usuarios.values().stream()
                    .anyMatch(u -> !u.isEliminado() && u.getId() != id && u.getEmail().equalsIgnoreCase(email));
            if (duplicado) {
                throw new DuplicateEmailException("El email ya esta en uso");
            }
            usuario.setEmail(email);
        }
        if (nombre != null) {
            usuario.setNombre(nombre);
        }
        if (apellido != null) {
            usuario.setApellido(apellido);
        }
        if (celular != null) {
            usuario.setCelular(celular);
        }
        if (contrasena != null) {
            usuario.setContrasena(contrasena);
        }
        if (rol != null) {
            usuario.setRol(rol);
        }
    }

    public void eliminar(Long id) throws EntityNotFoundException {
        Usuario usuario = usuarios.get(id);
        if (usuario == null || usuario.isEliminado()) {
            throw new EntityNotFoundException("Usuario no encontrado o eliminado");
        }
        usuario.setEliminado(true);
    }

    public Usuario buscarPorId(Long id) throws EntityNotFoundException {
        Usuario usuario = usuarios.get(id);
        if (usuario == null || usuario.isEliminado()) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }
        return usuario;
    }
}
