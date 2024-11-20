package controlador;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dao.FormularioSocioEconomicoDao;
import dao.MascotaDao;
import dao.SolicitudDao;
import dao.UsuarioDao;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import modelo.FormularioSocioEconomico;
import modelo.Mascota;
import modelo.Solicitud;
import modelo.Usuario;

@Named
@SessionScoped
public class ControladorSolicitud implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Solicitud> solicitudes;
	private List<Mascota> mascotas;
	private SolicitudDao solicitudDao;
	private MascotaDao mascotaDao;
	private UsuarioDao usuarioDao;
	private FormularioSocioEconomicoDao fseDao;
	private Mascota mascota;
	private Usuario usuario;
	private Solicitud solicitud;
	private FormularioSocioEconomico fse;
	private String estado;
	
	
	@PostConstruct
	public void init() {
		solicitudes = new ArrayList<>();
		solicitudDao = new SolicitudDao();
		mascotaDao = new MascotaDao();
		usuarioDao = new UsuarioDao();
		fseDao = new FormularioSocioEconomicoDao();
		mascota = new Mascota();
		usuario = new Usuario();
		fse = new FormularioSocioEconomico();
		mascotas = new ArrayList<>();
		
	}


	public List<Mascota> getMascotas() {
		return mascotas;
	}


	public void setMascotas(List<Mascota> mascotas) {
		this.mascotas = mascotas;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public List<Solicitud> getSolicitudes() {
		return solicitudes;
	}


	public void setSolicitudes(List<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public SolicitudDao getSolicitudDao() {
		return solicitudDao;
	}


	public void setSolicitudDao(SolicitudDao solicitudDao) {
		this.solicitudDao = solicitudDao;
	}


	public MascotaDao getMascotaDao() {
		return mascotaDao;
	}


	public void setMascotaDao(MascotaDao mascotaDao) {
		this.mascotaDao = mascotaDao;
	}


	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}


	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}


	public FormularioSocioEconomicoDao getFseDao() {
		return fseDao;
	}


	public void setFseDao(FormularioSocioEconomicoDao fseDao) {
		this.fseDao = fseDao;
	}


	public Mascota getMascota() {
		return mascota;
	}


	public void setMascota(Mascota mascota) {
		this.mascota = mascota;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Solicitud getSolicitud() {
		return solicitud;
	}


	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}


	public FormularioSocioEconomico getFse() {
		return fse;
	}


	public void setFse(FormularioSocioEconomico fse) {
		this.fse = fse;
	}
	
	public void verSolicitudes() throws IOException {
		solicitudes = solicitudDao.obtenerTodos();
		FacesContext context = FacesContext.getCurrentInstance();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("solicitudesLista", solicitudes);
		context.getExternalContext().redirect("ver-solicitudes-administrador.xhtml");
	}
	
	
	public void detallesSolicitud(Solicitud solicitud) throws Exception {
		mascota = mascotaDao.obtenerUno(solicitud.getIdMascota());
		usuario = usuarioDao.obtenerUno(solicitud.getIdUsuario());
		fse = fseDao.obtenerUno(solicitud.getIdFormularioSocioeconomico());
		System.out.print(mascota);
		System.out.print(usuario);
		System.out.print(fse);
		System.out.print(solicitud);
		FacesContext context = FacesContext.getCurrentInstance();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mascota", mascota);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fse", fse);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("solicitud", solicitud);
		context.getExternalContext().redirect("detalles-solicitud.xhtml");
	}
	
	public void responderSolicitud(Solicitud solicitud, String estado) throws IOException {
		solicitud.setEstadoSolicitud(estado);
		solicitud.setObvservaciones("Tu solicitud ha sido atendida por nuestros administradores");
		solicitudDao.actualizar(solicitud);
		//mascotas = mascotaDao.obtenerTodos();
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().redirect("ver-solicitudes-administrador.xhtml");
		//return "vista-administrador.xhtml";
	}
	
	
	public void detallesSolicitudUsuario(Solicitud solicitud) throws Exception {
		mascota = mascotaDao.obtenerUno(solicitud.getIdMascota());
		usuario = usuarioDao.obtenerUno(solicitud.getIdUsuario());
		fse = fseDao.obtenerUno(solicitud.getIdFormularioSocioeconomico());
		System.out.print(mascota);
		System.out.print(usuario);
		System.out.print(fse);
		System.out.print(solicitud);
		FacesContext context = FacesContext.getCurrentInstance();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mascota", mascota);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fse", fse);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("solicitud", solicitud);
		context.getExternalContext().redirect("detalle-solicitud-usuario.xhtml");
	}
	
}
