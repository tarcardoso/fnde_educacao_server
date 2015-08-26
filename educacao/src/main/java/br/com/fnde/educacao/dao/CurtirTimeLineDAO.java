package br.com.fnde.educacao.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.fnde.educacao.domain.CurtirTimeLine;
import br.com.mec.fies.core.db.HibernateDAOImpl;

@Repository
public class CurtirTimeLineDAO extends HibernateDAOImpl<CurtirTimeLine> {

	@Override
	protected Class<CurtirTimeLine> getEntityClass() {
		return CurtirTimeLine.class;
	}
	
	public List<CurtirTimeLine> getTodos() {
		return recuperarTodos();
	}

	public CurtirTimeLine getById(Long id) {
		Query query = getSession().createQuery("from CurtirTimeLine curtir where curtir.idCurtirTimeLine = ? ");
		query.setLong(0, id);
		return (CurtirTimeLine) query.uniqueResult();				   
	}

}