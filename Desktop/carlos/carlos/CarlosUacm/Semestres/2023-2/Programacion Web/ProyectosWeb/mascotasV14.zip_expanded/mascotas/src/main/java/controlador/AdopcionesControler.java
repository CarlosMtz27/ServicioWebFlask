package controlador;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.primefaces.PrimeFaces;

import dao.TransaccionDao;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import modelo.FormularioSocioEconomico;
import modelo.Solicitud;

@Named
@SessionScoped
public class AdopcionesControler implements Serializable {

	private static final long serialVersionUID = 1L;
	private FormularioSocioEconomico fse;
	private Solicitud solicitud;
	//private FormularioSocioEconomicoDao fseDao;
	//private SolicitudDao solicitudDao;
	TransaccionDao transaccion;
	private int idUsuario;
	private int idMascota;

	@PostConstruct
	public void init() {
		fse = new FormularioSocioEconomico();
		solicitud = new Solicitud();
		transaccion = new TransaccionDao();
		//fseDao = new FormularioSocioEconomicoDao();
		//solicitudDao = new SolicitudDao();
	}

	/*public FormularioSocioEconomicoDao getFseDao() {
		return fseDao;
	}

	public void setFseDao(FormularioSocioEconomicoDao fseDao) {
		this.fseDao = fseDao;
	}

	public SolicitudDao getSolicitudDao() {
		return solicitudDao;
	}

	public void setSolicitudDao(SolicitudDao solicitudDao) {
		this.solicitudDao = solicitudDao;
	}*/

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public FormularioSocioEconomico getFse() {
		return fse;
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

	public void setFse(FormularioSocioEconomico fse) {
		this.fse = fse;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String procesoDeAdopcion(FormularioSocioEconomico fse, int idUsuario) throws Exception {
		System.out.println("Proceso de adopcion");
		System.out.println(fse);
		System.out.println(this.idMascota);
		System.out.println(idUsuario);
		LocalDateTime fechaHoraActual = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy: HH:mm");
		String cadenaFechaHoraActual = fechaHoraActual.format(formatter);

		
		System.out.println(cadenaFechaHoraActual);
		fse.setIdUsuario(idUsuario);
		solicitud.setIdUsuario(idUsuario);
		solicitud.setIdMascota(idMascota);
		solicitud.setFechaSolicitud(cadenaFechaHoraActual);
		solicitud.setEstadoSolicitud("En espera de respuesta");
		solicitud.setObvservaciones("Tu solicitud esta en proceso de ser atendida por algunos de nuestros administradores");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Solicitud enviada"));
		PrimeFaces.current().ajax().update("messages");
		this.idMascota=0;
		transaccion.transaccionSolicituFormularioSE(solicitud,fse);
		return "home_usuario.xhtml";

	}
}
