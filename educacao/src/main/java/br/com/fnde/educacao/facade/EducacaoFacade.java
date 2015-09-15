package br.com.fnde.educacao.facade;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import br.com.fnde.educacao.dao.CurtirTimeLineDAO;
import br.com.fnde.educacao.dao.EscolaDAO;
import br.com.fnde.educacao.dao.ImproprioTimeLineDAO;
import br.com.fnde.educacao.dao.TimeLineDAO;
import br.com.fnde.educacao.domain.CurtirTimeLine;
import br.com.fnde.educacao.domain.Escola;
import br.com.fnde.educacao.domain.ImproprioTimeLine;
import br.com.fnde.educacao.domain.TimeLine;
import br.com.fnde.educacao.presenter.EscolaDistancia;
import br.com.fnde.educacao.presenter.Improprio;
import br.com.fnde.educacao.presenter.Publicacao;


@Component("educacaoFacade")
public class EducacaoFacade {

	@Autowired
	private EscolaDAO escolaDAO;
	
	@Autowired
	private TimeLineDAO timeLineDAO;
	
	@Autowired
	private ImproprioTimeLineDAO improprioTimeLineDAO;
	
	@Autowired
	private CurtirTimeLineDAO curtirTimeLineDAO;
	
	public EducacaoFacade() {
		super();
	}	
	
	@Transactional
	public String getDetalheEscola(Long idEscola, String pathImagem) {
		Escola escola = escolaDAO.getById( idEscola );
		
		JsonArray jarr = new JsonArray();
		if( escola != null ){
			JsonObject jo = new JsonObject();
			jo.addProperty("idTimeLine", idEscola );
			
			jo.addProperty("nome",  escola.getNoEscola() );
			jo.addProperty("endereco",  escola.getTxEndereco() );
			jo.addProperty("imagem",  escola.getTxImagem() );
			jo.addProperty("tipo", "Consolidado");
			
			JsonObject jsAvaliacao = new JsonObject();
	
			jsAvaliacao.addProperty("INFRAESTRUTURA", 0 );
			jsAvaliacao.addProperty("LIVROS", 0 );
			jsAvaliacao.addProperty("OBRAS", 0 );
			jsAvaliacao.addProperty("EQUIPAMENTOS", 0 );
			jsAvaliacao.addProperty("TECNOLOGIA", 0 );
			
			
			jo.add("avaliacao", jsAvaliacao );
			
			jarr.add( jo );
			
			List<TimeLine> lst = timeLineDAO.getByIdEscola(idEscola);
			montaJsonTimeLine(lst, jarr, pathImagem);
		}
		return jarr.toString();
	}	
	
	@Transactional
	public String getNotificacao(String latitude, String longitude){
		JsonArray jarr = new JsonArray();
		
		List<EscolaDistancia> lst = null;
		if( latitude != null && longitude != null ){
			lst = escolaDAO.getByLatitudeLongitude( Double.parseDouble(latitude), Double.parseDouble(longitude) );
		}
		if( lst != null ){
			//TODO fazer a logica para buscar dos favoritos
			for (EscolaDistancia tl : lst) {
				JsonObject jo = new JsonObject();
				jo.addProperty("idEscola", tl.getIdescola() );
				jo.addProperty("descricao", "Proximo a escola "+ tl.getNoescola() + " endereço "+tl.getTxendereco() );
				jarr.add( jo );
			}
		}
		return jarr.toString();
	}
	
	@Transactional
	public String getEscolas(Long page, Long start, Long limits, String latitude, String longitude){
		JsonArray jarr = new JsonArray();
		
		List<EscolaDistancia> lst = null;
		if( latitude != null && longitude != null ){
			lst = escolaDAO.getByLatitudeLongitude(page, start, limits, Double.parseDouble(latitude), Double.parseDouble(longitude) );
		}
		if( lst != null ){
			//TODO fazer a logica para buscar dos favoritos
			for (EscolaDistancia tl : lst) {
				JsonObject jo = new JsonObject();
				jo.addProperty("idEscola", tl.getIdescola() );
				
				jo.addProperty("nome",  tl.getNoescola() );
				jo.addProperty("distancia",  formataDistancia(tl.getDistancia()) );
				jo.addProperty("endereco",  tl.getTxendereco() );
				jo.addProperty("imagem",  tl.getTximagem() );
				
				System.out.println( jo );
				jarr.add( jo );
			}
		}
		return jarr.toString();
	}
	
	private String formataDistancia(Long n) {
		if( n == null) return "";
		double d = (double)n/1000;

		return String.format("%.2f", d)+" km";
	}

	@Transactional
	public Long curti(String idTimeLine, String idUsuario){
		CurtirTimeLine curtir = new CurtirTimeLine();
		
		Long qtdCurti = curtirTimeLineDAO.getJaCurtido(idTimeLine, idUsuario);
		if( qtdCurti == 0 ){
			curtir.setIdTimeLine( Long.parseLong( idTimeLine) );
			curtir.setIdUsuario(  idUsuario );
			
			curtirTimeLineDAO.insert(curtir);
			
			
			return timeLineDAO.updateCurti(idTimeLine);
		}else{
			return qtdCurti;
		}
	}
	
