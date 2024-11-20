package com.uacm.proyecto.dao.daoimpl;

import com.uacm.proyecto.dao.DAOException;
import com.uacm.proyecto.dao.VentaDetalleDao;
import com.uacm.proyecto.modelo.VentaDetalle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase se encarga de hacer las consultas sobre los detalles de las ventas
 * @author Carlos Martinez Hernandez
 */
public class VentaDetalleDaoImpl implements VentaDetalleDao{
    
    final String INSERT = "INSERT INTO ventas_detalles(venta, producto, cantidad ,precio_venta) VALUES(?,?,?,?)";
    final String UPDATE = "UPDATE ventas_detalles SET venta=?, producto=?, cantidad=?, precio_venta=? WHERE id_venta_detalle=?";
    final String DELETE = "DELETE FROM ventas_detalles WHERE id_venta_detalles=?";
    final String OBTENERTODOS = "SELECT id_venta_detalle, venta, producto, cantidad, precio_venta FROM ventas_detalles";
    final String OBTENERUNO = "SELECT id_venta_detalle, venta, producto, cantidad, precio_venta FROM ventas_detalles WHERE id_venta_detalle=?";
    final String OBTENERPRODUCTOS = "SELECT producto FROM ventas_detalles WHERE venta=?";
    
    private Connection conn;
    public VentaDetalleDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void agregar(VentaDetalle dato) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs= null;
        try{
            stat = conn.prepareStatement(INSERT);
            stat.setInt(1, dato.getVentas());
            stat.setInt(2, dato.getProducto());
            stat.setInt(3, dato.getCantidad());
            stat.setDouble(4, dato.getPrecioVenta());
            stat.executeUpdate();
        }catch(SQLException ex){
            throw new DAOException("Eror en sql", ex);
        }finally{
            if(rs!=null){
                try{
                    rs.close();
                }catch (SQLException ex){
                    new DAOException("Error en sql", ex);
                }
            }
            if(stat !=null){
                try {
                    stat.close();
                }catch(SQLException ex){
                    throw new DAOException("Error en sql", ex);
                }
            }
        }
    }

    @Override
    public void eliminar(VentaDetalle dato) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setInt(1, dato.getId());
            System.out.println(dato.getId());
            if(stat.executeUpdate()==0){
                throw new DAOException("Puede que el producto no se haya borrado");
            }
        }catch(SQLException ex){
            throw new DAOException("Error de SQL", ex);
        }finally{
            if(stat!=null){
                try{
                    stat.close();
                }catch(SQLException ex){
                    throw new DAOException("Error de SQL", ex);
                }
            }
        }
    }

    @Override
    public void editar(VentaDetalle dato) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(UPDATE);
            stat.setInt(1, dato.getVentas());
            stat.setInt(2, dato.getProducto());
            stat.setInt(3, dato.getCantidad());
            stat.setDouble(4, dato.getPrecioVenta());
            if(stat.executeUpdate()==0){
                throw new DAOException("Puede que el producto no se haya actualizado");
            }
        }catch(SQLException ex){
            throw new DAOException("Error de SQL", ex);
        }finally{
            if(stat!=null){
                try{
                    stat.close();
                }catch(SQLException ex){
                    throw new DAOException("Error de SQL", ex);
                }
            }
        }
    }
    
    private VentaDetalle convertir(ResultSet rs) throws SQLException, DAOException{
        int id = rs.getInt("id_venta_detalle");//modificacion
        int idVenta = rs.getInt("venta");
        int idProducto = rs.getInt("producto");
        int cantidad = rs.getInt("cantidad");
        Double precioVenta = rs.getDouble("precio_venta");
        VentaDetalle ventaDetalle = new VentaDetalle(id, idVenta, idProducto,cantidad, precioVenta);//modificacion
        
        return ventaDetalle;
    }

    @Override
    public List<VentaDetalle> obtenerTodos() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<VentaDetalle> ventasDetalle = new ArrayList<>();
        try{
            stat = conn.prepareStatement(OBTENERTODOS);
            rs = stat.executeQuery();
            while(rs.next()){
                ventasDetalle.add(convertir(rs));
            }
        }catch(SQLException ex){
            throw new DAOException("Error en SQL", ex);
        }finally{
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
            }
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException ex){
                   new DAOException("Erro en SQL", ex);
                }
            }
        }
        return ventasDetalle;
    }

    @Override
    public VentaDetalle obtener(Integer id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        VentaDetalle ventaDetalle = null;
        try{
            stat = conn.prepareStatement(OBTENERUNO);
            stat.setLong(1, id);
            rs = stat.executeQuery();
            if(rs.next()){
                ventaDetalle = convertir(rs);
            }else{
                throw new DAOException("No se ha encontrado ese registro");
            }
        }catch(SQLException ex){
            throw new DAOException("Error en SQL", ex);
        }finally{
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
            }
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException ex){
                   new DAOException("Erro en SQL", ex);
                }
            }
        }
        return ventaDetalle;
    }
    
    /*public static void main(String [] arg) throws SQLException, DAOException{
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/punto_venta", "root", "password");
        VentaDetalleDao dao = new VentaDetalleDaoImpl(conn);
        List<Integer> p = new ArrayList();
        p = dao.obtenerProductos(44);
        System.out.println(p.toString());
    }*/

    @Override
    public List<Integer> obtenerProductos(int numeroVenta) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Integer> productosVenta = new ArrayList<>();
        try{
            stat = conn.prepareStatement(OBTENERPRODUCTOS);
            stat.setInt(1, numeroVenta);
            rs = stat.executeQuery();
            while(rs.next()){
                productosVenta.add(rs.getInt("producto"));
            }
        }catch(SQLException ex){
            try {
                throw new DAOException("Error en SQL", ex);
            } catch (DAOException ex1) {
                Logger.getLogger(VentaDetalleDaoImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            if(rs != null){
                try{
                    rs.close();
                }catch(SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
            }
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException ex){
                   new DAOException("Erro en SQL", ex);
                }
            }
        }
        return productosVenta;
    } 
}
