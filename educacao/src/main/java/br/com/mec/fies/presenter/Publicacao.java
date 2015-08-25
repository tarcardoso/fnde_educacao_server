package br.com.mec.fies.presenter;

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
	private String idUsuario;
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
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
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
