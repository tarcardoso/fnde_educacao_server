package br.com.fnde.educacao.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import br.com.fnde.educacao.domain.TimeLine;
import br.com.mec.fies.core.db.HibernateDAOImpl;

@Repository
public class TimeLineDAO extends HibernateDAOImpl<TimeLine> {

	@Override
	protected Class<TimeLine> getEntityClass() {
		return TimeLine.class;
	}
	
	public List<TimeLine> getByLatitudeLongitude(double latitude, double longitude) {
		//TODO FAZER LOGIDA PARA PAGINACAO
		SQLQuery query = getSession().createSQLQuery(
			" select *,  "
			+" 	(select (3959 * 1.609 * 1000) * acos( cos( radians( :latitude) ) * cos(  "   
			+" 	radians(numlatitude ) ) * cos( "
			+" 	radians( numlongitude ) - radians( :longitude) ) + sin( radians( :latitude) ) * sin(   "
			+" 	 radians( numlatitude ) ) )  "
			+" 	FROM escola where idEscola = tl.idEscola ) AS distancia "
			+"  from timeline tl order by distancia limit 10"
					);
		query.setParameter("latitude", latitude); //-15.844539);
		query.setParameter("longitude", longitude); //-47.880803);
		
		query.addEntity(TimeLine.class);
		
		return query.list();
	}
	
	public List<TimeLine> getFavoritos( JsonElement json ){
		
		System.out.println( json );
		JsonArray arr = json.getAsJsonArray();
		
		String idEscola = "";
		for (JsonElement je : arr) {
			idEscola += je.getAsString()+",";
		}
		if( idEscola.length() > 0 ){
			idEscola = idEscola.substring(0, idEscola.length() - 1 );
		}
		
		if( idEscola.isEmpty() ){
			return new ArrayList<TimeLine>();
		}
		Query query = getSession().createQuery("from TimeLine tl where tl.idEscola in ("+ idEscola +") order by tl.idTimeLine desc");

		List<TimeLine> list = (List<TimeLine>) query.list();
		
		return list;
		
		//return recuperarTodos();
	}
	public List<TimeLine> getTodos() {
		return recuperarTodos();
	}
	
	public List<TimeLine> getByIdEscola(Long idEscola) {
		
		Query query = getSession().createQuery("from TimeLine tl where tl.idEscola = ? order by tl.idTimeLine desc");
		query.setLong(0, idEscola);
		List<TimeLine> list = (List<TimeLine>) query.list();
		
		return list;				   
	}
	
	public TimeLine getById(Long id) {
		
		Query query = getSession().createQuery("from TimeLine tl where tl.idTimeLine = ? ");
		query.setLong(0, id);
		return (TimeLine) query.uniqueResult();				   
	}

	public long updateCurti(String idTimeLine) {
		// TODO Auto-generated method stub
		TimeLine timeLine = getById( Long.parseLong( idTimeLine ) );
		
		long qtd = timeLine.getQtCurti();
		qtd++;
		timeLine.setQtCurti( qtd );
		
		return qtd;
	}

}