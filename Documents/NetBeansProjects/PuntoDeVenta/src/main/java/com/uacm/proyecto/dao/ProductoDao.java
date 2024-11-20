package com.uacm.proyecto.dao;

import com.uacm.proyecto.modelo.Producto;

/**
 * Esta interfaz define los metodos de un producto
 * @author Carlos Martinez Hernadez
 * @version 1.0
 */
public interface ProductoDao extends DAO<Producto, Integer>{//Modificaciob a todos los daos
    int ObtenerId(Producto producto);
    Producto BuscarPorCodigo(String codigo);
}
