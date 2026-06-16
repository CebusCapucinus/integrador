package com.mycompany.integrador.services;

import com.mycompany.integrador.entities.Categoria;
import com.mycompany.integrador.entities.Producto;
import com.mycompany.integrador.exceptions.EntityNotFoundException;
import com.mycompany.integrador.exceptions.ValidateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductService {

    private Map<Long, Producto> productos = new HashMap<>();
    private long nextId = 1;
    private CategoryService categoryService;

    public ProductService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<Producto> listar() {
        return productos.values().stream()
                .filter(p -> !p.isEliminado())
                .collect(Collectors.toList());
    }

    public List<Producto> listByCategory(Long categoriaId)
            throws EntityNotFoundException {

        Categoria categoria = categoryService.buscarPorId(categoriaId);
        return productos.values().stream()
                .filter(p -> !p.isEliminado() && p.getCategoria() != null
                && p.getCategoria().getId().equals(categoria.getId()))
                .collect(Collectors.toList());

    }

    public Producto crear(String nombre, String descripcion, double precio,
            int stock, String imagen, boolean disponible, Long categoriaId)
            throws ValidateException, EntityNotFoundException {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidateException("Nombre de producto vacio.");
        }
        if (precio < 0) {
            throw new ValidateException("El precio no puede ser negativo.");
        }
        if (stock < 0) {
            throw new ValidateException("El stock no puede ser negativo.");
        }

        Categoria categoria = categoryService.buscarPorId(categoriaId);
        Producto producto = new Producto(nombre, descripcion, precio, stock,
                imagen, disponible, categoria);
        producto.setId(nextId++);
        productos.put(producto.getId(), producto);
        return producto;
    }
}
