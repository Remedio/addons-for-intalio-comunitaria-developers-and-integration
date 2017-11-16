package org.openinfomanager.workflow;
import valentino.rejaxb.annotations.ClassXmlNodeName;
import valentino.rejaxb.annotations.XmlAttributeName;
import valentino.rejaxb.annotations.XmlNodeName;
@ClassXmlNodeName("input")
public class CierreInput implements INameSpaceBindable {

	@XmlAttributeName("xmlns:Solicitud1")
	private String xmlnsSolicitud1;
	@XmlAttributeName("xmlns:tns")
	private String xmlnsTns;
	@XmlAttributeName("xmlns")
	private String xmlns;
	
	
	@XmlNodeName("No_Conformidad_cierre")
	String No_Conformidad_cierre;
	
	@XmlNodeName("Estado")
	String Estado;
	
	
	
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
	public String getNo_Conformidad_cierre() {
		return No_Conformidad_cierre;
	}
	public void setNo_Conformidad_cierre(String no_Conformidad_cierre) {
		No_Conformidad_cierre = no_Conformidad_cierre;
	}
	public String getEstado() {
		return Estado;
	}
	public void setEstado(String estado) {
		Estado = estado;
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
	
	
}
