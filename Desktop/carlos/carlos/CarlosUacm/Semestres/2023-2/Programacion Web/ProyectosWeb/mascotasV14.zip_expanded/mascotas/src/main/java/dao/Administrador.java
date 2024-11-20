package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Administrador {
	private Connection conexion;
	private String url;
	private String usuarioBD;
	private String contrasenaBD;
	
	public Administrador() {
		url="jdbc:postgresql://127.0.0.1:5432/proyectomascotas";
		usuarioBD="postgres";
		contrasenaBD="password";		
	}

	public Connection dameConexion() {

		try {
			Class.forName("org.postgresql.Driver");
			conexion=DriverManager.getConnection(url, usuarioBD,
	              contrasenaBD);			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return conexion;
	}
	public void cerrarConexion(Connection conexion) {
		try {
			conexion.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());			
		}

	}
	
	
	 /*public static void main(String args[]) { Administrador a= new
	 Administrador(); Connection c= a.dameConexion(); if (c!=null)
	 System.out.println("CONEXION CREADA"); else
	 System.out.println("FALLO CONEXION CREADA");
	  
	 }*/
	 
	

}
