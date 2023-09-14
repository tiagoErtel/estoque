package br.com.springboot.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="nota_saida")
public class NotaSaida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(nullable=false, name="data_hora", columnDefinition = "DATETIME")
	private LocalDateTime dataHora;
	
	@ManyToOne
	@JoinColumn(name="cliente_id", nullable=false)
	private Cliente cliente;

	@OneToMany(mappedBy="notaSaida", cascade = CascadeType.ALL)
	private List<NotaSaidaItem> itens;
	
	@Transient
	private Float total;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Float getTotal() {
		this.total = 0f;
		if (this.itens != null) {
			for (NotaSaidaItem notaSaidaItem : itens) {
				total += notaSaidaItem.getValorTotal();
			}
		}
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public List<NotaSaidaItem> getItens() {
		return itens;
	}

	public void setItens(List<NotaSaidaItem> itens) {
		this.itens = itens;
	}
}
