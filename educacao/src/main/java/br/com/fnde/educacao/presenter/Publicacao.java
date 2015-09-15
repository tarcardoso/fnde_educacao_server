package br.com.fnde.educacao.presenter;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Publicacao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1919191L;
	
	private String categoria;
	private String tipo;
	private String nomeUsuario;
	private String chaveUsuario;
	private String idEscola;
	private String descricao;
	private String avaliacao;
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	public String getIdEscola() {
		return idEscola;
	}
	public void setIdEscola(String idEscola) {
		this.idEscola = idEscola;
	}
	
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public String getChaveUsuario() {
		return chaveUsuario;
	}
	public void setChaveUsuario(String chaveUsuario) {
		this.chaveUsuario = chaveUsuario;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
		
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getAvaliacao() {
		return avaliacao;
	}
	public void setAvaliacao(String avaliacao) {
		this.avaliacao = avaliacao;
	}
	@Override
	public String toString() {
		return this.categoria+": "+this.descricao;
	}
	
}
