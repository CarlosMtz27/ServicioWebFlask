package com.uacm.proyecto.modelo;

import java.util.List;

public class VentaDetalle {
    private int id;
    private int numVenta;
    private int producto;
    private Integer cantidad;
    private Double precioVenta;

    public VentaDetalle(int ventas, int producto, Integer cantidad, Double precioVenta) {
        this.numVenta = ventas;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
    }

    public VentaDetalle(int id, int ventas, int producto, Integer cantidad, Double precioVenta) {
        this.id = id;
        this.numVenta = ventas;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVentas() {
        return numVenta;
    }

    public void setVentas(int ventas) {
        this.numVenta = ventas;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    @Override
    public String toString() {
        return "VentaDetalle{" + "id=" + id + ", ventas=" + numVenta + ", producto=" + producto + ", cantidad=" + cantidad + ", precioVenta=" + precioVenta + '}';
    }

    
    
}
