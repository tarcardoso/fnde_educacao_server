package br.com.fnde.educacao.rest;


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

import br.com.fnde.educacao.facade.EducacaoFacade;
import br.com.fnde.educacao.presenter.Improprio;
import br.com.fnde.educacao.presenter.Publicacao;
import br.com.mec.fies.core.server.SpringManager;

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
	
	@POST
	@Path("/improprio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String salvaImproprio(@RequestBody Improprio improprio){
		init();
		System.out.println( improprio );
		
		this.educacaoFacade = getEducacaoFacade();
		
		long idImproprio = this.educacaoFacade.salvaImproprio( improprio );
		System.out.println("idImproprio: "+idImproprio);
	    
		return "{}";
	}
	
	@GET
	@Path("/improprio")
	@Produces("text/json;charset=UTF-8")
	public String getImproprio(){
		String json = "["+
				"    { "+
				"    	\"id\": 1, "+
				"        \"nome\": \"Span ou fraude\", "+
				"        \"descricao\": \"Usado para conteúdo exclusivamente comerciais ou qualquer ato de má fé com intuito de ludibriar alguém.\" "+
				"    }, "+
				"    { "+
				"    	\"idEscola\": 2, "+
				"        \"nome\": \"A foto coloca pessoas em risco\", "+
				"        \"descricao\": \"Usado para denunciar imagens que retratam auto flagelação, perseguição online (cyberbullying) ou uso de drogas.\" "+
				"    }, "+
				"    { "+
				"    	\"idEscola\": 3, "+
				"        \"nome\": \"A foto não deveria estar aqui\", "+
				"        \"descricao\": \"Usado em caso de nudez, pornografia, violação de direitos autorais ou discursos de ódio.\" "+
				"    } "+
				"] ";
		
		return json;
	}
	
	
	@GET
	@Path("/getTimeLine")
	@Produces("text/json;charset=UTF-8")
	public String getTimeLine(
			@QueryParam("latitude") String latitude, @QueryParam("longitude") String longitude, 
			@QueryParam("page") Long page, @QueryParam("start") Long start, @QueryParam("limit") Long limits){
		init();
		
		System.out.println("latitude: "+latitude+" longitude: "+ longitude+" page: "+page);
		
		if( page != null && page >1) return "[]";
		
		this.educacaoFacade = getEducacaoFacade();
		
		String scheme = request.getScheme();
	    String serverName = request.getServerName();
	    int serverPort = request.getServerPort();
	    String context = request.getContextPath();
	    String url = scheme + "://" + serverName + ":" + serverPort + context;
	    String path = "/imagens";
	    
		return this.educacaoFacade.getTimeLine(url+path, latitude, longitude);
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
		String idUsuario = request.getParameter("idUsuario");
		
		this.educacaoFacade = getEducacaoFacade();
		
		int curti = this.educacaoFacade.curti( idTimeLine, idUsuario );
		
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
	public String getEscolas(@QueryParam("latitude") String latitude, @QueryParam("longitude") String longitude){
		init();
		
		this.educacaoFacade = getEducacaoFacade();
		
		return this.educacaoFacade.getEscolas(latitude, longitude);
	}

	public void init() {
		SpringManager.getInstancia().setServletContext(request.getSession().getServletContext());
	}

	public EducacaoFacade getEducacaoFacade() {
		return SpringManager.getInstancia().getBean("educacaoFacade");
	}

}
