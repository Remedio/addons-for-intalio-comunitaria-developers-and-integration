package org.openinfomanager.modules.oferta.mvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OfertaCommand 
{
	private Long idoferta; 

	private String nombre; 

	private Date fechaEnvio;

	private String contactos;
	
	private String observaciones;

	private String tipoEnvio;

	private String mensaje;

	private int cantEspecialistas;

	Double tiempoDuracion;

	Double tarifaHoraria ;

	Double importe ;
		
	/*List<AdjuntoCommand> adjuntos;

	public OfertaCommand(){
		adjuntos = LazyList.decorate(
			      new ArrayList<AdjuntoCommand>(),
			      FactoryUtils.instantiateFactory(AdjuntoCommand.class));
	}*/
	
	public int getCantEspecialistas() {
		return cantEspecialistas;
	}

	public void setCantEspecialistas(int cantEspecialistas) {
		this.cantEspecialistas = cantEspecialistas;
	}
	
	public Double getTiempoDuracion() {
		return tiempoDuracion;
	}

	public void setTiempoDuracion(Double tiempoDuracion) {
		if(tiempoDuracion==null || tiempoDuracion.isNaN()) tiempoDuracion=0.0;
		this.tiempoDuracion = tiempoDuracion;
	}

	public Double getTarifaHoraria() {
		return tarifaHoraria;
	}

	public void setTarifaHoraria(Double tarifaHoraria) {
		if(tarifaHoraria==null || tarifaHoraria.isNaN()) tarifaHoraria=0.0;
		this.tarifaHoraria = tarifaHoraria;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		if(importe==null || importe.isNaN()) importe=0.0;
		this.importe = importe;
	}


	List<Long> productos = new ArrayList<Long>();
	
	public List<Long> getProductos() {
		return productos;
	}

	public void setProductos(List<Long> productos) {
		this.productos = productos;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Long getIdoferta() {
		return idoferta;
	}

	public void setIdoferta(Long idoferta) {
		if(idoferta==null) idoferta=0L;
		this.idoferta = idoferta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setfechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public String getContactos() {
		return contactos;
	}

	public void setContactos(String contactos) {
		this.contactos = contactos;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getTipoEnvio() {
		return tipoEnvio;
	}

	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	
	
}
