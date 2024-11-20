package controlador;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.util.IOUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.model.file.UploadedFile;

import dao.MascotaDao;
import dao.SolicitudDao;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
//import jakarta.inject.Inject;
import jakarta.inject.Named;
import modelo.Mascota;
import modelo.Solicitud;

@Named
@SessionScoped
public class AdministradorControlador1 implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Mascota> mascotas;
	private Mascota selectedMascota;
	private List<Mascota> selectedMascotas;
	private List<Solicitud> solicitudes;
	private Solicitud solicitud;
	private SolicitudDao solicitudDao;
	// @Inject
	private MascotaDao mascotaDao;
	private UploadedFile file;

	@PostConstruct
	public void init() throws Exception {
		mascotaDao = new MascotaDao();
		solicitudDao = new SolicitudDao();
		this.mascotas = this.mascotaDao.obtenerMascotasDisponiblesYEnEspera();
		this.solicitudes = this.solicitudDao.obtenerTodos();

	}

	public List<Mascota> getMascotas() {
		return mascotas;
	}

	public List<Solicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

	public Solicitud getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(Solicitud solicitud) {
		this.solicitud = solicitud;
	}

	public SolicitudDao getSolicitudDao() {
		return solicitudDao;
	}

	public void setSolicitudDao(SolicitudDao solicitudDao) {
		this.solicitudDao = solicitudDao;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void setMascotas(List<Mascota> mascotas) {
		this.mascotas = mascotas;
	}

	public Mascota getSelectedMascota() {
		return selectedMascota;
	}

	public void setSelectedMascota(Mascota selectedMascota) {
		this.selectedMascota = selectedMascota;
	}

	public List<Mascota> getSelectedMascotas() {
		return selectedMascotas;
	}

	public void setSelectedMascotas(List<Mascota> selectedMascotas) {
		this.selectedMascotas = selectedMascotas;
	}

	public MascotaDao getMascotaDao() {
		return mascotaDao;
	}

	public void setMascotaDao(MascotaDao mascotaDao) {
		this.mascotaDao = mascotaDao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void openNew() {
		this.selectedMascota = new Mascota();
	}

	public String verMascotas() throws Exception {
		mascotas = mascotaDao.obtenerMascotasDisponiblesYEnEspera();
		FacesContext context = FacesContext.getCurrentInstance();
		// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mascotasLista",
		// mascotas);
		context.getExternalContext().redirect("mostrar_mascotas1.xhtml");
		return "mostrar_mascotas.xhtml";
	}

	public void guardarMascota() throws Exception {
		String rutaCarpeta = "C:\\Users\\Ozzy\\Desktop\\programacionweb\\mascotas.zip_expanded\\mascotas\\src\\main\\webapp\\imagenes";
		try {
			if (this.selectedMascota.getIdMascota() == 0) {

				if (file != null && file.getSize() > 0) {
					this.selectedMascota.setFoto(file.getFileName());
					this.selectedMascota.setImagen(file.getContent());

					System.out.print(selectedMascota.getIdMascota());
					System.out.print(file.getFileName());
					escribirBytes(IOUtils.toByteArray(file.getInputStream()), rutaCarpeta, file.getFileName());
					mascotaDao.insertar(this.selectedMascota);
					this.mascotas = mascotaDao.obtenerMascotasDisponiblesYEnEspera();
					FacesContext context = FacesContext.getCurrentInstance();
					UIComponent tabla = context.getViewRoot().findComponent(":form:dt-products");
					PrimeFaces.current().ajax().update("form:imagenMascota");

					if (tabla != null) {
						context.getPartialViewContext().getRenderIds().add(tabla.getClientId());
					}

					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mascota Añadida"));

				} else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "No se adjuntó ningún archivo"));
				}

			} else {
				this.selectedMascota.setFoto(file.getFileName());
				this.selectedMascota.setImagen(file.getContent());
				System.out.print(selectedMascota.getIdMascota());
				System.out.print(file.getFileName());
				escribirBytes(IOUtils.toByteArray(file.getInputStream()), rutaCarpeta, file.getFileName());
				mascotaDao.actualizar(selectedMascota);
				this.mascotas = mascotaDao.obtenerMascotasDisponiblesYEnEspera();
				FacesContext context = FacesContext.getCurrentInstance();
				UIComponent tabla = context.getViewRoot().findComponent(":form:dt-products");
				if (tabla != null) {
					context.getPartialViewContext().getRenderIds().add(tabla.getClientId());
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mascota Actualizada"));
			}

		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, e.getMessage(), "Error"));
		} catch (NullPointerException ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El archivo adjunto es nulo"));
		}
		/*
		 * if(this.selectedMascota.getNombre() == null) {
		 * this.selectedMascota.setNombre(UUID.randomUUID().toString().replaceAll("-",
		 * "").substring(0, 9)); this.mascotas.add(this.selectedMascota);
		 * FacesContext.getCurrentInstance().addMessage(null, new
		 * FacesMessage("Mascota Añadida")); }else {
		 * FacesContext.getCurrentInstance().addMessage(null, new
		 * FacesMessage("Mascota Actualizada")); }
		 */

		PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
	}

	public void eliminarMascota() {
		mascotaDao.eliminar(selectedMascota.getIdMascota());
		mascotas.remove(selectedMascota);
		// selectedMascotas.remove(selectedMascota);
		selectedMascota = null;
		/// this.mascotas = mascotaDao.obtenerTodos();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Mascota Eliminada"));
		//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mascota Eliminada"));
		PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
	}

	public String getDeleteButtonMessage() {
		if (hasSelectedProducts()) {
			int size = this.selectedMascotas.size();
			return size > 1 ? size + " Mascotas Seleccionadas" : "1 Mascota seleccionada";
		}

		return "Delete";
	}

	public boolean hasSelectedProducts() {
		return this.selectedMascotas != null && !this.selectedMascotas.isEmpty();
	}

	public void deleteSelectedProducts() {
		this.mascotas.removeAll(this.selectedMascotas);
		this.selectedMascotas = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mascotas Eliminadas"));
		PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
		PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
	}

	public void escribirBytes(byte[] bytes, String carpeta, String nombreImagen) {
		try {
			Path path = Paths.get(carpeta, nombreImagen);
			Files.write(path, bytes);
			System.out.print("La imagen ya se escribio\n");
			System.out.print(path.getNameCount());

		} catch (IOException ex) {
			System.out.println("error al escribir los bytes" + ex.getMessage());
		}
	}

	public void verSolicitudes() throws IOException {

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().redirect("ver-solicitudes-administrador.xhtml");
		// return "ver-solicitudes-administrador.xhtml";
	}

	public void inicioAdministrador() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().redirect("vista-administrador.xhtml");
	}

	public void homeAdmin() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().redirect("mostrar-mascotas1.xhtml");
	}
	
	

}
