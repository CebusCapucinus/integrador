package com.mycompany.integrador.entities;

import java.time.LocalDateTime;

public abstract class Base {

    protected Long id;
    protected boolean eliminado;
    protected LocalDateTime createdAt;

    public Base() {
        this.createdAt = LocalDateTime.now();
        this.eliminado = false;
    }

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
