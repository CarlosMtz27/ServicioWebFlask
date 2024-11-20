package dao;


public class EstudianteDAO {
	/*private Administrador administrador;

	public EstudianteDAO() {
		administrador = new Administrador();
	}

	public void insertar(Estudiante estudiante) {
		Connection conexion = administrador.dameConexion();
		String comandoSQL = "INSERT INTO estudiantes VALUES(?,?,?,?,?)";

		PreparedStatement comando;
		try {
			comando = conexion.prepareStatement(comandoSQL);
			comando.setString(1, estudiante.getMatricula());
			comando.setString(2, estudiante.getNombre());
			comando.setString(3, estudiante.getApellidoPaterno());
			comando.setString(4, estudiante.getApellidoMaterno());
			comando.setInt(5, estudiante.getEdad());
			comando.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void borrar(Estudiante estudiante) {
		Connection conexion = administrador.dameConexion();
		String comandoSQL = "DELETE FROM estudiantes WHERE matricula =?";

		PreparedStatement comando;
		try {
			comando = conexion.prepareStatement(comandoSQL);
			comando.setString(1, estudiante.getMatricula());

			comando.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertarTransaccion(Estudiante estudiante, Connection conexion) throws SQLException {

		String comandoSQL = "INSERT INTO estudiantes VALUES(?,?,?,?,?)";

		PreparedStatement comando;

		comando = conexion.prepareStatement(comandoSQL);
		comando.setString(1, estudiante.getMatricula());
		comando.setString(2, estudiante.getNombre());
		comando.setString(3, estudiante.getApellidoPaterno());
		comando.setString(4, estudiante.getApellidoMaterno());
		comando.setInt(5, estudiante.getEdad());
		comando.executeUpdate();

	}

	public ArrayList<Estudiante> buscar() {
		Connection conexion = administrador.dameConexion();
		String comandoSQL = "SELECT * FROM estudiantes;";
		ResultSet resultSet = null;
		Estudiante estudiante = null;
		ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();

		PreparedStatement comando;
		try {
			comando = conexion.prepareStatement(comandoSQL);
			resultSet = comando.executeQuery();
			while (resultSet.next()) {
				estudiante = new Estudiante(resultSet.getString("matricula"), resultSet.getString("nombre"),
						resultSet.getString("apellido_paterno"), resultSet.getString("apellido_materno"),
						resultSet.getInt("edad"));
				estudiantes.add(estudiante);
			}

		} catch (SQLException e) {			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return estudiantes;
	}
*/
}
