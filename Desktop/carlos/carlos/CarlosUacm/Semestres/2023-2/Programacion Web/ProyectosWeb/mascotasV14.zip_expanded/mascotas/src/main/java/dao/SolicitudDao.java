package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Solicitud;

public class SolicitudDao {
    private Administrador administrador;
    private final String INSERTAR = "INSERT INTO solicitud(idUsuario, idMascota, idformulariosocioeconomico, fechasolicitud, estadosolicitud, observaciones) VALUES (?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE solicitud SET idUsuario=?, idMascota=?, idformulariosocioeconomico=?, fechasolicitud=?, estadosolicitud=?, observaciones=? WHERE idsolicitud=?";
    private final String OBTENERTODOS = "SELECT * FROM solicitud";
    private final String OBTENERUNO = "SELECT * FROM solicitud WHERE idsolicitud=?";
    private final String ELIMINAR = "DELETE FROM solicitud WHERE idsolicitud=?";
    private final String OBTENER_POR_USUARIO = "SELECT * FROM solicitud WHERE idUsuario = ?";
    
    public SolicitudDao() {
        administrador = new Administrador();
    }
    
    public void insertar(Solicitud solicitud) {
        try (Connection conexion = administrador.dameConexion();
             PreparedStatement comando = conexion.prepareStatement(INSERTAR)) {
            comando.setInt(1, solicitud.getIdUsuario());
            comando.setInt(2, solicitud.getIdMascota());
            comando.setInt(3, solicitud.getIdFormularioSocioeconomico());
            comando.setString(4, solicitud.getFechaSolicitud());
            comando.setString(5, solicitud.getEstadoSolicitud());
            comando.setString(6, solicitud.getObvservaciones());
            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void actualizar(Solicitud solicitud) {
        try (Connection conexion = administrador.dameConexion();
             PreparedStatement comando = conexion.prepareStatement(UPDATE)) {
            comando.setInt(1, solicitud.getIdUsuario());
            comando.setInt(2, solicitud.getIdMascota());
            comando.setInt(3, solicitud.getIdFormularioSocioeconomico());
            comando.setString(4, solicitud.getFechaSolicitud());
            comando.setString(5, solicitud.getEstadoSolicitud());
            comando.setString(6, solicitud.getObvservaciones());
            comando.setInt(7, solicitud.getIdSolicitud());
            comando.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Solicitud> obtenerTodos() {
        List<Solicitud> solicitudes = new ArrayList<>();
        try (Connection conexion = administrador.dameConexion();
             Statement comando = conexion.createStatement();
             ResultSet resultado = comando.executeQuery(OBTENERTODOS)) {
            while (resultado.next()) {
                Solicitud solicitud = obtenerSolicitudDesdeResultSet(resultado);
                solicitudes.add(solicitud);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }
    
    public Solicitud obtenerUno(int id) {
        Solicitud solicitud = null;
        try (Connection conexion = administrador.dameConexion();
             PreparedStatement comando = conexion.prepareStatement(OBTENERUNO)) {
            comando.setInt(1, id);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    solicitud = obtenerSolicitudDesdeResultSet(resultado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitud;
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
    
    private Solicitud obtenerSolicitudDesdeResultSet(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("idsolicitud");
        int idUsuario = resultado.getInt("idUsuario");
        int idMascota = resultado.getInt("idMascota");
        int idFormularioSocioeconomico = resultado.getInt("idformulariosocioeconomico");
        String fechaSolicitud = resultado.getString("fechasolicitud");
        String estadoSolicitud = resultado.getString("estadosolicitud");
        String observaciones = resultado.getString("observaciones");
        
        return new Solicitud(id, idUsuario, idMascota, idFormularioSocioeconomico, fechaSolicitud, estadoSolicitud, observaciones);
    }
    
    public List<Solicitud> obtenerSolicitudesPorUsuario(int idUsuario) {
        List<Solicitud> solicitudes = new ArrayList<>();

        try (Connection conexion = administrador.dameConexion();
             PreparedStatement statement = conexion.prepareStatement(OBTENER_POR_USUARIO)) {

            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Solicitud solicitud = mapSolicitud(resultSet);
                solicitudes.add(solicitud);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return solicitudes;
    }

    private Solicitud mapSolicitud(ResultSet resultSet) throws SQLException {
        Solicitud solicitud = new Solicitud();
        solicitud.setIdSolicitud(resultSet.getInt("idsolicitud"));
        solicitud.setIdUsuario(resultSet.getInt("idUsuario"));
        solicitud.setIdMascota(resultSet.getInt("idMascota"));
        solicitud.setIdFormularioSocioeconomico(resultSet.getInt("idformulariosocioeconomico"));
        solicitud.setFechaSolicitud(resultSet.getString("fechasolicitud"));
        solicitud.setEstadoSolicitud(resultSet.getString("estadosolicitud"));
        solicitud.setObvservaciones(resultSet.getString("observaciones"));
        return solicitud;
    }
    
    /*public static void main(String[] args) {
        // Crear una instancia de SolicitudDao
        SolicitudDao solicitudDao = new SolicitudDao();

        // Crear una nueva solicitud (Ejemplo)
        Solicitud nuevaSolicitud = new Solicitud(1, 2, 3, "2023-12-01", "Pendiente", "Nueva solicitud");

        // Insertar la nueva solicitud en la base de datos
        solicitudDao.insertar(nuevaSolicitud);
        System.out.println("Solicitud insertada: " + nuevaSolicitud);

        // Obtener todas las solicitudes
        System.out.println("\nListado de todas las solicitudes:");
        solicitudDao.obtenerTodos().forEach(System.out::println);

        // Obtener una solicitud por su ID (Ejemplo: ID = 1)
        int idSolicitud = 1;
        Solicitud solicitudPorId = solicitudDao.obtenerUno(idSolicitud);
        if (solicitudPorId != null) {
            System.out.println("\nSolicitud con ID " + idSolicitud + ": " + solicitudPorId);
        } else {
            System.out.println("\nNo se encontró ninguna solicitud con ID " + idSolicitud);
        }

        // Actualizar una solicitud (Ejemplo: Actualizar la solicitud recién insertada)
        if (nuevaSolicitud != null) {
            nuevaSolicitud.setEstadoSolicitud("Aprobada");
            solicitudDao.actualizar(nuevaSolicitud);
            System.out.println("\nSolicitud actualizada: " + nuevaSolicitud);
        }

        // Eliminar una solicitud por su ID (Ejemplo: ID = 1)
        solicitudDao.eliminar(idSolicitud);
        System.out.println("\nSolicitud con ID " + idSolicitud + " eliminada");

        // Verificar que la solicitud ha sido eliminada (mostrar todas las solicitudes nuevamente)
        System.out.println("\nListado de todas las solicitudes después de eliminar:");
        solicitudDao.obtenerTodos().forEach(System.out::println);
    }*/
}
