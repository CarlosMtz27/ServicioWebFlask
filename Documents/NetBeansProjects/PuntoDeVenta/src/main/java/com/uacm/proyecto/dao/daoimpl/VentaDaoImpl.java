package com.uacm.proyecto.dao.daoimpl;

import com.uacm.proyecto.dao.DAOException;
import com.uacm.proyecto.dao.VentaDao;
import com.uacm.proyecto.modelo.Venta;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase se encarga de hacer las consultas sobre las ventas
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class VentaDaoImpl implements VentaDao{
    
    final String INSERT = "INSERT INTO ventas(nombre_vendedor, numero_venta,fecha_venta,monto) VALUES(?,?,?,?)";
    final String UPDATE = "UPDATE ventas SET nombre_vendedor=?, numero_venta=?, fecha_venta=?, monto=? WHERE id_venta=?";
    final String DELETE = "DELETE FROM ventas WHERE id_venta=?";
    final String OBTENERTODOS = "SELECT id_venta, nombre_vendedor, numero_venta, fecha_venta, monto FROM ventas";
    final String OBTENERUNO = "SELECT id_venta, nombre_vendedor, numero_venta, fecha_venta, monto FROM ventas WHERE id_venta=?";
    final String V = "SELECT id_venta FROM ventas";
    private Connection conn;
    public VentaDaoImpl(Connection conn) {
        this.conn = conn;
    }

    public VentaDaoImpl() {
    }

    /**
     * ESte metodo se encarga de agaregar una nueva venta a la base de datos
     * @param dato
     * @throws DAOException 
     */
    @Override
    public void agregar(Venta dato) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs= null;
        try{
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, dato.getNombreVendedor());
            stat.setInt(2, dato.getNumeroVenta());
            stat.setDate(3, (Date) dato.getFechaVenta());
            stat.setDouble(4, dato.getMonto());
            stat.executeUpdate();
        }catch(SQLException ex){
            throw new DAOException("ERROR en sql", ex);
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
    public void eliminar(Venta dato) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setInt(1, dato.getId());//modificacion
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
    public void editar(Venta dato) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, dato.getNombreVendedor());
            stat.setInt(2, dato.getNumeroVenta());
            stat.setDate(3, (Date) dato.getFechaVenta());
            stat.setDouble(4, dato.getMonto());
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
    private Venta convertir(ResultSet rs) throws SQLException{
        int id = rs.getInt("id_venta");//modificacion
        String nombreVendedor = rs.getString("nombre_vendedor");
        int numeroVenta = rs.getInt("numero_venta");
        Date fechaVenta = rs.getDate("fecha_venta");
        Double monto = rs.getDouble("monto");
        Venta venta = new Venta(id, nombreVendedor, numeroVenta,fechaVenta, monto);//modificacion
        
        return venta;
    }

    @Override
    public List<Venta> obtenerTodos() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Venta> ventas = new ArrayList<>();
        try{
            stat = conn.prepareStatement(OBTENERTODOS);
            rs = stat.executeQuery();
            while(rs.next()){
                ventas.add(convertir(rs));
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
        return ventas;
    }

    @Override
    public Venta obtener(Integer id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Venta venta = null;
        try{
            stat = conn.prepareStatement(OBTENERUNO);
            stat.setLong(1, id);
            rs = stat.executeQuery();
            if(rs.next()){
                venta = convertir(rs);
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
                   new DAOException("Error en SQL", ex);
                }
            }
        }
        return venta;
    } 

    @Override
    public List<Integer> numeroVenta() {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Integer> ventas = new ArrayList();
        try{
            stat = conn.prepareStatement(V);
            rs = stat.executeQuery();
            while(rs.next()){
                ventas.add(rs.getInt("id_venta"));
            }
        }catch(SQLException ex){
            try {
                throw new DAOException("Error en SQL", ex);
            } catch (DAOException ex1) {
                Logger.getLogger(VentaDaoImpl.class.getName()).log(Level.SEVERE, null, ex1);
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
        return ventas;
    }
}
