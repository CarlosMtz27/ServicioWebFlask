package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.FormularioSocioEconomico;

public class FormularioSocioEconomicoDao {
	 private Administrador administrador;
	 private final String INSERTAR = "INSERT INTO formularios_socioeconomicos(idusuario, ingresomensual, tipovivienda, horasdisponibles, experiencia, otrosanimalesenhogar, informacionadicional) VALUES (?,?,?,?,?,?,?)";
	 private final String ACTUALIZAR = "UPDATE formularios_socioeconomicos SET ingresomensual = ?, tipovivienda = ?, horasdisponibles = ?, experiencia = ?, otrosanimalesenhogar = ?, informacionadicional = ? WHERE idformulario = ?";
	 private final String OBTENERTODOS = "SELECT * FROM formularios_socioeconomicos";
	 private final String OBTENERUNO = "SELECT * FROM formularios_socioeconomicos WHERE idformulario = ?";
	 //private final String ELIMINAR = "DELETE FROM formularios_socioeconomicos WHERE idformulario = ?";


	    public FormularioSocioEconomicoDao() {
	        administrador = new Administrador();
	    }

	    public void insertar(FormularioSocioEconomico fse) {
	        try (Connection conexion = administrador.dameConexion();
	             PreparedStatement pstmt = conexion.prepareStatement(INSERTAR)) {
	            pstmt.setInt(1, fse.getIdUsuario());
	            pstmt.setDouble(2, fse.getIngresoMensual());
	            pstmt.setString(3, fse.getTipoVivienda());
	            pstmt.setInt(4, fse.getHorasDisponibles());
	            pstmt.setString(5, fse.getExperiencia());
	            pstmt.setInt(6, fse.getOtrosAnimalesEnHogar());
	            pstmt.setString(7, fse.getInformacionAdicional());

	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public int insertarYObtenerId(FormularioSocioEconomico fse) {
	        int idGenerado = -1; // Valor por defecto si no se genera el ID

	        try (Connection conexion = administrador.dameConexion();
	             PreparedStatement pstmt = conexion.prepareStatement(INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
	            pstmt.setInt(1, fse.getIdUsuario());
	            pstmt.setDouble(2, fse.getIngresoMensual());
	            pstmt.setString(3, fse.getTipoVivienda());
	            pstmt.setInt(4, fse.getHorasDisponibles());
	            pstmt.setString(5, fse.getExperiencia());
	            pstmt.setInt(6, fse.getOtrosAnimalesEnHogar());
	            pstmt.setString(7, fse.getInformacionAdicional());

	            int filasAfectadas = pstmt.executeUpdate();

	            if (filasAfectadas == 1) {
	                ResultSet generatedKeys = pstmt.getGeneratedKeys();
	                if (generatedKeys.next()) {
	                    idGenerado = generatedKeys.getInt(1); // Obtener el ID generado
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return idGenerado;
	    }

	    public void actualizar(FormularioSocioEconomico fse) {
	        try (Connection conexion = administrador.dameConexion();
	             PreparedStatement pstmt = conexion.prepareStatement(ACTUALIZAR)) {
	            pstmt.setDouble(1, fse.getIngresoMensual());
	            pstmt.setString(2, fse.getTipoVivienda());
	            pstmt.setInt(3, fse.getHorasDisponibles());
	            pstmt.setString(4, fse.getExperiencia());
	            pstmt.setInt(5, fse.getOtrosAnimalesEnHogar());
	            pstmt.setString(6, fse.getInformacionAdicional());
	            pstmt.setInt(7, fse.getIdFormulario());

	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public List<FormularioSocioEconomico> obtenerTodos() throws Exception {
	        List<FormularioSocioEconomico> formularios = new ArrayList<>();
	        try (Connection conexion = administrador.dameConexion();
	             Statement stmt = conexion.createStatement();
	             ResultSet rs = stmt.executeQuery(OBTENERTODOS)) {

	            while (rs.next()) {
	                FormularioSocioEconomico fse = new FormularioSocioEconomico();
	                fse.setIdFormulario(rs.getInt("idformulario"));
	                fse.setIdUsuario(rs.getInt("idusuario"));
	                fse.setIngresoMensual(rs.getDouble("ingresomensual"));
	                fse.setTipoVivienda(rs.getString("tipovivienda"));
	                fse.setHorasDisponibles(rs.getInt("horasdisponibles"));
	                fse.setExperiencia(rs.getString("experiencia"));
	                fse.setOtrosAnimalesEnHogar(rs.getInt("otrosanimalesenhogar"));
	                fse.setInformacionAdicional(rs.getString("informacionadicional"));
	                
	                formularios.add(fse);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return formularios;
	    }
	    public FormularioSocioEconomico obtenerUno(int id) throws Exception {
	        FormularioSocioEconomico fse = null;
	        try (Connection conexion = administrador.dameConexion();
	             PreparedStatement pstmt = conexion.prepareStatement(OBTENERUNO)) {
	            
	            pstmt.setInt(1, id);
	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                fse = new FormularioSocioEconomico();
	                fse.setIdFormulario(rs.getInt("idformulario"));
	                fse.setIdUsuario(rs.getInt("idusuario"));
	                fse.setIngresoMensual(rs.getDouble("ingresomensual"));
	                fse.setTipoVivienda(rs.getString("tipovivienda"));
	                fse.setHorasDisponibles(rs.getInt("horasdisponibles"));
	                fse.setExperiencia(rs.getString("experiencia"));
	                fse.setOtrosAnimalesEnHogar(rs.getInt("otrosanimalesenhogar"));
	                fse.setInformacionAdicional(rs.getString("informacionadicional"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return fse;
	    }
	    
	   // public static void main(String[] args) {
	     //   FormularioSocioEconomicoDao formularioDao = new FormularioSocioEconomicoDao();
/*
	        // Insertar un formulario
	        FormularioSocioEconomico nuevoFormulario = new FormularioSocioEconomico();
	        nuevoFormulario.setIdUsuario(2);
	        nuevoFormulario.setIngresoMensual(2000.0);
	        nuevoFormulario.setTipoVivienda("Casa");
	        nuevoFormulario.setHorasDisponibles(30);
	        nuevoFormulario.setExperiencia("Sin Experiencia");
	        nuevoFormulario.setOtrosAnimalesEnHogar(2);
	        nuevoFormulario.setInformacionAdicional("Información adicional");

	        formularioDao.insertar(nuevoFormulario);*/

	        // Actualizar un formulario
	        /*FormularioSocioEconomico formularioExistente = formularioDao.obtenerUno(3);
	        if (formularioExistente != null) {
	            formularioExistente.setIngresoMensual(2500.0);
	            formularioExistente.setTipoVivienda("xd");

	            formularioDao.actualizar(formularioExistente);
	        }

	        System.out.print(formularioExistente);*/
	        // Obtener todos los formularios
	       // List<FormularioSocioEconomico> formularios = formularioDao.obtenerTodos();
	        //System.out.println(formularios);
	    //}

	   
}
