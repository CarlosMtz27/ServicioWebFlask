package com.uacm.proyecto.dao.daoimpl;

import com.uacm.proyecto.dao.DAOException;
import com.uacm.proyecto.dao.GerenteDao;
import com.uacm.proyecto.modelo.Gerente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Esta clase se encarga de hacer consultas a la base de datos sobre el gerente
 * @author Carlos Martinez Hernandez
 * @version 1.0
 */
public class GerenteDaoImpl implements GerenteDao{
    
    final String INSERT = "INSERT INTO gerentes(nombre, apellidos, edad, correo, contrasenia, clave_acceso) VALUES(?,?,?,?,?,?)";
    final String UPDATE = "UPDATE gerentes SET nombre=?, apellidos=?,edad=?, correo=?, contrasenia=?, clave_acceso=? WHERE id_gerente=?";
    final String DELETE = "DELETE FROM gerentes WHERE id_gerente=?";
    final String OBTENERTODOS = "SELECT id_gerente, nombre, apellidos, edad, correo, contrasenia, clave_acceso FROM gerentes";
    final String OBTENERUNO = "SELECT id_gerente, nombre, apellidos, edad, correo, contrasenia FROM gerentes WHERE id_gerente";
    final String VALIDAR = "SELECT id_gerente, nombre, apellidos, edad, correo, contrasenia, clave_acceso FROM gerentes WHERE correo=? AND contrasenia=?";
    final String VALIDARVENDEDOR = "SELECT id_gerente FROM gerentes WHERE clave_acceso=?";
    private Connection conn;
    
    public GerenteDaoImpl(Connection conn) {
        this.conn = conn;
    }

    /**
     * Este metodo se encarga de agregar gerentes a la base de datos
     * @param dato
     * @throws DAOException 
     */
    @Override
    public void agregar(Gerente dato) throws DAOException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        
        try{
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, dato.getNombre());
            stat.setString(2, dato.getApellidos());
            stat.setInt(3, dato.getEdad());
            stat.setString(4, dato.getCorreo());
            stat.setString(5, dato.getContraseña());
            stat.setString(6, dato.getClaveAcceso());
            stat.executeUpdate();
            System.out.println("El gerente se ha añadido exitosamente");
        }catch(SQLException ex){
            throw new DAOException("Error en sql", ex);
        }finally{
            if(rs!=null){
                try{
                    rs.close();
                }catch(SQLException ex){
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
     * Este metodo se encarga de eliminar gerentes de la base de datos
     * @param dato
     * @throws DAOException 
     */
    @Override
    public void eliminar(Gerente dato) throws DAOException {
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

    /**
     * Este metodo se encarga de editar un gerente
     * @param dato
     * @throws DAOException 
     */
    @Override
    public void editar(Gerente dato) throws DAOException{
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, dato.getNombre());
            stat.setString(2, dato.getApellidos());
            stat.setInt(3, dato.getEdad());
            stat.setString(4, dato.getCorreo());
            stat.setString(5,dato.getContraseña());
            stat.setString(6, dato.getClaveAcceso());
            //stat.executeUpdate();
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
    private Gerente convertir(ResultSet rs) throws SQLException{
        int id = rs.getInt("id_gerente");//modificacion
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellidos");
        Integer edad = rs.getInt("edad");
        String correo = rs.getString("correo");
        String contraseña = rs.getString("contrasenia");
        String claveAcceso = rs.getString("clave_acceso");
        Gerente gerente = new Gerente(id, nombre, apellido, edad, correo, contraseña, claveAcceso);//modificacion
        
        return gerente;
    }

    /**
     * Este metodo obtiene todos loss registros de la tabla gerentes
     * @return
     * @throws DAOException 
     */
    @Override
    public List<Gerente> obtenerTodos() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Gerente> gerentes = new ArrayList<>();
        try{
            stat = conn.prepareStatement(OBTENERTODOS);
            rs = stat.executeQuery();
            while(rs.next()){
                gerentes.add(convertir(rs));
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
        return gerentes;

    }

    /**
     * Este metodo obtiene un gerente de la base de datos
     * @param id
     * @return
     * @throws DAOException 
     */
    @Override
    public Gerente obtener(Integer id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Gerente gerente = null;
        try{
            stat = conn.prepareStatement(OBTENERUNO);
            stat.setLong(1, id);
            rs = stat.executeQuery();
            if(rs.next()){
                gerente = convertir(rs);
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
        return gerente;
    }

    /**
     * Este metodo se encarga de validar correco y contraseña un gerente en la base de datos
     * @param correo
     * @param pass
     * @return 
     */
    @Override
    public Gerente validarUsuario(String correo, String pass) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Gerente gerente = null;
        String nombre=null;
        int resultado=0;
        try{
            stat = conn.prepareStatement(VALIDAR);
            stat.setString(1, correo);
            stat.setString(2, pass);
            rs = stat.executeQuery();
            if(rs.next()){
                gerente = convertir(rs);
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
        return gerente;
    }

    /**
     * Este metodo se encarga de validar la clave de acceso de un gerente en la base de datos
     * @param claveAcceso
     * @return 
     */
    @Override
    public Boolean validaInicioVendedor(String claveAcceso) {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Boolean resultado = false;
        try{
            stat = conn.prepareStatement(VALIDARVENDEDOR);
            stat.setString(1, claveAcceso);
            rs = stat.executeQuery();
            if(rs.next()){
                resultado = true;
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
        return resultado;    
    }
}
