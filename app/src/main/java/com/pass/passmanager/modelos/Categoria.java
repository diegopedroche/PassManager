package com.pass.passmanager.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Categoria implements Serializable {
    private String nombre;
    private ArrayList<Cuenta> cuentas;

    public Categoria(String nombre, ArrayList<Cuenta> cuentas) {
        this.nombre = nombre;
        this.cuentas = cuentas;
    }

    public Categoria() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(ArrayList<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "nombre='" + nombre + '\'' +
                ", cuentas=" + cuentas +
                '}';
    }
}
