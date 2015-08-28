package br.com.fnde.educacao.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

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
		
//		String listaId = "";
//		for (Object object : results) {
//			Object[] arr = (Object[])object;
//			listaId += arr[0]+",";
//			System.out.println( object);
//		}
//		listaId = listaId.substring(0, listaId.length()-1 );
//		Query queryEscola = getSession().createQuery("from TimeLine tl where tl.idEscola in ("+listaId+") ");
//		
//		return queryEscola.list();
	}
	
	public List<TimeLine> getTodos() {
		return recuperarTodos();
	}
	
	public List<TimeLine> getByIdEscola(Long idEscola) {
		
		Query query = getSession().createQuery("from TimeLine tl where tl.idEscola = ? ");
		query.setLong(0, idEscola);
		List<TimeLine> list = (List<TimeLine>) query.list();
		
		return list;				   
	}
	
	public TimeLine getById(Long id) {
		
		Query query = getSession().createQuery("from TimeLine tl where tl.idTimeLine = ? ");
		query.setLong(0, id);
		return (TimeLine) query.uniqueResult();				   
	}

	public int updateCurti(String idTimeLine) {
		// TODO Auto-generated method stub
		TimeLine timeLine = getById( Long.parseLong( idTimeLine ) );
		
		int qtd = timeLine.getQtCurti();
		qtd++;
		timeLine.setQtCurti( qtd );
		
		return qtd;
	}

}