package org.openinfomanager.workflow;


import valentino.rejaxb.annotations.ClassXmlNodeName;
import valentino.rejaxb.annotations.XmlAttributeName;
import valentino.rejaxb.annotations.XmlNodeName;
@ClassXmlNodeName("input")
public class RespuestaClienteInput implements INameSpaceBindable {


	@XmlAttributeName("xmlns:Solicitud1")
	private String xmlnsSolicitud1;
	@XmlAttributeName("xmlns:tns")
	private String xmlnsTns;
	@XmlAttributeName("xmlns")
	private String xmlns;
	
	
	@XmlNodeName("Fecha_de_Solucin")
	String Fecha_de_Solucin;
	
	
	
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
	public String getFecha_de_Solucin() {
		return Fecha_de_Solucin;
	}
	public void setFecha_de_Solucin(String fecha_de_Solucin) {
		Fecha_de_Solucin = fecha_de_Solucin;
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
