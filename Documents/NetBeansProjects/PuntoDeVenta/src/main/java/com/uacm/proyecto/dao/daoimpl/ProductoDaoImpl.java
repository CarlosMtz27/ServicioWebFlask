package com.uacm.proyecto.dao.daoimpl;

import com.uacm.proyecto.dao.DAOException;
import com.uacm.proyecto.dao.ProductoDao;
import com.uacm.proyecto.modelo.Producto;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase se encarga de hacer consultas a la base de datos sobre productos
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class ProductoDaoImpl implements ProductoDao{

    final String INSERT = "INSERT INTO productos(nombre, descripcion, precio, cantidad, codigo) VALUES(?,?,?,?,?)";
    final String UPDATE = "UPDATE productos SET nombre=?, descripcion=?,precio=?, cantidad=?, codigo=? WHERE id_producto=?";
    final String DELETE = "DELETE FROM productos WHERE id_producto=?";
    final String OBTENERTODOS = "SELECT id_producto, nombre, descripcion, precio, cantidad, codigo FROM productos";
    final String OBTENERUNO = "SELECT id_producto, nombre, descripcion, precio, cantidad, codigo FROM productos WHERE id_producto=?";
    final String OBTENERPORCODIGO = "SELECT id_producto, nombre, descripcion, precio, cantidad, codigo FROM productos WHERE codigo=?";
    final String OBTENERID = "SELECT id_producto FROM productos WHERE nombre=? AND descripcion=? AND precio=? AND cantidad=? AND codigo=?";
    private Connection conn;

    public ProductoDaoImpl(Connection conn) {
        this.conn = conn;
    }

    public ProductoDaoImpl() {
    }
    
    /**
     * Este metodo se encarga de agregar productos a la base de datos
     * @param dato
     * @throws DAOException 
     */
    @Override
    public void agregar(Producto dato) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs= null;
        try{
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, dato.getNombre());
            stat.setString(2, dato.getDescripcion());
            stat.setDouble(3, dato.getPrecio());
            stat.setInt(4, dato.getCantidad());
            stat.setString(5, dato.getCodigo());
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

    /**
     * Este metodo se encarga de eliminar productos de la base de datos
     * @param dato
     * @throws DAOException 
     */
    @Override
    public void eliminar(Producto dato) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setInt(1, dato.getId());//modificacion
            System.out.println(dato.getId());
            if(stat.executeUpdate()== 0){
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

    /**
     * Este metodo se encarga de editar un producto
     * @param dato
     * @throws DAOException 
     */
    @Override
    public void editar(Producto dato) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, dato.getNombre());
            stat.setString(2, dato.getDescripcion());
            stat.setDouble(3, dato.getPrecio());
            stat.setInt(4, dato.getCantidad());
            stat.setString(5, dato.getCodigo());
            stat.setInt(6, dato.getId());
            stat.execute();
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

    /**
     * Este metodo se encarga de convertir los resultados del ResulSet
     * @param rs
     * @return
     * @throws SQLException 
     */
    private Producto convertir(ResultSet rs) throws SQLException{
        int id = rs.getInt("id_producto");//modificacion
        String nombre = rs.getString("nombre");
        String descripcion = rs.getString("descripcion");
        Double precio = rs.getDouble("precio");
        Integer cantidad = rs.getInt("cantidad");
        String codigo = rs.getString("codigo");
        Producto producto = new Producto(id, nombre, descripcion, precio, cantidad, codigo);//modificacion
        return producto;
    }
    
    /**
     * Este metodo obtiene todos loss registros de la tabla productos
     * @return
     * @throws DAOException 
     */
    @Override
    public List<Producto> obtenerTodos() throws DAOException {

        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Producto> productos = new ArrayList<>();
        try{
            stat = conn.prepareStatement(OBTENERTODOS);
            rs = stat.executeQuery();
            while(rs.next()){
                productos.add(convertir(rs));
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
        return productos;
    }

    /**
     * Este metodo obtiene un producto de la base de datos
     * @param id
     * @return
     * @throws DAOException 
     */
    @Override
    public Producto obtener(Integer id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Producto producto = null;
        try{
            stat = conn.prepareStatement(OBTENERUNO);
            stat.setInt(1, id);
            rs = stat.executeQuery();
            if(rs.next()){
                producto = convertir(rs);
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
                  throw  new DAOException("Error en SQL", ex);
                }
            }
            if(stat != null){
                try{
                    stat.close();
                }catch(SQLException ex){
                  throw new DAOException("Erro en SQL", ex);
                }
            }
        }
        return producto;

    }

    /**
     * Este metodo obtiene el id de un producto de la base de datos
     * @param producto
     * @return 
     */
    @Override
    public int ObtenerId(Producto producto){
        PreparedStatement stat = null;
        ResultSet rs = null;
        int idProducto = 0;
        try{
            stat = conn.prepareStatement(OBTENERID);
            stat.setString(1, producto.getNombre());
            stat.setString(2, producto.getDescripcion());
            stat.setDouble(3, producto.getPrecio());
            stat.setInt(4, producto.getCantidad());
            stat.setString(5, producto.getCodigo());
            rs = stat.executeQuery();
            if(rs.next()){
                idProducto = rs.getInt("id_producto");
            }
            idProducto = rs.getInt("id_producto");
        }catch(SQLException ex){
            try {
                throw new DAOException("Error en SQL", ex);
            } catch (DAOException ex1) {
                Logger.getLogger(ProductoDaoImpl.class.getName()).log(Level.SEVERE, null, ex1);
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
        return idProducto;
    }

    /**
     * Este metodo busca un producto por su codigo en la base de datos
     * @param codigo
     * @return 
     */
    @Override
    public Producto BuscarPorCodigo(String codigo) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Producto producto = null;
        try{
            stat = conn.prepareStatement(OBTENERPORCODIGO);
            stat.setString(1, codigo);
            rs = stat.executeQuery();
            if(rs.next()){
                producto = convertir(rs);
            }else{
                throw new DAOException("No se ha encontrado ese registro");
            }
        }catch(SQLException ex){
            try {
                throw new DAOException("Error en SQL", ex);
            } catch (DAOException ex1) {
                Logger.getLogger(GerenteDaoImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (DAOException ex) {
            Logger.getLogger(GerenteDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
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
        return producto;
    }
}
