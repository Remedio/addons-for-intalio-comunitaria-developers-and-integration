package org.openinfomanager.modules.oferta.mvc;

import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openinfomanager.mail.MailSender;
import org.openinfomanager.modules.administration.model.Usuario;
import org.openinfomanager.modules.administration.service.GrupoService;
import org.openinfomanager.modules.administration.service.UsuarioService;
import org.openinfomanager.modules.contract.service.ContractUtilService;
import org.openinfomanager.modules.oferta.model.DetallesOferta;
import org.openinfomanager.modules.oferta.model.Oferta;
import org.openinfomanager.modules.oferta.service.DetallesOfertaService;
import org.openinfomanager.modules.oferta.service.OfertaService;
import org.openinfomanager.modules.oferta.service.PagoAnticipadoService;

import org.openinfomanager.security.ISecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class OfertaMultiActionController extends MultiActionController {
	private static final Logger _log = LoggerFactory
			.getLogger(OfertaMultiActionController.class);

	private OfertaService ofertaService;
	private DetallesOfertaService detallesOfertaService;
	private ContractUtilService  utilService;
	private PagoAnticipadoService pagoAnticipadoService;

	public void setPagoAnticipadoService(PagoAnticipadoService pagoAnticipadoService) {
		this.pagoAnticipadoService = pagoAnticipadoService;
	}
	public ContractUtilService getUtilService() {
		return utilService;
	}
	public void setUtilService(ContractUtilService utilService) {
		this.utilService = utilService;
	}

	private ISecurityManager secMgr;
	private long moduleID;
	
	public void setModuleID(String moduleID) {
		this.moduleID = Long.parseLong(moduleID);
	}
	public void setSecurityManager(ISecurityManager secMgr) {
		this.secMgr = secMgr;
	}
	
	private MailSender mailService;

	private GrupoService grupoService;

	private UsuarioService usuarioService;

	public static class OfertaSearch {
		String estado;
		String autor;
		String nombre;
		String idoferta;
		String idcliente;
		String cliente;
		List<Long> detalles = new ArrayList<Long>();

		public List<Long> getDetalles() {
			return detalles;
		}

		public void setDetalles(List<Long> detalles) {
			this.detalles = detalles;
		}

		public String getCliente() {
			return cliente;
		}

		public void setCliente(String cliente) {
			this.cliente = cliente;
		}

		public String getAutor() {
			return autor;
		}

		public void setAutor(String autor) {
			this.autor = autor;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getIdcliente() {
			return idcliente;
		}

		public void setIdcliente(String idcliente) {
			this.idcliente = idcliente;
		}

		public String getIdoferta() {
			return idoferta;
		}

		public void setIdoferta(String idoferta) {
			this.idoferta = idoferta;
		}

		@Override
		public String toString() {
			return "OfertaSearch Parameters, estado: " + estado + ", oferta: "
					+ idoferta;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

	}

	public ModelAndView getModuleOferta(HttpServletRequest req,
			HttpServletResponse res) {
		ModelAndView mv = new ModelAndView("modules/ofertas");
		SecurityContext sch = SecurityContextHolder.getContext();
		mv.addObject("pmaskList",secMgr.getStringMapCommaDelimitedClassPermissionMask(sch.getAuthentication(),moduleID) );
		
			return mv;
		}
	
	public ModelAndView doSearchOferta(HttpServletRequest request,
			HttpServletResponse response, OfertaSearch ofertaSearch) {
		_log.debug("doSearch with " + ofertaSearch);
		ModelAndView mv = new ModelAndView("modules/oferta/ofertasearchresult");

		Map map = new HashMap();
		map = buildSearchMap(map, ofertaSearch);
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));

		if (map.containsKey("cliente")) {
			String idcliente = (String) map.get("cliente");
			List<Oferta> ofertas = ofertaService
					.getOfertaesByCliente(idcliente);

			mv.addObject("totalRows", ofertas.size());
			mv.addObject("ofertas", ofertas);
		} else {

			mv.addObject("totalRows",
					ofertaService.countOfertaBySearchCriteria(map));
			mv.addObject("ofertas",
					ofertaService.findOfertaBySearchCriteria(map, start, limit));
		}
		mv.addObject("success",true);
		mv.addObject("message","La operaci�n ha sido realizada con �xito.");	
		return mv;
	}

	public ModelAndView doDeleteOferta(HttpServletRequest request,
		HttpServletResponse response, OfertaSearch ofertaSearch)
	{
		long idoferta = Long.parseLong(request.getParameter("idoferta"));
		ModelAndView mv = new ModelAndView("modules/oferta/ofertasearchresult");
		try
		{
			ofertaService.deleteOferta(idoferta);
		
		}
		catch (DataIntegrityViolationException e) {
			
			mv.addObject("success",false); 
			mv.addObject("message","Esta oferta no puede ser eliminada porque su estado no es nuevo. ");
			return mv;		
		
		}
		catch (DataAccessException e) {
			mv.addObject("success",false); 
			mv.addObject("message","Ha ocurrido un error al ejecutar la accion eliminar. Si considera que esto no debe ocurrir contacte al administrador.");
			_log.info(e.getMessage());
			return mv;

		}
		return doSearchOferta(request, response, ofertaSearch);
	}

	public ModelAndView doDeleteDetallesOferta(HttpServletRequest request,
			HttpServletResponse response, OfertaSearch ofertaSearch) {
		try{detallesOfertaService.deleteDetallesOferta(Long.parseLong(request
				.getParameter("iddetalleoferta")));}
		catch (DataIntegrityViolationException e) {
			ModelAndView mv = new ModelAndView("modules/oferta/detallesofertasearchresult");
			mv.addObject("success",false); 
			mv.addObject("message","Este detalle de oferta no puede ser eliminado porque hay otros objetos que dependen de Ã©l. ");
			return mv;
		}	
		catch (DataAccessException e) {
			ModelAndView mv = new ModelAndView("modules/oferta/detallesofertasearchresult");
			mv.addObject("success",false); 
			mv.addObject("message","Ha ocurrido un error al ejecutar la accion eliminar. Si considera que esto no debe ocurrir contacte al administrador.");
			_log.info(e.getMessage());
			return mv;

		}
		return doSearchDetallesOferta(request, response, ofertaSearch);
	}

	public ModelAndView doSearchDetallesOferta(HttpServletRequest request,
			HttpServletResponse response, OfertaSearch ofertaSearch) {
		_log.debug("doSearch with idoferta = "
				+ request.getParameter("idoferta"));

		ModelAndView mv = new ModelAndView(
				"modules/oferta/detallesofertasearchresult");
		Map map = new HashMap();
		map = buildSearchMap(map, ofertaSearch);

		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		mv.addObject("total",
				detallesOfertaService.countDetallesOfertaBySearchCriteria(map));
		mv.addObject("data", detallesOfertaService
				.findDetallesOfertaBySearchCriteria(map, start, limit));
		mv.addObject("success",true);
		return mv;
	}
	
	public ModelAndView doSearchDetallePago(HttpServletRequest request,
			HttpServletResponse response, OfertaSearch ofertaSearch) {
		long idoferta = Long.parseLong(request.getParameter("idoferta"));
		_log.debug("doSearch with idoferta = "+idoferta );

		ModelAndView mv = new ModelAndView(
				"modules/oferta/detallepagosearchresults");
		Map map = new HashMap();
		map = buildSearchMap(map, ofertaSearch);

		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		mv.addObject("total",
				detallesOfertaService.countDetallesOfertaBySearchCriteria(map));
		mv.addObject("detalles", detallesOfertaService
				.findDetallesOfertaBySearchCriteria(map, start, limit));
		mv.addObject("success",true);
		return mv;
	}
	
	public ModelAndView doSearchPago(HttpServletRequest request,
			HttpServletResponse response, OfertaSearch ofertaSearch) {

		ModelAndView mv = new ModelAndView(
				"modules/oferta/pagosearchresult");
		Map map = new HashMap();
		map = buildSearchMap(map, ofertaSearch);
		if(map.isEmpty())
			map.put("estado", "Nuevo");
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		mv.addObject("total",
				pagoAnticipadoService.countPagoAnticipadoBySearchCriteria(map));
		mv.addObject("pagos", pagoAnticipadoService
				.findPagoAnticipadoBySearchCriteria(map, start, limit));
		mv.addObject("success",true);
		return mv;
	}
	
	public ModelAndView updateEstado(HttpServletRequest request,
			HttpServletResponse response, OfertaSearch ofertaSearch) {
		long idOferta=Long.parseLong(ofertaSearch.getIdoferta());
		_log.debug("doSearch with idoferta = "
				+ idOferta);

		ModelAndView mv = new ModelAndView("xmlSuccessView");
		
		Oferta of=ofertaService.findOfertaById(idOferta);
		of.setEstado(ofertaSearch.getEstado());
		ofertaService.saveOferta(of);
		mv.addObject("success","true");
		return mv;
	}

	/*original de gil
	 * 
	 * public ModelAndView sendMail(HttpServletRequest request,
			HttpServletResponse response) {
		long idoferta = Long.parseLong(request.getParameter("idoferta"));
		String targetGroup = request.getParameter("targetGroup");
		String[] msgTo;
		Oferta of = this.ofertaService.findOfertaById(idoferta);
		boolean isExternal = false;
		if (targetGroup.equalsIgnoreCase("comision"))
			msgTo = grupoService.getMemberEmailsByGroup("ComisiÃ³n de Ofertas");
		else {
			msgTo = of.getContactos().split(",");
			isExternal = true;
		}
		Usuario u = of.getAutor();
		String sender = u.getEmail();// u.getNombre()+" "+u.getApellidos()+"<"+u.getEmail()+">";NO
										// FUNCIONA ASÃ�
		send(of, sender, msgTo, isExternal);
		ModelAndView mv = new ModelAndView("xmlSuccessView");
		mv.addObject("success","true");
		return mv;// TODO modelandview
	}*/
	
	public ModelAndView sendMail(HttpServletRequest request,
			HttpServletResponse response) {
		long idoferta = Long.parseLong(request.getParameter("idoferta"));
		String targetGroup = request.getParameter("targetGroup");
		String[] msgTo;
		Oferta of = this.ofertaService.findOfertaById(idoferta);
		boolean isExternal = false;
		if (targetGroup.equalsIgnoreCase("comision"))
			msgTo = grupoService.getMemberEmailsByGroup("ComisiÃ³n de Ofertas");
		else {
			msgTo = of.getContactos().split(",");
			isExternal = true;
		}
		Usuario u = of.getAutor();
		String sender = u.getEmail();// u.getNombre()+" "+u.getApellidos()+"<"+u.getEmail()+">";NO
										// FUNCIONA ASÃ�
		send(of, sender, msgTo, isExternal);
		of.setEstado("Enviada");
		this.ofertaService.saveOferta(of);
		ModelAndView mv = new ModelAndView("xmlSuccessView");
		mv.addObject("success","true");
		return mv;// TODO modelandview
	}

	private void send(Oferta oferta, String sender, String[] msgTo,
			boolean isExternal) {
		// TODO make this locale sensitive per recipient
		_log.debug("tratando de enviar mensaje de oferta");
		// prepare message content
		StringBuffer sb = new StringBuffer();
		if (isExternal == false) sb.append("<p><strong>Mensaje: </strong></p><p>");
		sb.append(oferta.getMensaje());
		
		if (isExternal == false) {
			sb.append("</p><p><strong>Observaciones:</strong></p><p>");
			sb.append(oferta.getObservaciones());
			sb.append("</p>");
		}
		
		// sb.append(anchor);
		if (_log.isDebugEnabled()) {
			_log.debug("mail content: " + sb);
		}
		// prepare message
		String msgText = sb.toString();
		String msgSubject = "Oferta de Desoft sobre " + oferta.getNombre();
		String msgFrom = sender;
		String msTo = "";
		for (int i = 0; i < msgTo.length; i++) {
			msTo += msgTo[i]+",";
		}
		//msTo = msTo.substring(1, msTo.length());
        msTo=msTo+sender;
		Map<String, String> attachmentMap = new HashMap<String, String>(oferta
				.getDetalles().size());
		Set<DetallesOferta> ds = oferta.getDetalles();
		for (DetallesOferta d : ds)//Modificado por YANARA FAVOR YANARA MIRA ESTO(adjuntos de productos)
			attachmentMap.put(d.getProducto().getNombre(), d.getProducto().getReferencia());

		attachmentMap.put("oferta","/openinfomanager/reportengine.htm?report=32&format=pdf&empresa=1&idoferta="+oferta.getIdoferta());
		// send message
		try {
			this.mailService.sendMailWithAttachments(msgText, msgSubject,
					msgFrom, msTo, attachmentMap, isExternal);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doGenerateContract(HttpServletRequest request, HttpServletResponse response,OfertaSearch search)
	{
		String dir = getServletContext().getRealPath("/openinfomanager/");
		String dirok =  dir.replace("\\", "/");
		_log.info("esto es dirok en el controller "+dirok);
		String newDir = dirok.replaceFirst("/openinfomanager/", "/");
		_log.info("esto es newDir en el controller "+newDir);
		Long idoferta = Long.parseLong(request.getParameter("idoferta"));
		String nuevo = request.getParameter("nuevo");
		utilService.doGenerateContract(idoferta,newDir,nuevo);
	}
	
	public ModelAndView doGenerateContractByDetails(HttpServletRequest request, HttpServletResponse response,OfertaSearch search)
	{
		long idoferta = Long.parseLong(request.getParameter("idoferta"));
		List<Long> misdetalles = search.getDetalles();

		String dir = getServletContext().getRealPath("/openinfomanager/");
		String dirok =  dir.replace("\\", "/");
		_log.info("esto es dirok en el controller "+dirok);
		String newDir = dirok.replaceFirst("/openinfomanager/", "/");
		_log.info("esto es newDir en el controller "+newDir);

		String nuevo = request.getParameter("nuevo");
		if(misdetalles.size()>0)
		utilService.doGenerateContract(misdetalles,idoferta,newDir,nuevo);
		return doSearchOferta(request, response, search);
	}
	
	public void setOfertaService(OfertaService ofertaService) {
		this.ofertaService = ofertaService;
	}

	public void setDetallesOfertaService(
			DetallesOfertaService detallesOfertaService) {
		this.detallesOfertaService = detallesOfertaService;
	}

	public void setMailService(MailSender mailService) {
		this.mailService = mailService;
	}

	private Map buildSearchMap(Map map, OfertaSearch ofertaSearch) {
		if (ofertaSearch.getEstado() != null
				&& ofertaSearch.getEstado().isEmpty() == false) {
			map.put("estado", ofertaSearch.getEstado());
		}
		if (ofertaSearch.getNombre() != null
				&& ofertaSearch.getNombre().isEmpty() == false) {
			map.put("nombre", ofertaSearch.getNombre());
		}
		if (ofertaSearch.getAutor() != null
				&& ofertaSearch.getAutor().isEmpty() == false) {
			map.put("autor", ofertaSearch.getAutor());
		}
		if (ofertaSearch.getIdoferta() != null
				&& ofertaSearch.getIdoferta().isEmpty() == false) {
			map.put("oferta", ofertaSearch.getIdoferta());
		}

		if (ofertaSearch.getIdcliente() != null
				&& ofertaSearch.getIdcliente().isEmpty() == false) {
			map.put("cliente", ofertaSearch.getIdcliente());
		}
		if (ofertaSearch.getCliente()!= null
				&& ofertaSearch.getCliente().isEmpty() == false) {
			map.put("clienteNombre", ofertaSearch.getCliente());
		}
		return map;
	}

	public void setGrupoService(GrupoService grupoService) {
		this.grupoService = grupoService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
}
