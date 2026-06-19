/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.entities;

import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matia
 */
public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles; // Relación 1 a muchos con DetallePedido

    public Pedido(Long id, Usuario usuario, FormaPago formaPago) {
        super(id);
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE; // Por defecto arranca pendiente
        this.total = 0.0;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.detalles = new ArrayList<>(); // Inicializamos la lista vacía
    }

    // Método exigido por el UML para agregar detalles
    public void addDetallePedido(int cantidad, Double precio, Producto producto) {
        // En un sistema real, el precio suele sacarse directo del producto, 
        // pero respetamos la firma del método que pide el UML
        Long idDetalle = (long) (this.detalles.size() + 1);
        DetallePedido nuevoDetalle = new DetallePedido(idDetalle, cantidad, producto);
        this.detalles.add(nuevoDetalle);
        this.calcularTotal(); // Recalculamos el total cada vez que agregamos algo
    }

    // Método exigido por la interfaz Calculable
    @Override
    public void calcularTotal() {
        double suma = 0.0;
        for (DetallePedido detalle : detalles) {
            suma += detalle.getSubtotal();
        }
        this.total = suma;
    }

    // Getters y Setters
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Double getTotal() { return total; }

    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<DetallePedido> getDetalles() { return detalles; }

    @Override
    public String toString() {
        return "Pedido #" + getId() + " | Fecha: " + fecha + " | Estado: " + estado + 
               " | Cliente: " + usuario.getNombre() + " | Total: $" + total;
    }
}