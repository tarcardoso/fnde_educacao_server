package br.com.fnde.educacao.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.fnde.educacao.domain.TermoFinanciamento;
import br.com.mec.fies.core.db.HibernateDAOImpl;

@Repository
public class TermoFinanciamentoDAO extends HibernateDAOImpl<TermoFinanciamento> {

	@Override
	protected Class<TermoFinanciamento> getEntityClass() {
		return TermoFinanciamento.class;
	}
	
	public TermoFinanciamento getTermoPorAditamento(Integer coAditamento) {
		Query query = getSession().createQuery("from TermoFinanciamento usr where usr.coAditamento = ? ");
		query.setInteger(0, coAditamento);
		return (TermoFinanciamento) query.uniqueResult();				   
	}

}
