package org.openinfomanager.modules.oferta.mvc;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.intalio.tempo.web.ApplicationState;
import org.openinfomanager.core.security.CustomUserDetails;
import org.openinfomanager.mail.MailSender;
import org.openinfomanager.modules.administration.model.Usuario;
import org.openinfomanager.modules.administration.service.UsuarioService;
import org.openinfomanager.modules.cartera.model.DetallePaqueteServicio;
import org.openinfomanager.modules.cartera.service.PaqueteServicioService;
import org.openinfomanager.modules.clientes.model.Cliente;
import org.openinfomanager.modules.crm.model.Producto;
import org.openinfomanager.modules.crm.model.Proyecto;
import org.openinfomanager.modules.crm.service.IProductoService;
import org.openinfomanager.modules.crm.service.IProyectoService;
import org.openinfomanager.modules.oferta.model.DetallesOferta;
import org.openinfomanager.modules.oferta.model.Oferta;
import org.openinfomanager.modules.oferta.service.DetallesOfertaService;
import org.openinfomanager.modules.oferta.service.OfertaService;
import org.openinfomanager.workflow.connectors.intaliobpms.ProcessInitiatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class AddOfertaController extends SimpleFormController{
	private static final Logger _log = LoggerFactory
	.getLogger(AddOfertaController.class);
	
	private OfertaService ofertaService;
	private DetallesOfertaService service;
	private UsuarioService usuarioService;
	private IProyectoService proyectoService;
	private IProductoService productoService;
	private PaqueteServicioService paqueteService;

	public void setPaqueteService(PaqueteServicioService paqueteService) {
		this.paqueteService = paqueteService;
	}

	private MailSender mailService;
	public void setMailService(MailSender mailService) {
		this.mailService = mailService;
	}
	public void setProductoService(IProductoService productoService) {
		this.productoService = productoService;
	}

	public void setProyectoService(IProyectoService proyectoService) {
		this.proyectoService = proyectoService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public void setService(DetallesOfertaService service) {
		this.service = service;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		_log.debug("ejecutando formBackingObject");
		boolean isget = request.getMethod().equalsIgnoreCase("get");
		if(isget==true){
		  Long idoferta = ServletRequestUtils.getLongParameter(request,
			"idoferta");
			if(idoferta!=null){ 
			  Oferta oferta = ofertaService.findOfertaById(idoferta);
			  oferta.getDetalles();
			  request.getSession().setAttribute("oferta", oferta);
			}
		}
		return super.formBackingObject(request);
	}
	
	public AddOfertaController() 
	{
		setCommandName("ofertaCommand");
		setCommandClass(OfertaCommand.class);
		setFormView("modules/crm/ofertaform");
		setSuccessView("modules/crm/ofertadata");
		setValidateOnBinding(true);
	}

	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(
				Long.class, nf, true));
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("dd-MM-yyyy"), true));
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,Object command,BindException error){
		OfertaCommand m = (OfertaCommand) command;
		_log.info("The following data was submitted: " + m.toString());
		ModelAndView mav = new ModelAndView(getSuccessView());
		Long user = 0L;	
		Proyecto p = null;
		Cliente cl = null;
		
		String idcliente = request.getParameter("idcliente");
		String idproyecto = request.getParameter("idproyecto");
		if((idcliente==null||idcliente.isEmpty() || idcliente.equalsIgnoreCase(""))&&idproyecto!=null)
		{
			cl = new Cliente();
			cl.setIdcliente(proyectoService.findProyectoById(Long.parseLong(idproyecto)).getCliente().getIdcliente());
		}
		if(idcliente!=null && !idcliente.isEmpty() && !idcliente.equalsIgnoreCase(""))
		{
			cl = new Cliente();
			cl.setIdcliente(Long.parseLong(idcliente));
		}
		if(idproyecto!=null&&!idproyecto.isEmpty() && !idproyecto.equalsIgnoreCase(""))
		{
			p = new Proyecto();
			p.setIdproyecto(Long.parseLong(idproyecto));
		}
		
		Oferta mu = new Oferta(); 
		mu.setIdoferta(m.getIdoferta());
		mu.setEstado("Nuevo");
		mu.setContactos(m.getContactos());
	    mu.setFechaEnvio(m.getFechaEnvio());
	    mu.setMensaje(m.getMensaje());
	    mu.setObservaciones(m.getObservaciones());
	    mu.setTipoEnvio(m.getTipoEnvio());
	    mu.setCantEspecialistas(m.getCantEspecialistas());
		mu.setProyecto(p);
		mu.setCliente(cl);
	    
	    if(m.getNombre()==null || m.getNombre().isEmpty())
	    {
	    	mu.setNombre("Oferta Comercial Para: ");
	    }
	    else
	    {
	    	  mu.setNombre(m.getNombre());
	    }

		SecurityContext sch = SecurityContextHolder.getContext();
		Authentication auth = sch.getAuthentication();
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof CustomUserDetails) {
			CustomUserDetails cud = (CustomUserDetails) principal;
			_log.debug("propiedades extras de user details");
			Map<String, Object> extra = cud.getExtraInfo();
			user = Long.parseLong(extra.get("userid").toString());
			mu.setAutor(usuarioService.findUsuarioById(user));
		}
		String id = request.getParameter("idoferta");
		if(!id.isEmpty()&&!id.equalsIgnoreCase("")&&id!=null)
		{
			Long idof = Long.parseLong(id);
			
			if(idof>0)
			{
				Cliente cliente = ofertaService.findOfertaById(idof).getCliente();
				if(cliente.getIdcliente()!=cl.getIdcliente())
					mu.setIdoferta(0);				
			}
		}
	    mu = ofertaService.saveOferta(mu);
	    if(idproyecto!=null)
	    {
		    p = proyectoService.findProyectoById(Long.parseLong(idproyecto));
		    p.setEstado("En Ejecucion");
		    p = proyectoService.saveProyecto(p);
	    }
	    
	    if(mu.getTipoEnvio().equals("Correo")|| mu.getTipoEnvio().equals("Entrega Personal y Correo"))
	    {
	    	this.sendMail(request, response,mu);
	    	mu.setEstado("Enviada");
	    	mu =  ofertaService.saveOferta(mu);
	    } 
	 
		List<Long> mip = m.getProductos();
		double importe = 0.0;
		String nombreProducto = "";
		String descProducto = "";

		for (int i = 0; i < mip.size(); i++) 
		{
			DetallesOferta ds = new DetallesOferta(); 
			Long ac = mip.get(i);
			ds.setIddetalleoferta(0);
			ds.setOferta(mu);
			
			Producto prod = new Producto();
			prod.setIdproducto(ac);
			ds.setProducto(prod);
			Producto producto= productoService.findProductoById(prod.getIdproducto());
			if(mip.size()==1 && !producto.getCategoria().getTipocategoria().equalsIgnoreCase("categoriaproducto")&&!producto.getCategoria().getTipocategoria().equalsIgnoreCase("categoriapaquete")/* producto.getTarifaHoraria()!=0.0 || producto.getTiempoDuracion()!=0.0*/)
			{
				mu.setTarifaHoraria(producto.getTarifaHoraria());
				mu.setTiempoDuracion(producto.getTiempoDuracion());
				mu.setImporte(producto.getPrecioMN());
			}
			importe  = importe+ producto.getPrecioMN();
			String prodNombre = "- " +producto.getNombre() ;
			nombreProducto =  nombreProducto+ " " + prodNombre +"."+"\n"; 
			String descProd = "";

			if(producto.getCategoria().getTipocategoria().equalsIgnoreCase("categoriaproducto"))
			{
				descProd = "- "+producto.getCategoria().getDescripcion();
				descProducto = descProducto + " " +descProd +"."+ "\n";
				mu.setTipo("producto");
			}
			else if(producto.getCategoria().getTipocategoria().equalsIgnoreCase("categoriaservicio"))
			{
				descProd = "- "+producto.getDescripcion();
				descProducto = descProducto + " " +descProd +"."+ "\n";
				mu.setTipo("servicio");
			}
			else 
			{
				if(producto.getRef().getProcedencia().equalsIgnoreCase("cartera"))
				{
					Set<DetallePaqueteServicio> misdetalles = paqueteService.retrievePaqueteServicioById(Long.parseLong(producto.getRef().getIdref())).getDetalles();
					for(DetallePaqueteServicio dps: misdetalles)
					{
						if(dps.getElemento().getCategoria().getTipocategoria().equalsIgnoreCase("categoriaproducto"))
						{
							descProd = "- "+dps.getElemento().getCategoria().getDescripcion();
							descProducto = descProducto + " " +descProd +"."+ "\n";	
						}
					}				
				}
				mu.setTipo("paquete");
			}
			
			ds = service.saveDetallesOferta(ds);
			_log.info("guardando detalle de oferta con idproducto= " + ds.getProducto().getIdproducto());
			_log.info("guardando nombre de productos  " + nombreProducto);
			_log.info("guardando descripcion de productos = " + descProducto);
			_log.info("la categoria de productos es = " + producto.getCategoria().getTipocategoria());
		}
		
		mu.setImporte(importe);
		mu.setServicioAContratar(nombreProducto);
		mu.setDescServicio(descProducto);
		mu = ofertaService.saveOferta(mu);
			
			//ModelAndView mav = new ModelAndView(getSuccessView());
			mav.addObject("oferta",mu);
			ProcessInitiatorImpl initiator = new ProcessInitiatorImpl();
			ApplicationState as = (ApplicationState)request.getSession().getAttribute(ApplicationState.PARAMETER_NAME);
			String pipaTemplate = "NuevaOferta.ftl";
			String userCredentials=as.getCurrentUser().getToken(); 
			String pipaTaskForm="Oferta.xform";//"4748efe4-65eb-4366-a366-f8b6d63f4eda"; 
			HashMap pipaData= new HashMap();
			pipaData.put("idoferta",String.valueOf(mu.getIdoferta()));
			pipaData.put("oferta", mu.getNombre());
			String res=initiator.startProccess(userCredentials, pipaTaskForm, pipaData, pipaTemplate);
			if(res.indexOf("ok")>-1)
				mav.addObject("success", Boolean.TRUE);
			else{
				mav.addObject("success", Boolean.FALSE);
				mav.addObject("message",res);
			}
		return mav;
	}

	public void setOfertaService(OfertaService ofertaService) {
		this.ofertaService = ofertaService;
	}
	private void sendMail(HttpServletRequest request,
			HttpServletResponse response, Oferta mu) {
		long idoferta = mu.getIdoferta();
		//String targetGroup = request.getParameter("targetGroup");
		String[] msgTo;
		Oferta of = this.ofertaService.findOfertaById(idoferta);
		boolean isExternal = false;
		//if (targetGroup.equalsIgnoreCase("comision"))
			//msgTo = grupoService.getMemberEmailsByGroup("Comisi√≥n de Ofertas");
		//else {
			msgTo = of.getContactos().split(",");
			isExternal = true;
		//}
		Usuario u = of.getAutor();
		String sender = u.getEmail();// u.getNombre()+" "+u.getApellidos()+"<"+u.getEmail()+">";NO
										// FUNCIONA AS√?
		send(of, sender, msgTo, isExternal);
		of.setEstado("Enviada");
		this.ofertaService.saveOferta(of);
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
}
