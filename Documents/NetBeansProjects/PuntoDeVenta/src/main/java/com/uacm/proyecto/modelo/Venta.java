package com.uacm.proyecto.modelo;

import java.util.Date;
import java.util.List;

public class Venta {
    private int id;
    private String nombreVendedor;
    private Integer numeroVenta;
    private Date fechaVenta;
    private Double monto;

    public Venta(String nombreVendedor, Integer numeroVenta, Date fechaVenta, Double monto) {
        this.nombreVendedor = nombreVendedor;
        this.numeroVenta = numeroVenta;
        this.fechaVenta = fechaVenta;
        this.monto = monto;
    }

    public Venta(int id, String nombreVendedor, Integer numeroVenta, Date fechaVenta, Double monto) {
        this.id = id;
        this.nombreVendedor = nombreVendedor;
        this.numeroVenta = numeroVenta;
        this.fechaVenta = fechaVenta;
        this.monto = monto;
    }

    public Venta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public Integer getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(Integer numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "Venta{" + "id=" + id + ", nombreVendedor=" + nombreVendedor + ", numeroVenta=" + numeroVenta + ", fechaVenta=" + fechaVenta + ", monto=" + monto + '}';
    }
    
    
}
