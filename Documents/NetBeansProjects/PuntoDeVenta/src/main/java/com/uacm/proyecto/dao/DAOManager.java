package com.uacm.proyecto.dao;

/**
 * Esta clase se encarga administrar los daos
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public interface DAOManager {
    ProductoDao getProductoDAO();
    GerenteDao getGerenteDAO();
    VentaDetalleDao getVentaDetalleDAO();
    VentaDao getVentaDAO();
}
