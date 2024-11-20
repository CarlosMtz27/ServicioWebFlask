package modelo;

public class FormularioSocioEconomico {
	private int idFormulario;
	private int idUsuario;
	private Double ingresoMensual;
	private String tipoVivienda;
	private int horasDisponibles;
	private String experiencia;
	private int otrosAnimalesEnHogar;
	private String informacionAdicional;
	
	
	public int getIdFormulario() {
		return idFormulario;
	}
	public void setIdFormulario(int idFormulario) {
		this.idFormulario = idFormulario;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Double getIngresoMensual() {
		return ingresoMensual;
	}
	public void setIngresoMensual(Double ingresoMensual) {
		this.ingresoMensual = ingresoMensual;
	}
	public String getTipoVivienda() {
		return tipoVivienda;
	}
	public void setTipoVivienda(String tipoVivienda) {
		this.tipoVivienda = tipoVivienda;
	}
	public int getHorasDisponibles() {
		return horasDisponibles;
	}
	public void setHorasDisponibles(int horasDisponibles) throws Exception {
		
		if(horasDisponibles>0 && horasDisponibles<24) {
			this.horasDisponibles = horasDisponibles;
		}else {
			throw new Exception("Las horas deben ser mayores a 0");
		}
		
	}
	public String getExperiencia() {
		return experiencia;
	}
	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
	}
	public int getOtrosAnimalesEnHogar() {
		return otrosAnimalesEnHogar;
	}
	public void setOtrosAnimalesEnHogar(int otrosAnimalesEnHogar) throws Exception {
		if(otrosAnimalesEnHogar>=0) {
			this.otrosAnimalesEnHogar = otrosAnimalesEnHogar;
		}else {
			throw new Exception("Los animales deben ser mayores o iguales a 0");
		}
		
	}
	public String getInformacionAdicional() {
		return informacionAdicional;
	}
	public void setInformacionAdicional(String informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}
	@Override
	public String toString() {
		return "FormularioSocioEconomico [idFormulario=" + idFormulario + ", idUsuario=" + idUsuario
				+ ", ingresoMensual=" + ingresoMensual + ", tipoVivienda=" + tipoVivienda + ", horasDisponibles="
				+ horasDisponibles + ", experiencia=" + experiencia + ", otrosAnimalesEnHogar=" + otrosAnimalesEnHogar
				+ ", informacionAdicional=" + informacionAdicional + "]";
	}
	
	
}
