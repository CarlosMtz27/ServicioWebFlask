package modelo;

public class Mascota {
	private int idMascota;
	private String nombre;
	private String especie;
	private String raza;
	private int edad;
	private String descripcion;
	private String estado;
	private String descripcionSalud;
	private String foto;
	private byte[] imagen;

	public Mascota(int idMascota, String nombre, String especie, String raza, int edad, String descripcion,
			String estado, String descripcionSalud, String foto, byte[] imagen) {
		super();
		this.idMascota = idMascota;
		this.nombre = nombre;
		this.especie = especie;
		this.raza = raza;
		this.edad = edad;
		this.descripcion = descripcion;
		this.estado = estado;
		this.descripcionSalud = descripcionSalud;
		this.foto = foto;
		this.imagen = imagen;
	}

	public Mascota(int idMascota, String nombre, String especie, String raza, int edad, String descripcion,
			String estado, String descripcionSalud) {
		super();
		this.idMascota = idMascota;
		this.nombre = nombre;
		this.especie = especie;
		this.raza = raza;
		this.edad = edad;
		this.descripcion = descripcion;
		this.estado = estado;
		this.descripcionSalud = descripcionSalud;
	}




	public byte[] getImagen() {
		return imagen;
	}




	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public Mascota() {
	}

	public Mascota(String nombre, String especie, String raza, int edad, String descripcion, String estado,
			String descripcionSalud, String foto) {
		super();
		this.nombre = nombre;
		this.especie = especie;
		this.raza = raza;
		this.edad = edad;
		this.descripcion = descripcion;
		this.estado = estado;
		this.descripcionSalud = descripcionSalud;
		this.foto = foto;
	}

	public Mascota(Mascota mascota) {
		this.nombre = mascota.nombre;
		this.especie = mascota.especie;
		this.raza = mascota.raza;
		this.edad =mascota. edad;
		this.descripcion =mascota. descripcion;
		this.estado = mascota.estado;
		this.descripcionSalud = mascota.descripcionSalud;
		this.foto = mascota.foto;
	}
	

	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getDescripcionSalud() {
		return descripcionSalud;
	}
	public void setDescripcionSalud(String descripcionSalud) {
		this.descripcionSalud = descripcionSalud;
	}
	public int getIdMascota() {
		return idMascota;
	}
	public void setIdMascota(int idMascota) {
		this.idMascota = idMascota;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public String getRaza() {
		return raza;
	}
	public void setRaza(String raza) {
		this.raza = raza;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) throws Exception {
		if(edad > 0 && edad<25) {
			this.edad = edad;
		}else {
			throw new Exception("La edad debe estar entre 0 y 25");
		}
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Mascota [idMascota=" + idMascota + ", nombre=" + nombre + ", especie=" + especie + ", raza=" + raza
				+ ", edad=" + edad + ", descripcion=" + descripcion + ", estado=" + estado + ", idEstado="
				+ "]";
	}
	
}
