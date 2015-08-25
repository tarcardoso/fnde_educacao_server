package br.com.fnde.educacao.rest;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.fnde.educacao.facade.EducacaoFacade;
import br.com.mec.fies.core.server.SpringManager;
import br.com.mec.fies.presenter.ConfirmaAbertura;
import br.com.mec.fies.presenter.Entrada;
import br.com.mec.fies.presenter.Publicacao;
import br.com.mec.fies.presenter.Retorno;
import br.com.mec.fies.presenter.RootJson;
import br.com.mec.fies.presenter.Sms;
import br.com.mec.fies.presenter.ValidaAditamento;
import br.com.mec.fies.util.EngineJson;

@Path("/servicos")
/**
 * o path era teste (anteriormente)
 * http://coenraets.org/blog/2011/12/restful-services-with-jquery-and-java-using-jax-rs-and-jersey/
 * http://www.mkyong.com/webservices/jax-rs/integrate-jackson-with-resteasy/
 * http://www.mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/
 * 
 * http://stackoverflow.com/questions/9332276/cannot-create-jdbc-driver-of-class-for-connect-url-null-tomcat-sql-serv
 * http://tomcat.apache.org/tomcat-6.0-doc/config/context.html#Defining_a_context
 * 
 * http://localhost/phpmyadmin/tbl_sql.php?db=fies&table=termofinanciamento&server=1&target=&token=0c2c90f712f77f31e56845d8a6f2eadf#PMAURL-2:db_structure.php?db=fies&table=&server=1&target=&token=0c2c90f712f77f31e56845d8a6f2eadf
 * 
 * http://localhost:8080/mecfies/ns/rest/fies/aditamento/parte/get?nuCpf=51964149754
 * http://localhost:8080/mecfies/ns/rest/fies/ies/parte/get?coAditamento=1
 * http://localhost:8080/mecfies/ns/rest/fies/termo/parte/get?coAditamento=1
 * http://localhost:8080/mecfies/ns/rest/fies/aditamento/disponivel/get?nuCpf=51964149754
 * 
 */
@Component("serviceTest")
public class ServicoRest {
	
	@Autowired	 
	private EducacaoFacade educacaoFacade;
	
	@Context
	HttpServletRequest request;
	
	@GET
	@Path("/getTimeLine")
	@Produces("text/json;charset=UTF-8")
	public String getTimeLine(@QueryParam("latitude") String latitude, @QueryParam("longitude") String longitude, 
			@QueryParam("page") Long page, @QueryParam("start") Long start, @QueryParam("limit") Long limits){
		init();
		
		System.out.println("latitude: "+latitude+" longitude: "+ longitude+" page: "+page);
		
		if( page >1) return "[]";
		
		this.educacaoFacade = getEducacaoFacade();
		
		String scheme = request.getScheme();
	    String serverName = request.getServerName();
	    int serverPort = request.getServerPort();
	    String context = request.getContextPath();
	    String url = scheme + "://" + serverName + ":" + serverPort + context;
	    String path = "/imagens";
	    
		return this.educacaoFacade.getTimeLine(url+path);
	}
	
	@GET
	@Path("/getDetalhePost")
	@Produces("text/json;charset=UTF-8")
	public String getDetalhePost( @QueryParam("idTimeLine") Long idTimeLine ){
	    
		String scheme = request.getScheme();
	    String serverName = request.getServerName();
	    int serverPort = request.getServerPort();
	    String context = request.getContextPath();
	    String url = scheme + "://" + serverName + ":" + serverPort + context;
	    String path = "/imagens";
	    
	    init();
		
		this.educacaoFacade = getEducacaoFacade();
		
		return this.educacaoFacade.getDetalhePost(idTimeLine, url+path);
	}
	
	@GET
	@Path("/getDetalheEscola")
	@Produces("text/json;charset=UTF-8")
	public String getDetalheEscola(@QueryParam("idEscola") Long idEscola){
		init();
		
		this.educacaoFacade = getEducacaoFacade();
		
		String scheme = request.getScheme();
	    String serverName = request.getServerName();
	    int serverPort = request.getServerPort();
	    String context = request.getContextPath();
	    String url = scheme + "://" + serverName + ":" + serverPort + context;
	    String path = "/imagens";
	    
		return this.educacaoFacade.getDetalheEscola( idEscola, url+path );
	}
	
	@GET
	@Path("/postCurti")
	@Produces("text/json;charset=UTF-8")
	public String postCurti(){
		init();
		String idTimeLine = request.getParameter("idTimeLine");
		
		this.educacaoFacade = getEducacaoFacade();
		
		int curti = this.educacaoFacade.curti( idTimeLine );
		
		return "{\"qtd\": "+ curti+" }";
	}

	@POST
	@Path("/postTimeLine")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postTimeLine(@RequestBody Publicacao publicacao){
		init();
		
		this.educacaoFacade = getEducacaoFacade();
		
		int idTimeLine = this.educacaoFacade.salvaTimeLine( publicacao );
		
		return "{ \"idPost\":"+idTimeLine+"}";
	}
	
	@GET
	@Path("/getEscolas")
	@Produces("text/json;charset=UTF-8")
	public String getEscolas(){
		init();
		
		this.educacaoFacade = getEducacaoFacade();
		
		return this.educacaoFacade.getEscolas();
	}


	public void init() {
		 
		SpringManager.getInstancia().setServletContext(request.getSession().getServletContext());
	}

	public EducacaoFacade getEducacaoFacade() {
		return SpringManager.getInstancia().getBean("educacaoFacade");
	}
	

}
