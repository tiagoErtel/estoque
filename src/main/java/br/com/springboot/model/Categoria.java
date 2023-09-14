package br.com.springboot.model;

public enum Categoria {

	CELULARES("Celulares"),
	ELETRODOMESTICO("Eletrodomésticos"),
	INFORMATICA("Informática"),
	MOVEIS("Móveis"),
	UTENSILIO("Utensílio");
	
	private String descricao;
	
	Categoria(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}
