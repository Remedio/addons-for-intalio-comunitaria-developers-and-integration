package org.openinfomanager.modules.oferta.mvc;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class OfertaValidator implements Validator{
	
	public boolean supports(Class arg0) {
		return arg0.equals(OfertaCommand.class);
	}

	public void validate(Object arg0, Errors e) {
		OfertaCommand m=(OfertaCommand)arg0;
		ValidationUtils.rejectIfEmpty(e, "idproyecto", "m.idproyecto.required","El idproyecto no puede estar vac�a");
	}
}
