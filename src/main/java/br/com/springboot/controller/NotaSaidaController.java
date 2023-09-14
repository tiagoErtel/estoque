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

import br.com.springboot.bo.ClienteBO;
import br.com.springboot.bo.NotaSaidaBO;
import br.com.springboot.bo.ProdutoBO;
import br.com.springboot.model.NotaSaida;
import br.com.springboot.model.NotaSaidaItem;

@Controller
@RequestMapping("/nota-saida")
public class NotaSaidaController {

	@Autowired
	private NotaSaidaBO notaSaidaBO;
	
	@Autowired
	private ClienteBO clienteBO;
	
	@Autowired
	private ProdutoBO produtoBO;
	
	@RequestMapping(value="/novo", method=RequestMethod.GET)
	public ModelAndView novo(ModelMap model) {
		model.addAttribute("notaSaida", new NotaSaida());
		model.addAttribute("clientes", clienteBO.listaTodos());
		return new ModelAndView("/nota-saida/formulario", model);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String salva(@Valid @ModelAttribute NotaSaida notaSaida,
			BindingResult result,
			RedirectAttributes attr,
			ModelMap model) {
		
		if (notaSaida.getCliente().getId() == null)
			result.rejectValue("cliente", "field.required");
		
		if (result.hasErrors()) {
			model.addAttribute("clientes", clienteBO.listaTodos());
			return "/nota-saida/formulario";
		}
		
		if (notaSaida.getId() == null) {
			notaSaidaBO.insere(notaSaida);
			attr.addFlashAttribute("feedback", "A nota de saida foi cadastrada com sucesso");
		} else {
			notaSaidaBO.atualiza(notaSaida);
			attr.addFlashAttribute("feedback", "Os dados da nota de saida foram atualizados com sucesso");
		}
		
		return "redirect:/nota-saida";
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ModelAndView lista(ModelMap model) {
		model.addAttribute("notas", notaSaidaBO.listaTodos());
		return new ModelAndView("/nota-saida/lista", model);
	}
	
	@RequestMapping(value="/{id}/item", method=RequestMethod.GET)
	public ModelAndView produto(@PathVariable("id") Long id, ModelMap model) {
		NotaSaidaItem nei = new NotaSaidaItem();
		NotaSaida ne = notaSaidaBO.pesquisaPeloId(id);
		nei.setNotaSaida(ne);
		model.addAttribute("notaSaidaItem", nei);
		model.addAttribute("produtos", produtoBO.listaTodos());
		return new ModelAndView("/nota-saida-item/formulario", model);
	}
	
	@RequestMapping(value="/edita/{id}", method=RequestMethod.GET)
	public ModelAndView edita(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("notaSaidaItem", new NotaSaidaItem());
		model.addAttribute("clientes", clienteBO.listaTodos());
		model.addAttribute("notaSaida", notaSaidaBO.pesquisaPeloId(id));
		return new ModelAndView("/nota-saida/formulario", model);
	}
	
	@RequestMapping(value="/remove/{id}", method=RequestMethod.GET)
	public String remove(@PathVariable("id") Long id, RedirectAttributes attr) {
		NotaSaida ne = notaSaidaBO.pesquisaPeloId(id);
		notaSaidaBO.remove(ne);
		attr.addFlashAttribute("feedback", "Nota saida removida com sucesso");
		return "redirect:/nota-saida";
	}
}
