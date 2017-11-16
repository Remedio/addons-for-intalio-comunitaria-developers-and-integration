package org.openinfomanager.core.mvc;

import org.openinfomanager.core.menu.MenuCommand;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class MenuValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return clazz.equals(MenuCommand.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		MenuCommand m=(MenuCommand)obj;		
		ValidationUtils.rejectIfEmpty(errors,"action.titulo","m.titulo.required","El campo Titulo es requerido");
		ValidationUtils.rejectIfEmpty(errors,"action.descripcion","m.descripcion.required","El campo Descripcion es requerido");
		ValidationUtils.rejectIfEmpty(errors,"action.tipo","m.tipo.required","El campo Tipo es requerido");
		ValidationUtils.rejectIfEmpty(errors,"orden","m.orden.required","El campo Orden es requerido");
		ValidationUtils.rejectIfEmpty(errors,"showtip","m.showtip.required","El campo Mostrar Tip es requerido");

	}

}
