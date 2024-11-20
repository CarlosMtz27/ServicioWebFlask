package controlador;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.PrimeFaces;

import dao.MascotaDao;
import dao.UsuarioDao;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import modelo.Mascota;
import modelo.Usuario;

@Named
@SessionScoped
public class ControladorLogin implements Serializable {
	private static final long serialVersionUID = 1L;
	private String correo;
	private String password;
	private UsuarioDao usuarioDao;
	private MascotaDao mascotaDao;
	private List<Mascota> mascotas;
	private Usuario usuario;

	@PostConstruct
	public void init() {
		usuarioDao = new UsuarioDao();
		mascotaDao = new MascotaDao();
		mascotas = new ArrayList<>();
		usuario = new Usuario();
	}

	public MascotaDao getMascotaDao() {
		return mascotaDao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setMascotaDao(MascotaDao mascotaDao) {
		this.mascotaDao = mascotaDao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Mascota> getMascotas() {
		return mascotas;
	}

	public void setMascotas(List<Mascota> mascotas) {
		this.mascotas = mascotas;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public void login(String correo, String password) throws Exception {
		if (!correo.isEmpty() && !password.isEmpty()) {
			System.out.println("Entres");
			this.usuario = usuarioDao.validarLogin(correo, password);
			System.out.print(usuario);

			if (this.usuario != null) {
				if (this.usuario.getRol().equals("admin") || this.usuario.getRol().equals("ADMIN")) {
					System.out.println("\n\n\nEntreADMIN");
					FacesContext context = FacesContext.getCurrentInstance();
			
					context.getExternalContext().redirect("../administrador/mostrar-mascotas1.xhtml");
				} else {
					System.out.println("\n\n\nEntreUSER");
					FacesContext context = FacesContext.getCurrentInstance();
				
					context.getExternalContext().redirect("../usuario/home_usuario.xhtml");
				}
			} else {
				FacesContext.getCurrentInstance().addMessage("username",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Correo o contraseña incorrectos"));
				PrimeFaces.current().ajax().update("messages");
			}
		}
		// return null;
	}

	public void logout() throws IOException {
		this.correo = null;
		this.password = null;
		this.usuario = null;
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().redirect("../../index.xhtml");
	}

	public void inicio() throws IOException {
		this.correo = null;
		this.password = null;
		this.usuario = null;
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().redirect("../../index.xhtml");
	}

	public void loginVista() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().redirect("Vistas/home/login.xhtml");
	}
	
	
}
