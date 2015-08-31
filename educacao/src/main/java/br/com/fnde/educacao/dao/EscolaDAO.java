package br.com.fnde.educacao.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import br.com.fnde.educacao.domain.Escola;
import br.com.fnde.educacao.presenter.EscolaDistancia;
import br.com.mec.fies.core.db.HibernateDAOImpl;

@Repository
public class EscolaDAO extends HibernateDAOImpl<Escola> {

	@Override
	protected Class<Escola> getEntityClass() {
		return Escola.class;
	}
	
	public List<EscolaDistancia> getByLatitudeLongitude(double latitude, double longitude) {
		
		SQLQuery query = getSession().createSQLQuery(
				" SELECT idescola, noescola, tximagem, "
						+"( (3959 * 1.609 * 1000) * acos( cos( radians( :latitude) ) * cos(  "
						+" radians(numlatitude ) ) * cos( "
						+" radians( numlongitude ) - radians( :longitude) ) + sin( radians( :latitude) ) * sin(  " 
						+" radians( numlatitude ) ) ) ) AS "
						+" distancia "
						+" FROM escola order by distancia limit 10"
						);
		
			query.addScalar("idescola", new LongType())
            .addScalar("noescola", new StringType())
            .addScalar("tximagem", new StringType())
            .addScalar("distancia", new LongType());
            
			query.setParameter("latitude", latitude); //-15.844539);
			query.setParameter("longitude", longitude); //-47.880803);

			query.setResultTransformer( Transformers.aliasToBean(EscolaDistancia.class) );
			
			//query.addEntity(Escola.class);
			
			List<EscolaDistancia> list = query.list();
			
			
			return list;
			
//		Query query = getSession().createSQLQuery(
//				" SELECT idEscola, ( (3959 * 1.609 * 1000) * acos( cos( radians( :latitude ) ) * cos( "
//						+" radians(lat ) ) * cos( "
//						+" radians( lng ) - radians( :longitude) ) + sin( radians( :latitude) ) * sin( "
//						+"  radians( lat ) ) ) ) AS "
//						+"  distance "
//						+" FROM escola where idEscola <= 245  "
//					);
//		query.setParameter("latitude", latitude);   //-15.844539);
//		query.setParameter("longitude", longitude); //-47.880803);
//		
//		List results = query.list();
//		
//		String listaId = "";
//		for (Object object : results) {
//			Object[] arr = (Object[])object;
//			listaId += arr[0]+",";
//			System.out.println( arr[0]+": "+arr[1] );
//		}
//		listaId = listaId.substring(0, listaId.length()-1 );
//		Query queryEscola = getSession().createQuery("from Escola escola where escola.idEscola in ("+listaId+") ");
//		
//		return queryEscola.list();
	}
	public List<Escola> getTodos() {
		return recuperarTodos();
	}

	public Escola getById(Long id) {
		Query query = getSession().createQuery("from Escola escola where escola.idEscola = ? ");
		query.setLong(0, id);
		return (Escola) query.uniqueResult();				   
	}
}