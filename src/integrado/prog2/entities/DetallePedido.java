/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.entities;

/**
 *
 * @author matia
 */
public class DetallePedido extends Base {
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido(Long id, int cantidad, Producto producto) {
        super(id);
        this.cantidad = cantidad;
        this.producto = producto;
        // El subtotal se calcula automáticamente al instanciar el detalle
        this.subtotal = cantidad * producto.getPrecio();
    }

    // Getters y Setters
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
        this.subtotal = cantidad * this.producto.getPrecio(); // Actualiza subtotal si cambia cantidad
    }

    public Double getSubtotal() { return subtotal; }
    // No ponemos setSubtotal público para evitar que se modifique a mano y rompa la matemática

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { 
        this.producto = producto; 
        this.subtotal = this.cantidad * producto.getPrecio(); // Actualiza subtotal si cambia producto
    }

    @Override
    public String toString() {
        return cantidad + "x " + producto.getNombre() + " (Subtotal: $" + subtotal + ")";
    }
}