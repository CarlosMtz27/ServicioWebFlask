package controlador;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dao.MascotaDao;
import dao.SolicitudDao;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import modelo.Mascota;
import modelo.Solicitud;

@Named
@SessionScoped
public class ControladorUsuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private int idUsuario;
	private int idMascota;
	private SolicitudDao solicitudDao;
	private List<Solicitud> solicitudes;
	private List<Mascota> mascotas;
	private MascotaDao mascotaDao;

	@PostConstruct
	public void init() throws Exception {
		solicitudDao = new SolicitudDao();
		solicitudes = new ArrayList<>();
		mascotaDao = new MascotaDao();
		mascotas = mascotaDao.obtenerMascotasDisponiblesYEnEspera();
		solicitudes = solicitudDao.obtenerSolicitudesPorUsuario(idUsuario);
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public List<Solicitud> getSolicitudes() {
		return solicitudes;
	}
	
	

	public List<Mascota> getMascotas() {
		return mascotas;
	}

	public void setMascotas(List<Mascota> mascotas) {
		this.mascotas = mascotas;
	}

	public MascotaDao getMascotaDao() {
		return mascotaDao;
	}

	public void setMascotaDao(MascotaDao mascotaDao) {
		this.mascotaDao = mascotaDao;
	}

	public void setSolicitudes(List<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public SolicitudDao getSolicitudDao() {
		return solicitudDao;
	}

	public void setSolicitudDao(SolicitudDao solicitudDao) {
		this.solicitudDao = solicitudDao;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void adoptar(int idMascota, int id) throws IOException {
		System.out.println(id);
		System.out.println(idMascota);
		FacesContext context = FacesContext.getCurrentInstance();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idUsuario", id);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idMascota", idMascota);
		context.getExternalContext().redirect("formulario-socioeconomico.xhtml");

	}

	/*
	 * public void verSolicitudes(int idUsuario) throws IOException { this.idUsuario
	 * = idUsuario; System.out.println(idUsuario); solicitudes =
	 * solicitudDao.obtenerSolicitudesPorUsuario(this.idUsuario);
	 * System.out.println(solicitudes); FacesContext context =
	 * FacesContext.getCurrentInstance();
	 * 
	 * FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
	 * "solicitudesLista", solicitudes);
	 * context.getExternalContext().redirect("ver-solicitudes-usuario.xhtml");
	 * 
	 * //return ""; }
	 */

	public void verSolicitudes(int idUsuario) throws IOException {
		this.idUsuario = idUsuario;
		System.out.println(idUsuario);
		solicitudes = solicitudDao.obtenerSolicitudesPorUsuario(this.idUsuario);
		System.out.println(solicitudes);
		FacesContext context = FacesContext.getCurrentInstance();

		// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("solicitudesLista",
		// solicitudes);
		context.getExternalContext().redirect("ver-solicitudes-usuario.xhtml");

		// return "";
	}

	public void regresar() throws IOException {
		
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().redirect("ver-solicitudes-usuario.xhtml");

	}
	
	public void homeUsuario() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().redirect("home_usuario.xhtml");
	}

}
