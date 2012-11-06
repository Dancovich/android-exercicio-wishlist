package com.questingsoftware.threewishes.model;

import java.math.BigDecimal;

public class WishItem {

	private Long id;
	private String nome;
	private String local;
	private String categoria;
	private String contato;
	private BigDecimal precoMinimo;
	private BigDecimal precoMaximo;
	
	public WishItem(){
	}
	
	public WishItem(String nome, String local, String categoria,
			String contato, BigDecimal precoMinimo, BigDecimal precoMaximo) {
		super();
		this.nome = nome;
		this.local = local;
		this.categoria = categoria;
		this.contato = contato;
		this.precoMinimo = precoMinimo;
		this.precoMaximo = precoMaximo;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	public BigDecimal getPrecoMinimo() {
		return precoMinimo;
	}
	public void setPrecoMinimo(BigDecimal precoMinimo) {
		this.precoMinimo = precoMinimo;
	}
	public BigDecimal getPrecoMaximo() {
		return precoMaximo;
	}
	public void setPrecoMaximo(BigDecimal precoMaximo) {
		this.precoMaximo = precoMaximo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
