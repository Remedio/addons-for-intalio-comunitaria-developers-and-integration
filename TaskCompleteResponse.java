package org.openinfomanager.workflow.taskcommon;


import valentino.rejaxb.annotations.ClassXmlNodeName;
import valentino.rejaxb.annotations.XmlAttributeName;
import valentino.rejaxb.annotations.XmlNodeName;

/**
 * 
 * @author José Gil
 *<response xmlns="http://www.intalio.com/bpms/workflow/ib4p_20051115">
    <b4p:taskMetaData xmlns:b4p="http://www.intalio.com/bpms/workflow/ib4p_20051115">
        <b4p:nextTaskId />
        <b4p:nextTaskURL />
    </b4p:taskMetaData>
     <b4p:status xmlns:b4p="http://www.intalio.com/bpms/workflow/ib4p_20051115">OK</b4p:status>
    <b4p:errorCode xmlns:b4p="http://www.intalio.com/bpms/workflow/ib4p_20051115" />
    <b4p:errorReason xmlns:b4p="http://www.intalio.com/bpms/workflow/ib4p_20051115" />
    <b4p:isChainedAfter xmlns:b4p="http://www.intalio.com/bpms/workflow/ib4p_20051115" />
</response>
 */
@ClassXmlNodeName("response")
public class TaskCompleteResponse {
	@XmlAttributeName("xmlns")
	private String xmlns;
	@XmlNodeName("b4p:taskMetaData")
	private TaskMetaData taskMetaData;
	@XmlNodeName("b4p:status")
	private String status;
	@XmlNodeName("b4p:errorCode")
    private String errorCode;
	@XmlNodeName("b4p:errorReason")
    private String errorReason;
	@XmlNodeName("b4p:isChainedAfter")
    private String isChainedAfter;
	public String getXmlns() {
		return xmlns;
	}
	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}
	public TaskMetaData getTaskMetaData() {
		return taskMetaData;
	}
	public void setTaskMetaData(TaskMetaData taskMetaData) {
		this.taskMetaData = taskMetaData;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public String getIsChainedAfter() {
		return isChainedAfter;
	}
	public void setIsChainedAfter(String isChainedAfter) {
		this.isChainedAfter = isChainedAfter;
	}
	
}
