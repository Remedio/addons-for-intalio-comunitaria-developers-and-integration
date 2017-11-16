package org.openinfomanager.workflow.connectors;

import java.util.HashMap;
import java.util.List;

import org.openinfomanager.workflow.taskcommon.TaskCompleteResponse;

public interface ProcessParticipant {
	/**
	 * Fetch Current Task Data
	 * @param userCredentials user token
	 * @param taskId pa task id
	 * @param properties a list of properties to get into the HashMap
	 * @return ok or fault message
	 */
	String getTaskInputDataAsXmlString(String userCredentials, String taskId);
	/**
	 * Sends data back to process
	 * @param userCredentials user token
	 * @param taskId pa task id
	 * @param paData HashMap Data
	 * @param paTemplate Fremarker template file path
	 * @return ok or fault message
	 * @throws Exception 
	 */
	//TaskCompleteResponse sendTaskData(String userCredentials,String user,String taskId, HashMap paData, String paTemplate) throws Exception;
	Object getTaskInputDataAsObject(String userCredentials, String taskId, Class mclass);
	Object getTaskOutputDataAsObject(String userCredentials, String taskId,
			Class mclass);
	TaskCompleteResponse sendTaskData(String token, String user, String id, String xml) throws Exception;
}
