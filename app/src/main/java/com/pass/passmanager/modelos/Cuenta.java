package com.pass.passmanager.modelos;

import java.io.Serializable;

public class Cuenta implements Serializable {
    private String nombre;
    private String password;
    private String categoria;

    public Cuenta(String nombre, String password, String categoria) {
        this.nombre = nombre;
        this.password = password;
        this.categoria = categoria;
    }

    public Cuenta() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
