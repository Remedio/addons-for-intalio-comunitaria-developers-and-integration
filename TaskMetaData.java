package org.openinfomanager.workflow.taskcommon;

import valentino.rejaxb.annotations.ClassXmlNodeName;
import valentino.rejaxb.annotations.XmlAttributeName;
import valentino.rejaxb.annotations.XmlNodeName;

@ClassXmlNodeName("b4p:taskMetaData")
public class TaskMetaData {
	@XmlAttributeName("xmlns:b4p")
	private String xmlnsB4p;
	@XmlNodeName("b4p:nextTaskId")
	private String nextTaskId;
	@XmlNodeName("b4p:nextTaskURL")
	private String nextTaskURL;
	public String getXmlnsB4p() {
		return xmlnsB4p;
	}
	public void setXmlnsB4p(String xmlnsB4p) {
		this.xmlnsB4p = xmlnsB4p;
	}
	public String getNextTaskId() {
		return nextTaskId;
	}
	public void setNextTaskId(String nextTaskId) {
		this.nextTaskId = nextTaskId;
	}
	public String getNextTaskURL() {
		return nextTaskURL;
	}
	public void setNextTaskURL(String nextTaskURL) {
		this.nextTaskURL = nextTaskURL;
	}
}
