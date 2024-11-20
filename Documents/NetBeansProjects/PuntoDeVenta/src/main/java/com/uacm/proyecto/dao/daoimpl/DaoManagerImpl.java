package com.uacm.proyecto.dao.daoimpl;

import com.uacm.proyecto.dao.DAOManager;
import com.uacm.proyecto.dao.GerenteDao;
import com.uacm.proyecto.dao.ProductoDao;
import com.uacm.proyecto.dao.VentaDao;
import com.uacm.proyecto.dao.VentaDetalleDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *ESta clase se encarga de crear la conexion para la base de datos e implementa de DAOManager
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class DaoManagerImpl implements DAOManager{
    private Connection conn;
    private ProductoDao productos = null;
    private GerenteDao gerentes = null;
    private VentaDetalleDao ventaDetalles= null;
    private VentaDao ventas = null;
    
    /**
     * Constructor de la clase
     * @throws SQLException 
     */
    public DaoManagerImpl() throws SQLException{
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/punto_venta", "root", "password");
    }

    @Override
    public ProductoDao getProductoDAO() {
        if(productos == null){
            productos = new ProductoDaoImpl(conn);
        }
        return productos;
    }

    @Override
    public GerenteDao getGerenteDAO() {
        if(gerentes == null){
            gerentes = new GerenteDaoImpl(conn);
        }
        return gerentes;
    }

    @Override
    public VentaDetalleDao getVentaDetalleDAO() {
        if(ventaDetalles == null){
            ventaDetalles = new VentaDetalleDaoImpl(conn);
        }
        return ventaDetalles;
    }

    @Override
    public VentaDao getVentaDAO() {
        if(ventas == null){
            ventas = new VentaDaoImpl(conn);
        }
        return ventas;
    }
}
