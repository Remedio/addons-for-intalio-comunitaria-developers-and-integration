package org.openinfomanager.core.mvc;

import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;

import org.openinfomanager.core.menu.MenuCommand;
import org.openinfomanager.core.menu.MenuService;
import org.openinfomanager.modules.administration.model.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class MenuSFController extends SimpleFormController {
	private static final Logger _log = LoggerFactory
			.getLogger(MenuSFController.class);

	private MenuService menuService;

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public MenuSFController() {
		setCommandName("menuCommand");
		setCommandClass(MenuCommand.class);
		setFormView("modules/menubuilder/menu");
		setSuccessView("modules/menubuilder/menu");
		setValidateOnBinding(true);
		setValidator(new MenuValidator());
	}

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, nf, true));
		// binder.registerCustomEditor(boolean.class, new CustomBooleanEditor())
	}

	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		_log.debug("ejecutando formBackingObject");
		_log.debug("idaction from request: "
				+ request.getParameter("idaction"));
		if (isFormSubmission(request)) {
			long idaction = ServletRequestUtils.getLongParameter(request,
					"action.idaction");
			if(idaction!=0L){
				Menu menu = menuService.findMenuById(idaction);
				request.getSession().setAttribute("menu", menu);
			}
		}
		return super.formBackingObject(request);
	}

	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		MenuCommand m = (MenuCommand) command;
		_log.debug("Datos enviados: " + m.getAction().getTitulo());

		long pertenece = m.getParentMenu();
		_log.debug("el padre menu es" + pertenece);

		Menu menu = new Menu();
		menu.setTitulo(m.getAction().getTitulo());
		menu.setDescripcion(m.getAction().getDescripcion());
		menu.setTipo(m.getAction().getTipo());
		menu.setLink(m.getAction().getLink());
		menu.setLinkType(m.getAction().getLinkType());
		menu.setScript(m.getAction().getScript());
		menu.setIconref(m.getAction().getIconref());
		
		menu.setOrden(m.getOrden());
		menu.setShowtip((m.getShowtip()?1:0));
		_log.debug("show tip: " + m.getShowtip());
		
		if(pertenece!=0L)
			menu.setParentMenu(menuService.findMenuById(pertenece));
		else
			menu.setParentMenu(null);
		
		if (m.getAction().getIdaction() == 0L) {
			_log.debug("saving menu");
			menu = menuService.saveMenu(menu);
		} else {
			_log.debug("updating menu with idaction" + m.getAction().getIdaction());
			menu.setIdaction(m.getAction().getIdaction());
			menu = menuService.updateMenu(menu);
		}
		ModelAndView mav = new ModelAndView(getSuccessView());
		mav.addObject("menu", menu);
		mav.addObject("success", true);
		return mav;
	}
}
