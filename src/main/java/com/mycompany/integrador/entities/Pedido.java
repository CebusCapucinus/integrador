package com.mycompany.integrador.entities;

import com.mycompany.integrador.enums.Estado;
import com.mycompany.integrador.enums.FormaPago;
import com.mycompany.integrador.exceptions.ValidateException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base {

    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    public Pedido(Usuario usuario, FormaPago formapago) {

        super();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detalles = new ArrayList<>();
        this.total = 0.0;

    }

    public void addDetallePedido(int cantidad, Producto producto) throws ValidateException {

        if (cantidad <= 0) {
            throw new ValidateException("La cantidad debe ser mayor a 0.");
        }
        if (producto == null) {
            throw new ValidateException("El producto no puede ser nulo.");
        }

        DetallePedido detalle = new DetallePedido(cantidad, producto);
        this.detalles.add(detalle);
        this.total = calcularTotal();

    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().getId().equals(producto.getId())) {
                return detalle;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {

        detalles.removeIf(detalle -> detalle.getProducto().getId().equals(producto.getId()));
        this.total = calcularTotal();

    }

    //@Override
    public double calcularTotal() {
        double suma = 0.0;
        for (DetallePedido detalle : detalles) {
            suma += detalle.getSubtotal();
        }
        return suma;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    @Override
    public String toString() {
        return "ID Pedido: " + id + " | Usuario: " + usuario.getNombre()
                + " | Estado: " + estado + " | Total: " + total
                + " | Fecha: " + fecha;
    }

}
