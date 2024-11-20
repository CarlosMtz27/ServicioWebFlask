package com.uacm.proyecto.controller;

import java.util.Objects;

/**
 * Esta clase sirve para guardar los productos y poder mostrarlos en una tabla
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class ProductoTabla {
    private String nombre, descripcion, codigo;
    private Double precio;
    private Integer cantidad, id;

    /**
     * Este es un constructor de la clase
     * @param id
     * @param nombre
     * @param descripcion
     * @param codigo
     * @param precio
     * @param cantidad 
     */
    public ProductoTabla(Integer id,String nombre, String descripcion, String codigo, Double precio, Integer cantidad) {
        this.id=id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    /**
     * Este es un constructor vacio de la clase
     */
    ProductoTabla() {
    }

    /**
     * Este es un constructor de la clase
     * @param nombre
     * @param descripcion
     * @param codigo
     * @param precio
     * @param cantidad 
     */
    ProductoTabla(String nombre, String descripcion, String codigo, Double precio, Integer cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    /**
     * Metodo get para el id
     * @return 
     */
    public Integer getId() {
        return id;
    }

     /**
     * Metodo set para el id
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    

    /**
     * Metodo get para el nombre
     * @return 
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo set para el nombre
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo get para la descripcion
     * @return 
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Metodo set para la descripcion
     * @param descripcion 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Metodo get para el codigo
     * @return 
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Metodo set para el codigo
     * @param codigo 
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Metodo get para el precio
     * @return 
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Metodo set para el precio
     * @param precio 
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Metodo get para la cantidad
     * @return 
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Metodo set para la cantidad
     * @param cantidad 
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.nombre);
        hash = 59 * hash + Objects.hashCode(this.descripcion);
        hash = 59 * hash + Objects.hashCode(this.codigo);
        hash = 59 * hash + Objects.hashCode(this.precio);
        hash = 59 * hash + Objects.hashCode(this.cantidad);
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
        final ProductoTabla other = (ProductoTabla) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.precio, other.precio)) {
            return false;
        }
        return Objects.equals(this.cantidad, other.cantidad);
    }
    
    
}
