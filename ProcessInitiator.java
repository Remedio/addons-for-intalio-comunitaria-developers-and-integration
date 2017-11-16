package org.openinfomanager.workflow.connectors;

import java.util.HashMap;

public interface ProcessInitiator {
	/**
	 * 
	 * @param userCredentials token
	 * @param pipaTaskID id of the pipa task
	 * @param pipaData   HasMap<String,String> parameter value
	 * @param pipaTemplate Freemarker Template File path
	 * @return ok or fault message
	 */
	String startProccess(String userCredentials,String pipaTaskID, HashMap pipaData, String pipaTemplate);
}
