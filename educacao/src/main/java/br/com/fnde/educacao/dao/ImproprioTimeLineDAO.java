package br.com.fnde.educacao.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import br.com.fnde.educacao.domain.ImproprioTimeLine;
import br.com.fnde.educacao.domain.TimeLine;
import br.com.mec.fies.core.db.HibernateDAOImpl;

@Repository
public class ImproprioTimeLineDAO extends HibernateDAOImpl<ImproprioTimeLine> {

	@Override
	protected Class<ImproprioTimeLine> getEntityClass() {
		return ImproprioTimeLine.class;
	}
	
	public List<ImproprioTimeLine> getTodos() {
		return recuperarTodos();
	}

	public ImproprioTimeLine getById(Long id) {
		Query query = getSession().createQuery("from ImproprioTimeLine improprio where impropio.idImproprioTimeLine = ? ");
		query.setLong(0, id);
		return (ImproprioTimeLine) query.uniqueResult();				   
	}

	public List<Object> getImproprioByTimeLine(Integer idTimeLine) {
		SQLQuery query = getSession().createSQLQuery("select idusuario from impropriotimeline where idtimeline = "+idTimeLine );
		
		
		return query.list();
	}

}
