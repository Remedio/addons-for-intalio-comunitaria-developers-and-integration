package org.openinfomanager.workflow;

import valentino.rejaxb.annotations.ClassXmlNodeName;
import valentino.rejaxb.annotations.XmlAttributeName;
import valentino.rejaxb.annotations.XmlNodeName;

@ClassXmlNodeName("output")
public class ResponsableOutput implements INameSpaceBindable {

	@XmlAttributeName("xmlns")
	private String xmlns;
	
	@XmlNodeName("Responsable")
	String Responsable;
	
	
	@XmlNodeName("subproceso")
	String subproceso;
	@XmlNodeName("idsolicitud")
	String idsolicitud;
	public String getXmlns() {
		return xmlns;
	}
	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}
	public String getResponsable() {
		return Responsable;
	}
	public void setResponsable(String responsable) {
		Responsable = responsable;
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
