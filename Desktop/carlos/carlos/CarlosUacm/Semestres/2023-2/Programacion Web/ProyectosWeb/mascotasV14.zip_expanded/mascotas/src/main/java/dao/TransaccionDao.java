package dao;

import java.sql.Connection;
import java.sql.SQLException;

import modelo.FormularioSocioEconomico;
import modelo.Solicitud;

public class TransaccionDao {
	private Administrador administrador;
	private SolicitudDao solicitudDao;
	private FormularioSocioEconomicoDao fseDAO;

	public TransaccionDao() {
		administrador = new Administrador();
	}

	public void transaccionSolicituFormularioSE(Solicitud solicitud, FormularioSocioEconomico fse) throws Exception {
	    Connection conexion = null;
	    solicitudDao = new SolicitudDao();
	    fseDAO = new FormularioSocioEconomicoDao();
	    int idFse;
	    try {
	        conexion = administrador.dameConexion();
	        conexion.setAutoCommit(false);
	        
	        idFse = fseDAO.insertarYObtenerId(fse);
	        
	        if (idFse != -1) {
	            solicitud.setIdFormularioSocioeconomico(idFse);
	            solicitudDao.insertar(solicitud);
	            conexion.commit();
	        } else {
	            throw new Exception("Error al insertar el Formulario Socioeconómico");
	        }

	    } catch (SQLException sqle) {
	        if (conexion != null) {
	            try {
	                conexion.rollback();
	            } catch (SQLException rollbackException) {
	                rollbackException.printStackTrace();
	            }
	        }
	        sqle.printStackTrace();
	        throw new Exception("Falló el registro Grupo Estudiante");
	    } finally {
	        if (conexion != null) {
	            try {
	                conexion.setAutoCommit(true);
	                conexion.close();
	            } catch (SQLException closeException) {
	                closeException.printStackTrace();
	            }
	        }
	    }
	}

	/*public ArrayList<Calificacion> buscarCalificaciones() throws DAOException {
		Connection conexion = administrador.dameConexion();
		String comandoSQL = "SELECT c.numero_grupo, c.id_curso," +
		                     " c.matricula, e.nombre, e.apellido_paterno,"+
				             " e.apellido_materno, c.promedio  " +
		                     "  FROM estudiantes e, calificaciones c " +
				             "  WHERE e.matricula=c.matricula;";
		ResultSet resultSet = null;
		Estudiante estudiante = null;
		Calificacion calificacion = null;
		ArrayList<Calificacion> calificaciones = new ArrayList<Calificacion>();

		PreparedStatement comando;
		try {
			comando = conexion.prepareStatement(comandoSQL);
			resultSet = comando.executeQuery();
			while (resultSet.next()) {

				estudiante = new Estudiante(resultSet.getString(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getString(6),resultSet.getDouble(7));
				calificacion = new Calificacion(resultSet.getString(1),resultSet.getString(2), estudiante);			
				
				calificaciones.add(calificacion);
			}

		} catch (SQLException e) {			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOException("Falló la búsqueda de calificaciones");			
		}
		return calificaciones;
	}*/

}
