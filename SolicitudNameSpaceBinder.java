package org.openinfomanager.workflow;

public class SolicitudNameSpaceBinder implements NameSpaceBinder {

	@Override
	public void bind(INameSpaceBindable input, INameSpaceBindable output) {
		output.setXmlns(input.getXmlns());
	}

}
