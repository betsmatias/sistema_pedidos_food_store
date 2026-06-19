/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ValidacionException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matia
 */
public class ProductoService {
  private List<Producto> productos;
    private Long contadorId;

    public ProductoService() {
        this.productos = new ArrayList<>();
        this.contadorId = 1L;
    }

    // HU-PROD-01 - Listar productos
    public List<Producto> listarProductos() {
        List<Producto> activos = new ArrayList<>();
        for (Producto prod : productos) {
            // Se muestran solo productos no eliminados
            if (!prod.isEliminado()) {
                activos.add(prod);
            }
        }
        return activos;
    }

    // HU-PROD-02 - Crear producto
    public Producto crearProducto(String nombre, String descripcion, Double precio, 
                                  int stock, String imagen, Boolean disponible, 
                                  Categoria categoria) throws ValidacionException {
        
        // Validaciones exigidas por el dominio
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("Error: El nombre del producto no puede estar vacío.");
        }
        if (precio < 0) {
            throw new ValidacionException("Error: El precio no puede ser menor a 0.");
        }
        if (stock < 0) {
            throw new ValidacionException("Error: El stock no puede ser menor a 0.");
        }
        if (categoria == null || categoria.isEliminado()) {
            throw new ValidacionException("Error: La categoría asignada no es válida o está eliminada.");
        }

        Producto nuevo = new Producto(contadorId++, nombre, precio, descripcion, stock, imagen, disponible, categoria);
        productos.add(nuevo);
        return nuevo;
    }

    // Método auxiliar para buscar por ID
    public Producto buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Producto prod : productos) {
            if (prod.getId().equals(id) && !prod.isEliminado()) {
                return prod;
            }
        }
        throw new EntidadNoEncontradaException("Error: No se encontró ningún producto activo con el ID " + id);
    }

    // HU-PROD-03 - Editar producto
    // Usamos las clases Wrapper (Double e Integer) para poder recibir 'null' si el usuario no quiere modificar esos campos numéricos
    public void editarProducto(Long id, String nuevoNombre, String nuevaDesc, 
                               Double nuevoPrecio, Integer nuevoStock, 
                               Categoria nuevaCategoria) throws EntidadNoEncontradaException, ValidacionException {
        
        Producto prod = buscarPorId(id);

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            prod.setNombre(nuevoNombre);
        }
        if (nuevaDesc != null && !nuevaDesc.trim().isEmpty()) {
            prod.setDescripcion(nuevaDesc);
        }
        if (nuevoPrecio != null) {
            if (nuevoPrecio < 0) throw new ValidacionException("Error: El precio no puede ser negativo.");
            prod.setPrecio(nuevoPrecio);
        }
        if (nuevoStock != null) {
            if (nuevoStock < 0) throw new ValidacionException("Error: El stock no puede ser negativo.");
            prod.setStock(nuevoStock);
        }
        if (nuevaCategoria != null) {
            if (nuevaCategoria.isEliminado()) throw new ValidacionException("Error: La categoría no es válida.");
            prod.setCategoria(nuevaCategoria);
        }
    }

    // HU-PROD-04 - Eliminar producto (baja lógica)
    public void eliminarProducto(Long id) throws EntidadNoEncontradaException {
        Producto prod = buscarPorId(id);
        prod.setEliminado(true); // Baja lógica obligatoria
    }
}  

