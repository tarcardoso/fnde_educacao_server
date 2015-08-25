package br.com.fnde.educacao.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.fnde.educacao.domain.TimeLine;
import br.com.mec.fies.core.db.HibernateDAOImpl;

@Repository
public class TimeLineDAO extends HibernateDAOImpl<TimeLine> {

	@Override
	protected Class<TimeLine> getEntityClass() {
		return TimeLine.class;
	}
	
	public List<TimeLine> getTodos() {
//		Query query = getSession().createQuery("from Fiador usr where usr.nuCpfEstudante = ?  and coAditamento = ?");
//		query.setString(0, nuCpfEstudante);
//		query.setInteger(1, coAditamento);
//		List<Fiador> list = (List<Fiador>) query.list();
//		
//		return list;		
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