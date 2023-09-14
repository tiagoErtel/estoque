package br.com.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="nota_saida_itens")
public class NotaSaidaItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="produto_id")
	@NotNull
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name="nota_saida_id")
	@NotNull	
	private NotaSaida notaSaida;
	
	@NotNull(message = "informe a quantidade")
	private Integer quantidade;
	
	@NotNull(message = "informe o valor unitário")
	private Float valorUnitario;
	
	private Float valorTotal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public NotaSaida getNotaSaida() {
		return notaSaida;
	}

	public void setNotaSaida(NotaSaida notaSaida) {
		this.notaSaida = notaSaida;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Float getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Float valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Float valorTotal) {
		this.valorTotal = valorTotal;
	}
}
