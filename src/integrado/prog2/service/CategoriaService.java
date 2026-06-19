/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

import integrado.prog2.entities.Categoria;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ValidacionException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matia
 */
public class CategoriaService {
  // Acá vive nuestra "base de datos" en memoria
    private List<Categoria> categorias;
    private Long contadorId;

    public CategoriaService() {
        this.categorias = new ArrayList<>();
        this.contadorId = 1L; // Simulamos el ID auto-incremental
    }

    // HU-CAT-01 - Listar categorías [cite: 243]
    public List<Categoria> listarCategorias() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria cat : categorias) {
            // Solo devolvemos las que NO están eliminadas lógicamente [cite: 246]
            if (!cat.isEliminado()) {
                activas.add(cat);
            }
        }
        return activas;
    }

    // HU-CAT-02 - Crear categoría [cite: 250]
    public Categoria crearCategoria(String nombre, String descripcion) throws ValidacionException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("Error: El nombre de la categoría no puede estar vacío.");
        }

        // Validar que el nombre sea único (ignorando mayúsculas/minúsculas) 
        for (Categoria cat : categorias) {
            if (!cat.isEliminado() && cat.getNombre().equalsIgnoreCase(nombre)) {
                throw new ValidacionException("Error: Ya existe una categoría con el nombre '" + nombre + "'.");
            }
        }

        // Creamos, agregamos a la lista e incrementamos el contador para la próxima
        Categoria nueva = new Categoria(contadorId++, nombre, descripcion);
        categorias.add(nueva);
        return nueva;
    }

    // Método auxiliar interno para buscar por ID y reutilizar código
    public Categoria buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Categoria cat : categorias) {
            if (cat.getId().equals(id) && !cat.isEliminado()) {
                return cat;
            }
        }
        throw new EntidadNoEncontradaException("Error: No se encontró ninguna categoría activa con el ID " + id);
    }

    // HU-CAT-03 - Editar categoría [cite: 263]
    public void editarCategoria(Long id, String nuevoNombre, String nuevaDescripcion) throws EntidadNoEncontradaException, ValidacionException {
        // Usamos el método auxiliar. Si no existe, lanza la excepción y corta la ejecución acá [cite: 267]
        Categoria cat = buscarPorId(id);

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            // Validar que el nuevo nombre no lo esté usando OTRA categoría
            for (Categoria c : categorias) {
                if (!c.isEliminado() && !c.getId().equals(id) && c.getNombre().equalsIgnoreCase(nuevoNombre)) {
                    throw new ValidacionException("Error: Ya existe otra categoría con el nombre '" + nuevoNombre + "'.");
                }
            }
            cat.setNombre(nuevoNombre);
        }

        if (nuevaDescripcion != null && !nuevaDescripcion.trim().isEmpty()) {
            cat.setDescripcion(nuevaDescripcion);
        }
    }

    // HU-CAT-04 - Eliminar categoría (baja lógica) [cite: 269]
    public void eliminarCategoria(Long id) throws EntidadNoEncontradaException {
        Categoria cat = buscarPorId(id);
        // Regla de oro de la consigna: no usamos categorias.remove(cat). Hacemos soft delete. 
        cat.setEliminado(true); 
    }
}  

