package com.uacm.proyecto.dao;

import com.uacm.proyecto.modelo.Venta;
import java.util.List;

/**
 *
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public interface VentaDao extends DAO<Venta, Integer>{
    List<Integer> numeroVenta();
}
