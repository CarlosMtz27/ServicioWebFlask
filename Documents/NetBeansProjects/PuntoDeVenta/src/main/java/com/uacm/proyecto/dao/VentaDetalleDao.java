package com.uacm.proyecto.dao;

import com.uacm.proyecto.modelo.VentaDetalle;
import java.util.List;

/**
 *
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public interface VentaDetalleDao extends DAO<VentaDetalle, Integer>{
    List<Integer> obtenerProductos(int numeroVenta);
}
