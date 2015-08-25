package br.com.fnde.educacao.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.fnde.educacao.domain.Escola;
import br.com.mec.fies.core.db.HibernateDAOImpl;

@Repository
public class EscolaDAO extends HibernateDAOImpl<Escola> {

	@Override
	protected Class<Escola> getEntityClass() {
		return Escola.class;
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