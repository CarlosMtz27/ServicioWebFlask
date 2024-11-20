package modelo;

public class Adopcion {
	private int idAdopcion;
	private int idMascota;
	private String fecha;
	private int idUsuario;
	
	
	public int getIdAdopcion() {
		return idAdopcion;
	}
	public void setIdAdopcion(int idAdopcion) {
		this.idAdopcion = idAdopcion;
	}
	public int getIdMascota() {
		return idMascota;
	}
	public void setIdMascota(int idMascota) {
		this.idMascota = idMascota;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	@Override
	public String toString() {
		return "Adopcion [idAdopcion=" + idAdopcion + ", idMascota=" + idMascota + ", fecha=" + fecha + ", idUsuario="
				+ idUsuario + "]";
	}
	
	
	
}
