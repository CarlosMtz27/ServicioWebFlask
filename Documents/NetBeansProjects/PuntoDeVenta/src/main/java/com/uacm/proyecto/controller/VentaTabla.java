package com.uacm.proyecto.controller;

import java.util.Objects;

/**
 * Esta clase se encarga de guardar e inicializar las ventas para mostrarlos en una tabla
 * @author Carlos Martinez Hernandez
 */
public class VentaTabla {
    private int id;
    private int numeroVenta;
    private String nombre;
    private double total;

    public VentaTabla(int id, int numeroVenta, String nombre, double total) {
        this.id = id;
        this.numeroVenta = numeroVenta;
        this.nombre = nombre;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(int numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.id;
        hash = 71 * hash + this.numeroVenta;
        hash = 71 * hash + Objects.hashCode(this.nombre);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.total) ^ (Double.doubleToLongBits(this.total) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VentaTabla other = (VentaTabla) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.numeroVenta != other.numeroVenta) {
            return false;
        }
        if (Double.doubleToLongBits(this.total) != Double.doubleToLongBits(other.total)) {
            return false;
        }
        return Objects.equals(this.nombre, other.nombre);
    }
    
    
    
}
