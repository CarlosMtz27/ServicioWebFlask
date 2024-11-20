package controlador;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

import org.primefaces.PrimeFaces;

import dao.UsuarioDao;
import modelo.Usuario;

@Named
@SessionScoped
public class RegistroUsuario implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private UsuarioDao usuarioDao;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        usuarioDao=new UsuarioDao();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String agregar() {
        usuario.setRol("usuario");
        usuarioDao.insertar(usuario);
        
        FacesContext context = FacesContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Registro exitoso"));
        try {
            context.getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "index";
       
    }
        
    public void agregarLogin() {
        usuario.setRol("usuario");
        System.out.println(usuario);

        try {
            if (usuario.getTelefono() != null && usuario.getTelefono().length() == 10) {
            	usuarioDao.insertar(usuario);
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro", "Tu registro ha sido exitoso");
                FacesContext.getCurrentInstance().addMessage(null, message);
                PrimeFaces.current().ajax().update("messages");
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El número de teléfono debe tener 10 dígitos y ningún campo debe estar vacío.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                PrimeFaces.current().ajax().update("messages");
            }
        } catch (Exception e) {
            // Manejar la excepción de formato de correo electrónico inválido
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El formato del correo electrónico es inválido.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            PrimeFaces.current().ajax().update("messages");
        }
    }

       
        //FacesContext context = FacesContext.getCurrentInstance();
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Registro exitoso"));
       
    
    
    public void registro() throws IOException {
    	 FacesContext context = FacesContext.getCurrentInstance();
    	 context.getExternalContext().redirect("registro_usuarios.xhtml");
    }

}
