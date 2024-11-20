package modelo;

public class Solicitud {
	private int idSolicitud;
	private int idUsuario;
	private int idMascota;
	private int idFormularioSocioeconomico;
	private String fechaSolicitud;
	private String estadoSolicitud;
	private String obvservaciones;
	
	
	
	public Solicitud(int idUsuario, int idMascota, int idFormularioSocioeconomico, String fechaSolicitud,
			String estadoSolicitud, String obvservaciones) {
		super();
		this.idUsuario = idUsuario;
		this.idMascota = idMascota;
		this.idFormularioSocioeconomico = idFormularioSocioeconomico;
		this.fechaSolicitud = fechaSolicitud;
		this.estadoSolicitud = estadoSolicitud;
		this.obvservaciones = obvservaciones;
	}
	public Solicitud() {
		super();
	}
	public Solicitud(int idSolicitud, int idUsuario, int idMascota, int idFormularioSocioeconomico,
			String fechaSolicitud, String estadoSolicitud, String obvservaciones) {

		this.idSolicitud = idSolicitud;
		this.idUsuario = idUsuario;
		this.idMascota = idMascota;
		this.idFormularioSocioeconomico = idFormularioSocioeconomico;
		this.fechaSolicitud = fechaSolicitud;
		this.estadoSolicitud = estadoSolicitud;
		this.obvservaciones = obvservaciones;
	}
	public int getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(int idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdMascota() {
		return idMascota;
	}
	public void setIdMascota(int idMascota) {
		this.idMascota = idMascota;
	}
	
	public int getIdFormularioSocioeconomico() {
		return idFormularioSocioeconomico;
	}
	public void setIdFormularioSocioeconomico(int idFormularioSocioeconomico) {
		this.idFormularioSocioeconomico = idFormularioSocioeconomico;
	}
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}
	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}
	public String getObvservaciones() {
		return obvservaciones;
	}
	public void setObvservaciones(String obvservaciones) {
		this.obvservaciones = obvservaciones;
	}
	@Override
	public String toString() {
		return "Solicitud [idSolicitud=" + idSolicitud + ", idUsuario=" + idUsuario + ", idMascota=" + idMascota
				+ ", idFormularioSocioeconomico=" + idFormularioSocioeconomico + ", fechaSolicitud=" + fechaSolicitud
				+ ", estadoSolicitud=" + estadoSolicitud + ", obvservaciones=" + obvservaciones + "]";
	}
	
	
	
}
