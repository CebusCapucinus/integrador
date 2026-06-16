package com.mycompany.integrador.services;

import com.mycompany.integrador.entities.Categoria;
import com.mycompany.integrador.exceptions.EntityNotFoundException;
import com.mycompany.integrador.exceptions.ValidateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryService {

    private Map<Long, Categoria> categorias = new HashMap<>();
    private long nextId = 1;

    public List<Categoria> listar() {
        return categorias.values().stream()
                .filter(c -> !c.isEliminado())
                .collect(Collectors.toList());

    }

    public Categoria crear(String nombre, String descripcion)
            throws ValidateException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidateException("El nombre no puede estar vacio.");
        }
        boolean exists = categorias.values().stream()
                .anyMatch(c -> !c.isEliminado() && c.getNombre().equalsIgnoreCase(nombre));
        if (exists) {
            throw new ValidateException("Ya existe una categoria con ese nombre.");
        }
        Categoria categoria = new Categoria(nombre, descripcion);
        categoria.setId(nextId++);
        categorias.put(categoria.getId(), categoria);
        return categoria;

    }

    public void editar(Long id, String newName, String newDescription)
            throws EntityNotFoundException, ValidateException {
        Categoria categoria = categorias.get(id);
        if (categoria == null || categoria.isEliminado()) {
            throw new EntityNotFoundException("Categoria no encontrada o eliminada");

        }
        if (newName != null && !newName.trim().isEmpty()) {
            boolean duplicate = categorias.values().stream()
                    .anyMatch(c -> !c.isEliminado() && c.getId() != id
                    && c.getNombre().equalsIgnoreCase(newName));
            if (duplicate) {
                throw new ValidateException("Ya existe otra categoria con ese nombre");
            }
            categoria.setNombre(newName);
        }
        if (newDescription != null) {
            categoria.setDescripcion(newDescription);
        }

    }

    public void eliminar(Long id) throws EntityNotFoundException {
        Categoria categoria = categorias.get(id);
        if (categoria == null || categoria.isEliminado()) {
            throw new EntityNotFoundException("Categoria no encontrada o ya eliminada");

        }
        categoria.setEliminado(true);
    }

    public Categoria buscarPorId(Long id) throws EntityNotFoundException {
        Categoria categoria = categorias.get(id);
        if (categoria == null || categoria.isEliminado()) {
            throw new EntityNotFoundException("Categoria no encontrada");
        }
        return categoria;
    }

}
