package br.com.springboot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import br.com.springboot.model.NotaSaidaItem;

@Repository
@Transactional
public class NotaSaidaItemDAO implements CRUD<NotaSaidaItem, Long> {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public NotaSaidaItem pesquisaPeloId(Long id) {
		return em.find(NotaSaidaItem.class, id);
	}

	@Override
	public List<NotaSaidaItem> listaTodos() {
		Query query = em.createNamedQuery("select nei from NotaSaidaItem nei");
		return query.getResultList();
	}

	@Override
	public void insere(NotaSaidaItem notaSaidaItem) {
		em.persist(notaSaidaItem);
	}

	@Override
	public void atualiza(NotaSaidaItem notaSaidaItem) {
		em.merge(notaSaidaItem);
	}

	@Override
	public void remove(NotaSaidaItem notaSaidaItem) {
		em.remove(notaSaidaItem);
	}

	public List<NotaSaidaItem> listaItensNota(Long notaSaidaId) {
		Query query = em.createQuery("from NotaSaidaItem n where n.notaSaida.id = :notaSaidaId")
						.setParameter("notaSaidaId", notaSaidaId);
		return query.getResultList();
	}
}
