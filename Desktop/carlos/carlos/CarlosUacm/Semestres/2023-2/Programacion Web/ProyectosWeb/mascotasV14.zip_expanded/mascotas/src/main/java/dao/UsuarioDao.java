package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;

public class UsuarioDao {
    private Administrador administrador;
    private final String INSERTAR = "INSERT INTO usuarios (nombre, apellidoPaterno, apellidoMaterno, correo, password, direccion, telefono, rol) VALUES (?,?,?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE usuarios SET nombre=?, apellidoPaterno=?, apellidoMaterno=?, correo=?, password=?, direccion=?, telefono=?, rol=? WHERE id=?";
    private final String OBTENERTODOS = "SELECT * FROM usuarios";
    private final String OBTENERUNO = "SELECT * FROM usuarios WHERE id=?";
    private final String ELIMINAR = "DELETE FROM usuarios WHERE id=?";
    
    public UsuarioDao() {
        administrador = new Administrador();
    }
    
    public void insertar(Usuario usuario) {
        try (Connection conexion = administrador.dameConexion();
             PreparedStatement comando = conexion.prepareStatement(INSERTAR)) {
            comando.setString(1, usuario.getNombre());
            comando.setString(2, usuario.getApellidoPaterno());
            comando.setString(3, usuario.getApellidoMaterno());
            comando.setString(4, usuario.getCorreo());
            comando.setString(5, usuario.getPassword());
            comando.setString(6, usuario.getDireccion());
            comando.setString(7, usuario.getTelefono());
            comando.setString(8, usuario.getRol());
            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void actualizar(Usuario usuario) {
        try (Connection conexion = administrador.dameConexion();
             PreparedStatement comando = conexion.prepareStatement(UPDATE)) {
            comando.setString(1, usuario.getNombre());
            comando.setString(2, usuario.getApellidoPaterno());
            comando.setString(3, usuario.getApellidoMaterno());
            comando.setString(4, usuario.getCorreo());
            comando.setString(5, usuario.getPassword());
            comando.setString(6, usuario.getDireccion());
            comando.setString(7, usuario.getTelefono());
            comando.setString(8, usuario.getRol());
            comando.setInt(9, usuario.getId());
            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Usuario> obtenerTodos() throws Exception {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conexion = administrador.dameConexion();
             Statement comando = conexion.createStatement();
             ResultSet resultado = comando.executeQuery(OBTENERTODOS)) {
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultado.getInt("id"));
                usuario.setNombre(resultado.getString("nombre"));
                usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                usuario.setCorreo(resultado.getString("correo"));
                usuario.setPassword(resultado.getString("password"));
                usuario.setDireccion(resultado.getString("direccion"));
                usuario.setTelefono(resultado.getString("telefono"));
                usuario.setRol(resultado.getString("rol"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    
    public Usuario obtenerUno(int id) throws Exception {
        Usuario usuario = new Usuario();
        try (Connection conexion = administrador.dameConexion();
             PreparedStatement comando = conexion.prepareStatement(OBTENERUNO)) {
            comando.setInt(1, id);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    usuario.setId(resultado.getInt("id"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setPassword(resultado.getString("password"));
                    usuario.setDireccion(resultado.getString("direccion"));
                    usuario.setTelefono(resultado.getString("telefono"));
                    usuario.setRol(resultado.getString("rol"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
    
    public void eliminar(int id) {
        try (Connection conexion = administrador.dameConexion();
             PreparedStatement comando = conexion.prepareStatement(ELIMINAR)) {
            comando.setInt(1, id);
            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Usuario validarLogin(String correo, String password) throws Exception {
        Usuario usuario = null;
        String CONSULTAR_USUARIO = "SELECT * FROM usuarios WHERE correo = ? AND password = ?";
        
        try (Connection conexion = administrador.dameConexion();
            PreparedStatement comando = conexion.prepareStatement(CONSULTAR_USUARIO)) {
            comando.setString(1, correo);
            comando.setString(2, password);
            
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    usuario = new Usuario();
                    usuario.setId(resultado.getInt("id"));
                    usuario.setNombre(resultado.getString("nombre"));
                    usuario.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    usuario.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    usuario.setCorreo(resultado.getString("correo"));
                    usuario.setPassword(resultado.getString("password"));
                    usuario.setDireccion(resultado.getString("direccion"));
                    usuario.setTelefono(resultado.getString("telefono"));
                    usuario.setRol(resultado.getString("rol"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuario;
    }


    
}