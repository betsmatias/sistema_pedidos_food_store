/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.entities;

import java.time.LocalDateTime;

/**
 *
 * @author matia
 */
public abstract class Base {
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;

    public Base(Long id) {
        this.id = id;
        this.eliminado = false; // Por defecto al crear, no está eliminado
        this.createdAt = LocalDateTime.now(); // Fecha y hora del sistema al momento de instanciar
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}