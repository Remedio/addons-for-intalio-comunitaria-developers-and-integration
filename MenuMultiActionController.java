package org.openinfomanager.core.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openinfomanager.core.menu.MenuService;
import org.openinfomanager.modules.administration.model.Menu;
import org.openinfomanager.security.ISecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class MenuMultiActionController extends MultiActionController {
	private static final Logger _log = LoggerFactory
			.getLogger(MenuSFController.class);

	private MenuService menuService;
    private long moduleID;
    private ISecurityManager secMgr;
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public ModelAndView getAllMenus(HttpServletRequest request,
			HttpServletResponse response) {
		SecurityContext sch = SecurityContextHolder.getContext();
		ModelAndView mv = new ModelAndView("modules/menus");
		List<Menu> menus = new ArrayList<Menu>();
		if(request.getParameterMap().containsKey("menubuilder")){
		menus=menuService.getMenus();		
		mv.addObject("pmaskList",secMgr.getStringMapCommaDelimitedClassPermissionMask(sch.getAuthentication(),moduleID) );
		}else{
			List<Long> authIds = secMgr.getAuthorityListIds(sch.getAuthentication());
			List aus = new ArrayList();
			for(Long l:authIds) aus.add((long)l);
			menus = menuService.getMenusByUser(aus);
		}
		mv.addObject("menus",menus);
		return mv;
	}
	public void setModuleID(String moduleID) {
		this.moduleID = Long.parseLong(moduleID);
	}

	public void setSecurityManager(ISecurityManager secMgr) {
		this.secMgr = secMgr;
	}
}