	@Transactional
	public long salvaImproprio(Improprio improprio) {
		ImproprioTimeLine itl = new ImproprioTimeLine();
		itl.setIdImproprio( Long.parseLong(improprio.getIdImproprio()) );
		itl.setIdTimeLine( Long.parseLong(improprio.getIdTimeLine()) );
		itl.setIdUsuario( improprio.getIdUsuario() );
		
		improprioTimeLineDAO.insert(itl);
		
		return itl.getIdImproprioTimeLine();
	}
	
	@Transactional
	public int salvaTimeLine(Publicacao publicacao) {
		TimeLine tl = new TimeLine();
		tl.setDsTimeLine( publicacao.getDescricao() );
		tl.setQtCurti( 0l );
		tl.setDtTimeLine( new java.sql.Date(System.currentTimeMillis() ));
		if( publicacao.getIdEscola() != null ){
			tl.setIdEscola( Long.parseLong(publicacao.getIdEscola()) );
		}
		
		//TODO definir informacoes do facebook
		if( publicacao.getNomeUsuario()  != null){
			tl.setNoFaceBook( publicacao.getNomeUsuario() );
		}
		tl.setChaveFaceBook( publicacao.getChaveUsuario() );
		if( publicacao.getTipo() != null ){
			tl.setTpTimeLine( Integer.parseInt(publicacao.getTipo()) );
		}
		if( publicacao.getDescricao() == null ){
			tl.setDsTimeLine( publicacao.getAvaliacao() );
		}
		tl.setTxCategoria(publicacao.getCategoria() );
		//tl.setIdTimeLine(0);
		
		timeLineDAO.insert(tl);
		
		return tl.getIdTimeLine();
	}
	private void montaJsonTimeLine( List<TimeLine> lst, JsonArray jarr, String  pathImagem){
		for (TimeLine tl : lst) {
			JsonObject jo = new JsonObject();
			jo.addProperty("idTimeLine", tl.getIdTimeLine());
			jo.addProperty("idEscola", tl.getIdEscola() );
			JsonObject joFB = new JsonObject();
			joFB.addProperty("nome", tl.getNoFaceBook());
			joFB.addProperty("chave", tl.getChaveFaceBook());
			jo.add("facebook", joFB);
			jo.addProperty("tipo", getTipoPost( tl.getTpTimeLine() ));
			jo.addProperty("tipo_sl", getTipoPostLg( tl.getTpTimeLine() ));
			jo.addProperty("tempo", calculaTempo( tl.getDtTimeLine() ));
			Escola escola = (Escola)escolaDAO.getById( tl.getIdEscola() );
			if( escola != null ){
				JsonObject joEscola = new JsonObject();
				joEscola.addProperty("nome", escola.getNoEscola());
				joEscola.addProperty("endereco",escola.getTxEndereco() );
				jo.add("escola", joEscola);
			}
			if( tl.getTpTimeLine() == 3 ){
				JsonParser parse = new JsonParser();
				JsonElement el =  parse.parse( tl.getDsTimeLine() );
				
				jo.add("avaliacao",  el );
				
				if( escola != null ){
					jo.addProperty("imagem",  escola.getTxImagem() ); //pathImagem+"/"+tl.getTxImagem() );
				}
				
			}else{
				jo.addProperty("descricao",  tl.getDsTimeLine() );
				jo.addProperty("categoria",  tl.getTxCategoria() );
				//jo.addProperty("resposta",  ""); //tl.getDsResposta() );
				if( tl.getTxImagem() != null ){
					jo.addProperty("imagem",  pathImagem+"/"+tl.getTxImagem() );
				}
			}
			jo.addProperty("qtdCurti", tl.getQtCurti() );
			jo.add("listaImproprio",  jaMarcadoImproprio( tl.getIdTimeLine() ) );
			jarr.add( jo );
		}
	}
	private JsonArray jaMarcadoImproprio(Integer idTimeLine ){
		JsonArray jarr = new JsonArray();
		
		List<Object> lst = improprioTimeLineDAO.getImproprioByTimeLine(idTimeLine);
		for (Object object : lst) {
			jarr.add( new JsonPrimitive(object.toString()) );
		}
		return jarr;
	}
	
