package com.mycompany.integrador.services;

import com.mycompany.integrador.entities.Pedido;
import com.mycompany.integrador.entities.Producto;
import com.mycompany.integrador.entities.Usuario;
import com.mycompany.integrador.enums.Estado;
import com.mycompany.integrador.enums.FormaPago;
import com.mycompany.integrador.exceptions.EntityNotFoundException;
import com.mycompany.integrador.exceptions.ValidateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService {
    
    private Map<Long, Pedido> pedidos = new HashMap<>();
    private long nextId = 1;
    private UserService userService;
    private ProductService productService;

    public OrderService(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    public List<Pedido> listar() {
        return pedidos.values().stream()
                .filter(p -> !p.isEliminado())
                .collect(Collectors.toList());
    }

    public List<Pedido> listarPorUsuario(Long usuarioId) throws EntityNotFoundException {
        Usuario usuario = userService.buscarPorId(usuarioId);
        return pedidos.values().stream()
                .filter(p -> !p.isEliminado() && p.getUsuario().getId().equals(usuario.getId()))
                .collect(Collectors.toList());
    }

    public Pedido crear(Long usuarioId, FormaPago formaPago, Map<Producto, Integer> productosYCantidades)
            throws EntityNotFoundException, ValidateException {
        Usuario usuario = userService.buscarPorId(usuarioId);
        Pedido pedido = new Pedido(usuario, formaPago);
        for (Map.Entry<Producto, Integer> entry : productosYCantidades.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            if (cantidad <= 0) {
                throw new ValidateException("Cantidad debe ser mayor a 0 para " + producto.getNombre());
            }
            if (producto.getStock() < cantidad) {
                throw new ValidateException("Stock insuficiente para " + producto.getNombre());
            }
            pedido.addDetallePedido(cantidad, producto);
        }
        for (Map.Entry<Producto, Integer> entry : productosYCantidades.entrySet()) {
            productService.reducirStock(entry.getKey().getId(), entry.getValue());
        }
        pedido.setId(nextId++);
        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    public void actualizarEstadoYFormaPago(Long pedidoId, Estado nuevoEstado, FormaPago nuevaFormaPago)
            throws EntityNotFoundException {
        Pedido pedido = pedidos.get(pedidoId);
        if (pedido == null || pedido.isEliminado()) {
            throw new EntityNotFoundException("Pedido no encontrado o eliminado");
        }
        if (nuevoEstado != null) {
            pedido.setEstado(nuevoEstado);
        }
        if (nuevaFormaPago != null) {
            pedido.setFormaPago(nuevaFormaPago);
        }
        pedido.setTotal(pedido.calcularTotal());
    }

    public void eliminar(Long pedidoId) throws EntityNotFoundException {
        Pedido pedido = pedidos.get(pedidoId);
        if (pedido == null || pedido.isEliminado()) {
            throw new EntityNotFoundException("Pedido no encontrado o eliminado");
        }
        pedido.setEliminado(true);
    }

    public Pedido buscarPorId(Long id) throws EntityNotFoundException {
        Pedido pedido = pedidos.get(id);
        if (pedido == null || pedido.isEliminado()) {
            throw new EntityNotFoundException("Pedido no encontrado");
        }
        return pedido;
    }
}