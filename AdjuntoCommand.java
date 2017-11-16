package org.openinfomanager.modules.oferta.mvc;

public class AdjuntoCommand{
	public Long getIdadjunto() {
		return idadjunto;
	}
	public void setIdadjunto(Long idadjunto) {
		if(idadjunto==null) idadjunto=0L;
		this.idadjunto = idadjunto;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre() {
		return nombre;
	}

	public Long idadjunto;
	private String nombre;
	public String referencia;
	public String tipo;
}