	@Transactional
	public String getLocalidade(String sLatitude, String sLongitude, String filtro) {
		JsonArray jarr = new JsonArray();
		
		if( filtro != null){
			List<Escola> lst = this.escolaDAO.getByLocalidade( filtro );
			for (Escola tl : lst) {
				JsonObject jo = new JsonObject();
				jo.addProperty("tipo",  "endereco" );
				jo.addProperty("texto",  tl.getTxEndereco() );
				System.out.println( jo );
				jarr.add( jo );
			}
		}else{
			List<EscolaDistancia> lst = this.escolaDAO.getByLatitudeLongitude(1l, 0l, 20l, Double.parseDouble(sLongitude), Double.parseDouble(sLatitude) );
			for (EscolaDistancia tl : lst) {
				JsonObject jo = new JsonObject();
				
				jo.addProperty("tipo",  "endereco" );
				jo.addProperty("texto",  tl.getTxendereco() );
				
				System.out.println( jo );
				jarr.add( jo );
			}
		}
		
		
		return jarr.toString();
	}
	
	@Transactional
	public String getTimeLine(String pathImagem, String sLatitude, String sLongitude, Long page, Long start, Long limits, String arrIdFavoritos){
		JsonArray jarr = new JsonArray();
		
		List<TimeLine> lst 	=null; 
		if( sLatitude == null || sLongitude == null ){
			if ( arrIdFavoritos != null ){
				JsonParser parse = new JsonParser();
				JsonElement el =  parse.parse( arrIdFavoritos );
				
				lst = timeLineDAO.getFavoritos(el);
			}else{
				lst = timeLineDAO.getTodos();
			}
		}else{
			//TODO A SER IMPLEMENTADO QUANDO ESTIVER BUSCANDO LAT E LONGITUDE
			lst = timeLineDAO.getByLatitudeLongitude( Double.parseDouble(sLongitude), Double.parseDouble(sLatitude) );
		}
		montaJsonTimeLine(lst, jarr, pathImagem);
		
		return jarr.toString();
	}
	
	private String calculaTempo(Date dtTileLine) {
		//TODO FAZER O CALCULO DO TEMPO DECORRIDO
		return "2 minutos atrás ("+dtTileLine.toString()+")";
	}

	private String getTipoPostLg(Integer tpTimeLine) {
		String ret = "d";
		switch (tpTimeLine) {
			case 1:
				ret = "d";
				break;
			case 2:
				ret = "p";
				break;
			case 3:
				ret = "a";
				break;
			default:
				break;
		}
		return ret;
	}
	
	private String getTipoPost(Integer tpTimeLine) {
		String ret = "Denuncia";
		switch (tpTimeLine) {
		case 1:
			ret = "Denúncia";
			break;
		case 2:
			ret = "Proposta";
			break;
		case 3:
			ret = "Avaliação";
			break;
		default:
			break;
		}
		return ret;
	}

	@Transactional
	public String getDetalhePost(Long idTimeLine, String pathImagem) {
//		TimeLine tl = timeLineDAO.recuperarPorChave( idTimeLine );
		TimeLine tl = timeLineDAO.getById( idTimeLine );
		
		JsonObject jo = new JsonObject();
		
		jo.addProperty("idTimeLine", tl.getIdTimeLine());
		//jo.addProperty("idEscola", tl.getIdEscola() );
		JsonObject joFB = new JsonObject();
		joFB.addProperty("nome", tl.getNoFaceBook());
		joFB.addProperty("chave", tl.getChaveFaceBook());
		jo.add("facebook", joFB);
		
		Escola escola = (Escola)escolaDAO.getById( tl.getIdEscola() );
		if( escola != null ){
			JsonObject joEscola = new JsonObject();
			joEscola.addProperty("nome", escola.getNoEscola());
			joEscola.addProperty("endereco",escola.getTxEndereco() );
			jo.add("escola", joEscola);
		}
		
		jo.addProperty("tipo", getTipoPost( tl.getTpTimeLine() ));
		jo.addProperty("tipo_sl", getTipoPostLg( tl.getTpTimeLine() ));
		jo.addProperty("tempo", calculaTempo( tl.getDtTimeLine() ));
		jo.addProperty("descricao",  tl.getDsTimeLine() );
		jo.addProperty("categoria",  tl.getTxCategoria() );
		jo.addProperty("resposta",  tl.getDsResposta() );
		
		if( tl.getTxImagem() != null ){
			jo.addProperty("imagem",  pathImagem+"/"+tl.getTxImagem() );
		}
		
		return jo.toString();
	}

	@Transactional
	public String getEscolasByLocalidade(String localidade) {
		JsonArray jarr = new JsonArray();
		
		List<Escola> lst = escolaDAO.getByLocalidade( localidade );
		
		if( lst != null ){
			//TODO fazer a logica para buscar dos favoritos
			for (Escola tl : lst) {
				JsonObject jo = new JsonObject();
				jo.addProperty("idEscola", tl.getIdEscola() );
				
				jo.addProperty("nome",  tl.getNoEscola() );
				//jo.addProperty("distancia",  formataDistancia(tl.getDistancia()) );
				jo.addProperty("endereco",  tl.getTxEndereco() );
				jo.addProperty("imagem",  tl.getTxImagem() );
				
				System.out.println( jo );
				jarr.add( jo );
			}
		}
		return jarr.toString();
	}

	

	
}