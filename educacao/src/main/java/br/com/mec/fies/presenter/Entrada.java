package br.com.mec.fies.presenter;

import java.io.Serializable;

public class Entrada implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2460478338327394940L;
	
	private String nuCpf;
	private String coAditamento;
	private String senha;
	
	public String getNuCpf() {
		return nuCpf;
	}
	public void setNuCpf(String nuCpf) {
		this.nuCpf = nuCpf;
	}
	public String getCoAditamento() {
		return coAditamento;
	}
	public void setCoAditamento(String coAditamento) {
		this.coAditamento = coAditamento;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
