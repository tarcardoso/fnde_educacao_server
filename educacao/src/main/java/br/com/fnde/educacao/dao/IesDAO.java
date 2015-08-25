package br.com.fnde.educacao.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.fnde.educacao.domain.Ies;
import br.com.mec.fies.core.db.HibernateDAOImpl;

@Repository
public class IesDAO extends HibernateDAOImpl<Ies> {

	@Override
	protected Class<Ies> getEntityClass() {
		return Ies.class;
	}
	
	public Ies getIesPorAditamento(Integer coAditamento) {
		Query query = getSession().createQuery("from Ies usr where usr.coAditamento = ? ");
		query.setInteger(0, coAditamento);
		return (Ies) query.uniqueResult();				   
	}

	public Ies getIesPorPk(Integer coIes) {
		Query query = getSession().createQuery("from Ies usr where usr.coIes = ?");
		query.setInteger(0, coIes);
		return (Ies) query.uniqueResult();
	}

}
