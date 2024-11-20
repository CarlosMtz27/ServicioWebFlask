package com.uacm.proyecto.modelo;

import java.util.Objects;

public class Producto {
    private int id;
    private String Nombre;
    private String descripcion;
    private Double precio;
    private Integer cantidad;
    private String codigo;

    public Producto(String Nombre, String descripcion, Double precio, Integer cantidad, String codigo) {
        this.Nombre = Nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.codigo = codigo;
    }

    public Producto(int id, String nombre, String descripcion, Double precio, Integer cantidad, String codigo) {
        this.id = id;
        this.Nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.codigo = codigo;
    }

    public Producto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.Nombre);
        hash = 73 * hash + Objects.hashCode(this.descripcion);
        hash = 73 * hash + Objects.hashCode(this.precio);
        hash = 73 * hash + Objects.hashCode(this.cantidad);
        hash = 73 * hash + Objects.hashCode(this.codigo);
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
        final Producto other = (Producto) obj;
        if (!Objects.equals(this.Nombre, other.Nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.precio, other.precio)) {
            return false;
        }
        return Objects.equals(this.cantidad, other.cantidad);
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", Nombre=" + Nombre + ", descripcion=" + descripcion + ", precio=" + precio + ", cantidad=" + cantidad + ", codigo=" + codigo + '}'+"\n";
    }

   
    
    
    
}
