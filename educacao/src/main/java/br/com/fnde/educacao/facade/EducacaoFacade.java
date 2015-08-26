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

import br.com.fnde.educacao.dao.CurtirTimeLineDAO;
import br.com.fnde.educacao.dao.EscolaDAO;
import br.com.fnde.educacao.dao.ImproprioTimeLineDAO;
import br.com.fnde.educacao.dao.TimeLineDAO;
import br.com.fnde.educacao.domain.CurtirTimeLine;
import br.com.fnde.educacao.domain.Escola;
import br.com.fnde.educacao.domain.ImproprioTimeLine;
import br.com.fnde.educacao.domain.TimeLine;
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
		
		JsonObject jo = new JsonObject();
		jo.addProperty("idEscola", idEscola );
		
		jo.addProperty("nome",  escola.getNoEscola() );
		jo.addProperty("endereco",  escola.getTxEndereco() );
		jo.addProperty("imagem",  escola.getTxImagem() );
		jo.addProperty("tipo", "Consolidado");
		
		JsonObject jsAvaliacao = new JsonObject();

		jsAvaliacao.addProperty("aaa", 1 );
		jsAvaliacao.addProperty("bbb", 3 );
		jsAvaliacao.addProperty("ccc", 1 );
		
		jo.add("avaliacao", jsAvaliacao );
		
		jarr.add( jo );
		
		List<TimeLine> lst = timeLineDAO.getByIdEscola(idEscola);
		montaJsonTimeLine(lst, jarr, pathImagem);
		
		return jarr.toString();
	}	
	
	@Transactional
	public String getEscolas(String latitude, String longitude){
		JsonArray jarr = new JsonArray();
		
		List<Escola> lst = null;
		if( latitude != null && longitude != null ){
			lst = escolaDAO.getByLatitudeLongitude(Double.parseDouble(latitude), Double.parseDouble(longitude) );
		}else{
			lst = escolaDAO.getTodos();
		}
		
		for (Escola tl : lst) {
			JsonObject jo = new JsonObject();
			jo.addProperty("idEscola", tl.getIdEscola() );
			
			jo.addProperty("nome",  tl.getNoEscola() );
			jo.addProperty("endereco",  tl.getTxEndereco() );
			jo.addProperty("imagem",  tl.getTxImagem() );
			
			System.out.println( jo );
			jarr.add( jo );
		}
		return jarr.toString();
	}
	
	@Transactional
	public int curti(String idTimeLine, String idUsuario){
		CurtirTimeLine curtir = new CurtirTimeLine();
		
		curtir.setIdTimeLine( Long.parseLong( idTimeLine) );
		curtir.setIdUsuario( Long.parseLong( idUsuario) );
		
		curtirTimeLineDAO.insert(curtir);
		
		return timeLineDAO.updateCurti(idTimeLine);
	}
	
	@Transactional
	public long salvaImproprio(Improprio improprio) {
		ImproprioTimeLine itl = new ImproprioTimeLine();
		itl.setIdImproprio( Long.parseLong(improprio.getIdImproprio()) );
		itl.setIdTimeLine( Long.parseLong(improprio.getIdTimeLine()) );
		itl.setIdUsuario( Long.parseLong(improprio.getIdUsuario() ) );
		
		improprioTimeLineDAO.insert(itl);
		
		return itl.getIdImproprioTimeLine();
	}
	
	@Transactional
	public int salvaTimeLine(Publicacao publicacao) {
		TimeLine tl = new TimeLine();
		tl.setDsTimeLine( publicacao.getDescricao() );
		tl.setQtCurti( 0 );
		tl.setDtTimeLine( new java.sql.Date(System.currentTimeMillis() ));
		if( publicacao.getIdEscola() != null ){
			tl.setIdEscola( Long.parseLong(publicacao.getIdEscola()) );
		}
		
		//TODO definir informacoes do facebook
		if( publicacao.getIdUsuario()  != null){
			tl.setNoFaceBook( publicacao.getIdUsuario() );
		}
		tl.setFotoFaceBook( "resources/img/imagem.jpg" );
		if( publicacao.getTipo() != null ){
			tl.setTpTimeLine( Integer.parseInt(publicacao.getTipo()) );
		}
		if( publicacao.getDescricao() == null ){
			tl.setDsTimeLine( publicacao.getAvaliacao() );
		}
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
			joFB.addProperty("foto", tl.getFotoFaceBook());
			jo.add("facebook", joFB);
			jo.addProperty("tipo", getTipoPost( tl.getTpTimeLine() ));
			jo.addProperty("tipo_sl", getTipoPostLg( tl.getTpTimeLine() ));
			jo.addProperty("tempo", calculaTempo( tl.getDtTimeLine() ));
			
			Escola escola = (Escola)escolaDAO.getById( tl.getIdEscola() );
			
			JsonObject joEscola = new JsonObject();
			joEscola.addProperty("nome", escola.getNoEscola());
			joEscola.addProperty("endereco",escola.getTxEndereco() );
			jo.add("escola", joEscola);
			
			
			if( tl.getTpTimeLine() == 3 ){
				JsonParser parse = new JsonParser();
				JsonElement el =  parse.parse( tl.getDsTimeLine() );
				
				jo.add("avaliacao",  el );
				
				
				
				jo.addProperty("imagem",  escola.getTxImagem() ); //pathImagem+"/"+tl.getTxImagem() );
				
			}else{
				jo.addProperty("descricao",  tl.getDsTimeLine() );
				jo.addProperty("resposta",  ""); //tl.getDsResposta() );
				jo.addProperty("imagem",  pathImagem+"/"+tl.getTxImagem() );
			}
			
			
			
			jo.addProperty("qtdCurti", tl.getQtCurti() );
			
			jarr.add( jo );
		}
	}
	
	@Transactional
	public String getTimeLine(String pathImagem, String sLatitude, String sLongitude){
		JsonArray jarr = new JsonArray();
		
		List<TimeLine> lst 	=null; 
		if( sLatitude == null || sLongitude == null ){
			lst = timeLineDAO.getTodos();
		}else{
			//TODO A SER SIMPLEMENTADO QUANDO ESTIVER BUSCANDO LAT E LONGITUDE
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
		String ret = "D";
		switch (tpTimeLine) {
			case 1:
				ret = "D";
				break;
			case 2:
				ret = "P";
				break;
			case 3:
				ret = "A";
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
		jo.addProperty("idEscola", tl.getIdEscola() );
		JsonObject joFB = new JsonObject();
		joFB.addProperty("nome", tl.getNoFaceBook());
		joFB.addProperty("foto", tl.getFotoFaceBook());
		jo.add("facebook", joFB);
		jo.addProperty("tipo", getTipoPost( tl.getTpTimeLine() ));
		jo.addProperty("tempo", calculaTempo( tl.getDtTimeLine() ));
		jo.addProperty("descricao",  tl.getDsTimeLine() );
		jo.addProperty("resposta",  tl.getDsResposta() );
		jo.addProperty("imagem",  pathImagem+"/"+tl.getTxImagem() );
		
		return jo.toString();
	}

	
}