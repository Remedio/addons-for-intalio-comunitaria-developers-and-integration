package org.openinfomanager.workflow;


import valentino.rejaxb.annotations.ClassXmlNodeName;
import valentino.rejaxb.annotations.XmlAttributeName;
import valentino.rejaxb.annotations.XmlNodeName;
@ClassXmlNodeName("input")
public class ResponsableInput implements INameSpaceBindable {


		@XmlAttributeName("xmlns:Solicitud1")
		private String xmlnsSolicitud1;
		@XmlAttributeName("xmlns:tns")
		private String xmlnsTns;
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
