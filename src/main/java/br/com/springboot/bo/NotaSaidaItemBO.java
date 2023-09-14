package br.com.springboot.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springboot.dao.CRUD;
import br.com.springboot.dao.NotaSaidaItemDAO;
import br.com.springboot.model.NotaSaidaItem;

@Service
public class NotaSaidaItemBO implements CRUD<NotaSaidaItem, Long> {

	@Autowired
	private NotaSaidaItemDAO dao;
	
	@Override
	public NotaSaidaItem pesquisaPeloId(Long id) {
		return dao.pesquisaPeloId(id);
	}

	@Override
	public List<NotaSaidaItem> listaTodos() {
		return dao.listaTodos();
	}

	@Override
	public void insere(NotaSaidaItem notaSaidaItem) {
		dao.insere(notaSaidaItem);
	}

	@Override
	public void atualiza(NotaSaidaItem notaSaidaItem) {
		dao.atualiza(notaSaidaItem);
	}

	@Override
	public void remove(NotaSaidaItem notaSaidaItem) {
		dao.remove(notaSaidaItem);
	}
	
	public boolean itemJaAdicionado(NotaSaidaItem notaSaidaItem) {
		Long notaSaidaId = notaSaidaItem.getNotaSaida().getId();
		List<NotaSaidaItem> itens = dao.listaItensNota(notaSaidaId);
		
		Long produtoId = notaSaidaItem.getProduto().getId();
		
		if (notaSaidaItem.getId() == null) {
			for (NotaSaidaItem item : itens) {
				if (item.getProduto().getId() == produtoId) {
					return true;
				}
			}
		} else {
			Long notaSaidaItemId = notaSaidaItem.getId();
			for (NotaSaidaItem item : itens) {
				if (item.getProduto().getId() == produtoId && notaSaidaItemId != item.getId()) {
					return true;
				}
			}
		}
		return false;
	}
	
}
