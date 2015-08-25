package br.com.fnde.educacao.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.com.fnde.educacao.domain.DadoCadastral;
import br.com.mec.fies.core.db.HibernateDAOImpl;

@Repository
public class DadoCadastralDAO extends HibernateDAOImpl<DadoCadastral> {

	@Override
	protected Class<DadoCadastral> getEntityClass() {
		return DadoCadastral.class;
	}
	
	public DadoCadastral getDadoCadastralPorCpf(String nuCpf) {
		Query query = getSession().createQuery("from DadoCadastral usr where usr.nuCpf = ? ");
		query.setString(0, nuCpf);
		return (DadoCadastral) query.uniqueResult();				   
	}

	public DadoCadastral getDadoCadastralPorPk(Integer codigo) {
		Query query = getSession().createQuery("from DadoCadastral usr where usr.codigo = ?");
		query.setInteger(0, codigo);
		return (DadoCadastral) query.uniqueResult();
	}

}
