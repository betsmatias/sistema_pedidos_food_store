/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.entities.Pedido;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ValidacionException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matia
 */
public class PedidoService {
   private List<Pedido> pedidos;
    private Long contadorId;

    public PedidoService() {
        this.pedidos = new ArrayList<>();
        this.contadorId = 1L;
    }

    // HU-PED-01 - Listar pedidos
    public List<Pedido> listarPedidos() {
        List<Pedido> activos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }

    // HU-PED-02 - Guardar el pedido (la creación ocurre en el Main)
    public void guardarPedido(Pedido pedido) throws ValidacionException {
        // Regla de negocio: No permitir crear Pedido sin usuario
        if (pedido.getUsuario() == null || pedido.getUsuario().isEliminado()) {
            throw new ValidacionException("Error: El pedido debe tener un usuario valido asignado.");
        }
        // Validamos que el carrito no esté vacío
        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            throw new ValidacionException("Error: No se puede registrar un pedido sin productos (detalles vacios).");
        }

        pedido.setId(contadorId++);
        pedidos.add(pedido);
    }

    // Método auxiliar
    public Pedido buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("Error: No se encontro ningun pedido con el ID " + id);
    }

    // HU-PED-03 - Actualizar estado/forma de pago
    public void actualizarPedido(Long id, Estado nEstado, FormaPago nPago) throws EntidadNoEncontradaException {
        Pedido p = buscarPorId(id);
        
        if (nEstado != null) {
            p.setEstado(nEstado);
        }
        if (nPago != null) {
            p.setFormaPago(nPago);
        }
    }

    // HU-PED-04 - Eliminar pedido (baja lógica)
    public void eliminarPedido(Long id) throws EntidadNoEncontradaException {
        Pedido p = buscarPorId(id);
        p.setEliminado(true); // Baja lógica del pedido [cite: 390-394]
    }
} 

