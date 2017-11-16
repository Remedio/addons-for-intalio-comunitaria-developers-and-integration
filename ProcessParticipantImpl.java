package org.openinfomanager.workflow.connectors.intaliobpms;

import java.util.HashMap;
import java.util.List;

import org.intalio.tempo.uiframework.Configuration;
import org.intalio.tempo.workflow.task.PATask;
import org.intalio.tempo.workflow.task.xml.XmlTooling;
import org.openinfomanager.taskservices.TaskActionHelper;
import org.openinfomanager.workflow.connectors.ProcessParticipant;
import org.openinfomanager.workflow.taskcommon.TaskCompleteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import valentino.rejaxb.XmlBinderFactory;

public class ProcessParticipantImpl implements ProcessParticipant {
	private static final Logger _log = LoggerFactory
			.getLogger(ProcessParticipantImpl.class);
	Configuration conf = Configuration.getInstance();
	TaskActionHelper hlp = TaskActionHelper.getInstance(conf);
	PATask mtask = null;

	@Override
	public String getTaskInputDataAsXmlString(String userCredentials,
			String taskId// ,List<String> properties
	) {

		String data;
		try {
			data = hlp.getTaskInputDataAsXmlString(userCredentials, taskId);
		} catch (Exception e) {
			data = "fault: " + e.getMessage();
			e.printStackTrace();
		}
		return data;
		// return parseData(data, properties);
	}

	@Override
	public Object getTaskInputDataAsObject(String userCredentials,
			String taskId, Class mclass) {

		Document data;
		Object obj = null;
		try {
			mtask = (PATask) hlp.getTask(userCredentials, taskId);
			data = mtask.getInput(); // hlp.getTaskInputDataAsDocument(userCredentials,
										// taskId);
			obj = mclass.newInstance();
			XmlBinderFactory factory = XmlBinderFactory.newInstance();
			factory.bind(obj, data);
		} catch (Exception e) {
			data = null;
			e.printStackTrace();
		}

		return obj;
		// return parseData(data, properties);
	}

	@Override
	public Object getTaskOutputDataAsObject(String userCredentials,
			String taskId, Class mclass) {

		Document data;
		Object obj = null;
		try {
			if (mtask == null)
				mtask = (PATask) hlp.getTask(userCredentials, taskId);
			data = mtask.getOutput(); // hlp.getTaskInputDataAsDocument(userCredentials,
										// taskId);
			_log.debug("output data: " + mtask.getOutputAsXmlString());
			obj = mclass.newInstance();
			if (data == null)
				return obj;
			XmlBinderFactory factory = XmlBinderFactory.newInstance();
			factory.bind(obj, data);
		} catch (Exception e) {
			data = null;
			e.printStackTrace();
		}

		return obj;
		// return parseData(data, properties);
	}

	private HashMap parseData(String data, List<String> properties) {
		HashMap map = new HashMap();

		for (String prop : properties) {
			String regex = "<" + prop + ">(\\.*)<\\/" + prop + ">";
			java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
			java.util.regex.Matcher m = p.matcher(data);
			String matchedText = "";
			if (m.find()) {
				matchedText = m.group();
			}
			map.put(prop, matchedText);
		}
		return map;
	}

	@Override
	public TaskCompleteResponse sendTaskData(String userCredentials, String user,
			String taskId, String xml) throws Exception {
		TaskCompleteResponse res = new TaskCompleteResponse();
		XmlBinderFactory factory = XmlBinderFactory.newInstance();
		String taskres = hlp.complete(userCredentials, taskId, user, xml);
		_log.debug("Complete Response: " + taskres);
		Document response = XmlTooling.deserializeDocument(taskres);
		factory.bind(res, response);
		return res;
	}
}
