package br.com.springboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.springboot.bo.NotaSaidaBO;
import br.com.springboot.bo.NotaSaidaItemBO;
import br.com.springboot.bo.ProdutoBO;
import br.com.springboot.model.NotaSaida;
import br.com.springboot.model.NotaSaidaItem;

@Controller
@RequestMapping("/nota-saida-item")
public class NotaSaidaItemController {

	@Autowired
	private ProdutoBO produtoBO;
	
	@Autowired
	private NotaSaidaBO notaSaidaBO;
	
	@Autowired
	private NotaSaidaItemBO notaSaidaItemBO;
	
	@RequestMapping(value="", method = RequestMethod.POST)
	public String salva(@Valid @ModelAttribute NotaSaidaItem notaSaidaItem,
			BindingResult result,
			RedirectAttributes attr,
			ModelMap model) {
		
		Long produtoId = notaSaidaItem.getProduto().getId();
		if (produtoId == null)
			result.rejectValue("produto", "field.required");

		if (notaSaidaItemBO.itemJaAdicionado(notaSaidaItem)) {
			result.rejectValue("produto", "duplicate");
		}
		
		if (result.hasErrors()) {
			model.addAttribute("produtos", produtoBO.listaTodos());
			return "/nota-saida-item/formulario";
		}
		
		NotaSaida notaSaida = notaSaidaBO.pesquisaPeloId(notaSaidaItem.getNotaSaida().getId());
		notaSaidaItem.setNotaSaida(notaSaida);
		
		if (notaSaidaItem.getId() == null) {
			notaSaidaItemBO.insere(notaSaidaItem);
			attr.addFlashAttribute("feedcack", "Produto adicionado com sucesso!");
		} else {
			notaSaidaItemBO.atualiza(notaSaidaItem);
			attr.addFlashAttribute("feedcack", "Produto atualizado com sucesso!");
		}
		
		Long notaSaidaId = notaSaidaItem.getNotaSaida().getId();
		return "redirect:/nota-saida/edita/" + notaSaidaId;
	}
	
	@RequestMapping(value="/edita/{id}", method=RequestMethod.GET)
	public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("notaSaidaItem", notaSaidaItemBO.pesquisaPeloId(id));
		model.addAttribute("produtos", produtoBO.listaTodos());
		return new ModelAndView("/nota-saida-item/formulario", model);
	}
	
	@RequestMapping(value="/remove/{id}", method=RequestMethod.GET)
	public String remove(@PathVariable("id") Long id, RedirectAttributes attr) {
		Long notaId = 0L;
		NotaSaidaItem notaSaidaItem = notaSaidaItemBO.pesquisaPeloId(id);
		notaId = notaSaidaItem.getNotaSaida().getId();
		notaSaidaItemBO.remove(notaSaidaItem);
		attr.addAttribute("feedback", "Item removido com sucesso!");
		return "redirect:/nota-saida/edita/" + notaId;
	}
	
}
