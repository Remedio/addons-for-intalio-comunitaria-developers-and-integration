package org.openinfomanager.workflow;

import valentino.rejaxb.annotations.ClassXmlNodeName;
import valentino.rejaxb.annotations.XmlAttributeName;
import valentino.rejaxb.annotations.XmlNodeName;

@ClassXmlNodeName("input")
public class SolicitudInput implements INameSpaceBindable {
	@XmlAttributeName("xmlns:Solicitud1")
	private String xmlnsSolicitud1;
	@XmlAttributeName("xmlns:tns")
	private String xmlnsTns;
	@XmlAttributeName("xmlns")
	private String xmlns;
	@XmlNodeName("informacion")
	String informacion;
	@XmlNodeName("fechadecaducida")
	String fechadecaducida;
	@XmlNodeName("notas")
	String notas;
	@XmlNodeName("subproceso")
	String subproceso;
	@XmlNodeName("idsolicitud")
	String idsolicitud;
	public String getXmlnsSolicitud1() {
		return xmlnsSolicitud1;
	}
	public void setXmlnsSolicitud1(String xmlnsSolicitud1) {
		this.xmlnsSolicitud1 = xmlnsSolicitud1;
	}
	public String getXmlnsTns() {
		return xmlnsTns;
	}
	public void setXmlnsTns(String xmlnsTns) {
		this.xmlnsTns = xmlnsTns;
	}
	public String getXmlns() {
		return xmlns;
	}
	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}
	public String getInformacion() {
		return informacion;
	}
	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}
	public String getFechadecaducida() {
		return fechadecaducida;
	}
	public void setFechadecaducida(String fechadecaducida) {
		this.fechadecaducida = fechadecaducida;
	}
	public String getNotas() {
		return notas;
	}
	public void setNotas(String notas) {
		this.notas = notas;
	}
	public String getSubproceso() {
		return subproceso;
	}
	public void setSubproceso(String subproceso) {
		this.subproceso = subproceso;
	}
	public String getIdsolicitud() {
		return idsolicitud;
	}
	public void setIdsolicitud(String idsolicitud) {
		this.idsolicitud = idsolicitud;
	}
	@Override
	public String toString(){
		return "SolicitudInput " + idsolicitud; 
	}
}
