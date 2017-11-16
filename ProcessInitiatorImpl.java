package org.openinfomanager.workflow.connectors.intaliobpms;

import java.util.HashMap;

import org.intalio.tempo.uiframework.Configuration;
import org.openinfomanager.taskservices.TaskActionHelper;
import org.openinfomanager.workflow.connectors.ProcessInitiator;

public class ProcessInitiatorImpl implements ProcessInitiator {
	Configuration conf = Configuration.getInstance();
	TaskActionHelper hlp = TaskActionHelper.getInstance(conf);
	@Override
	public String startProccess(String userCredentials, String pipaTaskFormName,
			HashMap pipaData, String pipaTemplate){ 
		try {
			String pipaTaskID = hlp.getPipaIDByFormName(userCredentials, pipaTaskFormName);
			hlp.startPipa(pipaTaskID, userCredentials, pipaData, pipaTemplate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fault: " + e.getMessage();
		}
		return "ok";
	}

}
