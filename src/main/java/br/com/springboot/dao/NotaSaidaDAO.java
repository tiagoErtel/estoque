package br.com.springboot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.springboot.model.NotaSaida;

@Repository
@Transactional
public class NotaSaidaDAO implements CRUD<NotaSaida, Long> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public NotaSaida pesquisaPeloId(Long id) {
		return em.find(NotaSaida.class, id);
	}

	@Override
	public List<NotaSaida> listaTodos() {
		Query query = em.createQuery("select ne from NotaSaida ne");
		return query.getResultList();
	}

	@Override
	public void insere(NotaSaida notaSaida) {
		em.persist(notaSaida);
	}

	@Override
	public void atualiza(NotaSaida notaSaida) {
		em.merge(notaSaida);
	}

	@Override
	public void remove(NotaSaida notaSaida) {
		em.remove(notaSaida);
	}
}
