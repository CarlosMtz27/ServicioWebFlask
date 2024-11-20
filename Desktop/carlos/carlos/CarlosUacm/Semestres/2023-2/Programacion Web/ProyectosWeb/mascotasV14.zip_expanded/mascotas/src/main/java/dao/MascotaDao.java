package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import modelo.Mascota;
import java.sql.*;
import java.util.ArrayList;

public class MascotaDao {
    private Administrador administrador;
    private final String INSERTAR = "INSERT INTO mascotas(nombre, especie, raza, edad, descripcion, estado, descripcionsalud, imagen_path, imagen) VALUES (?,?,?,?,?,?,?,?,?)";
    private final String ACTUALIZAR = "UPDATE mascotas SET nombre=?, especie=?, raza=?, edad=?, descripcion=?, estado=?, descripcionsalud=?, imagen_path=?, imagen=? WHERE idMascota=?";
    private final String OBTENERTODOS = "SELECT * FROM mascotas";
    private final String OBTENERUNO = "SELECT * FROM mascotas WHERE idMascota=?";
    private final String ELIMINAR = "DELETE FROM mascotas WHERE idMascota=?";
    private final String OBTENER_DISPONIBLES_Y_EN_ESPERA = "SELECT * FROM mascotas WHERE estado='disponible' OR estado='en espera' OR estado='Disponible' OR estado='En espera'";


    public MascotaDao() {
        administrador = new Administrador();
    }

    public void insertar(Mascota mascota) {
        try (Connection conn = administrador.dameConexion();
             PreparedStatement statement = conn.prepareStatement(INSERTAR)) {
            statement.setString(1, mascota.getNombre());
            statement.setString(2, mascota.getEspecie());
            statement.setString(3, mascota.getRaza());
            statement.setInt(4, mascota.getEdad());
            statement.setString(5, mascota.getDescripcion());
            statement.setString(6, mascota.getEstado());
            statement.setString(7, mascota.getDescripcionSalud());
            statement.setString(8, mascota.getFoto());
            statement.setBytes(9, mascota.getImagen());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Mascota> obtenerMascotasDisponiblesYEnEspera() throws Exception {
        List<Mascota> mascotas = new ArrayList<>();

        try (Connection conn = administrador.dameConexion();
             PreparedStatement statement = conn.prepareStatement(OBTENER_DISPONIBLES_Y_EN_ESPERA);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Mascota mascota = new Mascota();
                mascota.setIdMascota(resultSet.getInt("idMascota"));
                mascota.setNombre(resultSet.getString("nombre"));
                mascota.setEspecie(resultSet.getString("especie"));
                mascota.setRaza(resultSet.getString("raza"));
                mascota.setEdad(resultSet.getInt("edad"));
                mascota.setDescripcion(resultSet.getString("descripcion"));
                mascota.setEstado(resultSet.getString("estado"));
                mascota.setDescripcionSalud(resultSet.getString("descripcionsalud"));
                mascota.setFoto(resultSet.getString("imagen_path"));
                mascota.setImagen(resultSet.getBytes("imagen"));

                mascotas.add(mascota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mascotas;
    }

    public void actualizar(Mascota mascota) {
        try (Connection conn = administrador.dameConexion();
             PreparedStatement statement = conn.prepareStatement(ACTUALIZAR)) {
            statement.setString(1, mascota.getNombre());
            statement.setString(2, mascota.getEspecie());
            statement.setString(3, mascota.getRaza());
            statement.setInt(4, mascota.getEdad());
            statement.setString(5, mascota.getDescripcion());
            statement.setString(6, mascota.getEstado());
            statement.setString(7, mascota.getDescripcionSalud());
            statement.setString(8, mascota.getFoto());
            statement.setBytes(9, mascota.getImagen());
            statement.setInt(10, mascota.getIdMascota());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Mascota> obtenerTodos() {
        List<Mascota> mascotas = new ArrayList<>();
        try (Connection conn = administrador.dameConexion();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(OBTENERTODOS)) {
            while (resultSet.next()) {
                Mascota mascota = obtenerMascotaDesdeResultSet(resultSet);
                mascotas.add(mascota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mascotas;
    }

    public Mascota obtenerUno(int id) {
        Mascota mascota = null;
        try (Connection conn = administrador.dameConexion();
             PreparedStatement statement = conn.prepareStatement(OBTENERUNO)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    mascota = obtenerMascotaDesdeResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mascota;
    }

    public void eliminar(int id) {
        try (Connection conn = administrador.dameConexion();
             PreparedStatement statement = conn.prepareStatement(ELIMINAR)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Mascota obtenerMascotaDesdeResultSet(ResultSet resultSet) throws SQLException {
    	int id = resultSet.getInt("idMascota");
        String nombre = resultSet.getString("nombre");
        String especie = resultSet.getString("especie");
        String raza = resultSet.getString("raza");
        int edad = resultSet.getInt("edad");
        String descripcion = resultSet.getString("descripcion");
        String estado = resultSet.getString("estado");
        String descripcionSalud = resultSet.getString("descripcionsalud");
        String imagenPath = resultSet.getString("imagen_path");
        byte[] imagen = resultSet.getBytes("imagen");

        return new Mascota(id, nombre, especie, raza, edad, descripcion, estado, descripcionSalud, imagenPath, imagen);
    
    }
    
}
